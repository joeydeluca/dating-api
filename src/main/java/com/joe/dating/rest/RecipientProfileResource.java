package com.joe.dating.rest;

import com.joe.dating.common.NiceTryException;
import com.joe.dating.domain.favorite.Favorite;
import com.joe.dating.domain.favorite.FavoriteRepository;
import com.joe.dating.domain.flirt.Flirt;
import com.joe.dating.domain.flirt.FlirtRepository;
import com.joe.dating.domain.message.Message;
import com.joe.dating.domain.message.MessageRepository;
import com.joe.dating.domain.profileview.ProfileView;
import com.joe.dating.domain.profileview.ProfileViewRepository;
import com.joe.dating.domain.recipientprofile.RecipientProfileService;
import com.joe.dating.domain.user.User;
import com.joe.dating.domain.user.UserService;
import com.joe.dating.email_sending.*;
import com.joe.dating.security.AuthContext;
import com.joe.dating.security.AuthService;
import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Joe Deluca on 11/21/2016.
 */
@RestController
@RequestMapping("/api/recipient-profile")
public class RecipientProfileResource {

    private static final Logger logger = LoggerFactory.getLogger(RecipientProfileResource.class);

    private final RecipientProfileService recipientProfileService;
    private final AuthService authService;
    private final FlirtRepository flirtRepository;
    private final FavoriteRepository favoriteRepository;
    private final UserService userService;
    private final MessageRepository messageRepository;
    private final ProfileViewRepository profileViewRepository;
    private final EmailSender emailSender;
    private final Configuration freemarkerConfiguration;

    public RecipientProfileResource(RecipientProfileService recipientProfileService, AuthService authService, FlirtRepository flirtRepository, FavoriteRepository favoriteRepository, UserService userService, MessageRepository messageRepository, ProfileViewRepository profileViewRepository, EmailSender emailSender, Configuration freemarkerConfiguration) {
        this.recipientProfileService = recipientProfileService;
        this.authService = authService;
        this.flirtRepository = flirtRepository;
        this.favoriteRepository = favoriteRepository;
        this.userService = userService;
        this.messageRepository = messageRepository;
        this.profileViewRepository = profileViewRepository;
        this.emailSender = emailSender;
        this.freemarkerConfiguration = freemarkerConfiguration;
    }

    @GetMapping("/{recipientUserId}")
    public ResponseEntity<RecipientProfile> findOne(
            @PathVariable("recipientUserId") Long recipientUserId,
            @RequestHeader(value = "authorization") String authToken) {

        AuthContext authContext = this.authService.verifyToken(authToken);

        User user = userService.findOne(recipientUserId);

        doAsync(() -> {
            ProfileView profileView = new ProfileView();
            profileView.setFromUser(new User(authContext.getUserId()));
            profileView.setToUser(new User(recipientUserId));
            profileViewRepository.save(profileView);
            recipientProfileService.evictProfileViewCache(recipientUserId);
        });

        return ResponseEntity.ok(new RecipientProfile(user));
    }

    @PostMapping("/{recipientUserId}/flirt")
    public ResponseEntity<Void> create(
            @RequestHeader(value = "authorization") String authToken,
            @PathVariable("recipientUserId") Long recipientUserId
    ) {
        AuthContext authContext = this.authService.verifyToken(authToken);

        ZonedDateTime today = ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZoneId.systemDefault());
        ZonedDateTime yesterday = today.minusDays(1);

        logger.info("Attempting to send Flirt; fromProfileId={}, toProfileId={}", authContext.getUserId(), recipientUserId);

        if(flirtRepository.getFlirtsForToday(authContext.getUserId(), recipientUserId, Date.from(yesterday.toInstant())) > 0) {
            logger.info("Flirt already sent for today; fromProfileId={}, toProfileId={}", authContext.getUserId(), recipientUserId);
            return ResponseEntity.accepted().build();
        }

        User user = userService.findOne(authContext.getUserId());
        User recipientUser = userService.findOne(recipientUserId);
        Flirt flirt = new Flirt();
        flirt.setFromUser(user);
        flirt.setToUser(recipientUser);
        flirtRepository.save(flirt);

        recipientProfileService.evictFlirtsCache(recipientUser.getId());

        emailSender.sendEmail(new FlirtAlertEmail(user, recipientUser, freemarkerConfiguration));

        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{recipientUserId}/favorite")
    public ResponseEntity<Void> createFavorite(
            @RequestHeader(value = "authorization") String authToken,
            @PathVariable("recipientUserId") Long recipientUserId
    ) {
        AuthContext authContext = this.authService.verifyToken(authToken);

        logger.info("Attempting to add Favorite; fromProfileId={}, toProfileId={}", authContext.getUserId(), recipientUserId);

        if(favoriteRepository.findByFromUserIdAndToUserId(authContext.getUserId(), recipientUserId) != null) {
            logger.info("Favorite already sent; fromProfileId={}, toProfileId={}", authContext.getUserId(), recipientUserId);
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        User user = userService.findOne(authContext.getUserId());
        User recipientUser = userService.findOne(recipientUserId);
        Favorite favorite = new Favorite();
        favorite.setFromUser(user);
        favorite.setToUser(recipientUser);
        favoriteRepository.save(favorite);

        recipientProfileService.evictFavoritesCache(authContext.getUserId());
        recipientProfileService.evictFavoritesCache(recipientUserId);

        emailSender.sendEmail(new FavoriteAlertEmail(user, recipientUser, freemarkerConfiguration));

        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{recipientUserId}/favorite")
    public ResponseEntity<Void> deleteFavorite(
            @RequestHeader(value = "authorization") String authToken,
            @PathVariable("recipientUserId") Long recipientUserId
    ) {
        AuthContext authContext = this.authService.verifyToken(authToken);

        logger.info("Attempting to delete Favorite; fromProfileId={}, toProfileId={}", authContext.getUserId(), recipientUserId);

        Favorite favorite = favoriteRepository.findByFromUserIdAndToUserId(authContext.getUserId(), recipientUserId);
        if(favorite == null) {
            logger.info("Favorite cannot be deleted because it does not exist; fromProfileId={}, toProfileId={}", authContext.getUserId(), recipientUserId);
            return ResponseEntity.accepted().build();
        }

        recipientProfileService.evictFavoritesCache(authContext.getUserId());
        recipientProfileService.evictFavoritesCache(recipientUserId);

        favoriteRepository.delete(favorite);

        return ResponseEntity.accepted().build();
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages(
            @RequestHeader(value = "authorization") String authToken
    ) {
        AuthContext authContext = this.authService.verifyToken(authToken);
        return ResponseEntity.ok(recipientProfileService.getMessages(authContext.getUserId()));
    }

    @PostMapping("/{recipientUserId}/message")
    public ResponseEntity<Message> sendMessage(
            @RequestHeader(value = "authorization") String authToken,
            @PathVariable("recipientUserId") Long recipientUserId,
            @RequestBody String messageText
    ) {
        AuthContext authContext = this.authService.verifyToken(authToken);

        logger.info("Attempting to send message; fromProfileId={}, toProfileId={}", authContext.getUserId(), recipientUserId);

        if(!authContext.isPaid()) {
            logger.info("Unpaid member is trying to send message; fromProfileId={}, toProfileId={}", authContext.getUserId(), recipientUserId);
            throw new NiceTryException();
        }

        Message message = new Message();

        User user = userService.findOne(authContext.getUserId());
        User recipientUser = userService.findOne(recipientUserId);

        message.setFromUser(user);
        message.setToUser(recipientUser);
        message.setMessage(messageText);
        message.setSubject("");

        messageRepository.save(message);

        recipientProfileService.evictMessagesCache(authContext.getUserId());
        recipientProfileService.evictMessagesCache(recipientUserId);

        emailSender.sendEmail(new MessageAlertEmail(user, recipientUser, freemarkerConfiguration));

        return ResponseEntity.ok(message);
    }

    @PutMapping("/{recipientUserId}/message/read")
    public ResponseEntity<Void> markMessagesAsRead(
            @RequestHeader(value = "authorization") String authToken,
            @PathVariable("recipientUserId") Long recipientUserId) {
        AuthContext authContext = this.authService.verifyToken(authToken);

        logger.info("Attempting to mark messages as read; fromProfileId={}, toProfileId={}", authContext.getUserId(), recipientUserId);

        if(!authContext.isPaid()) {
            logger.info("Unpaid member is trying to read messages; fromProfileId={}, toProfileId={}", authContext.getUserId(), recipientUserId);
            throw new NiceTryException();
        }

        List<Message> messages = messageRepository.findByFromUserIdAndToUserId(recipientUserId, authContext.getUserId());
        if(messages == null) {
            return ResponseEntity.ok().build();
        }

        List<Message> readMessages = new ArrayList<>();
        messages.stream().filter((message -> message.getReadDate() == null)).forEach(message -> {
            message.setReadDate(new Date());
            readMessages.add(message);
            messageRepository.save(readMessages);
            recipientProfileService.evictMessagesCache(authContext.getUserId());
            recipientProfileService.evictMessagesCache(recipientUserId);
            recipientProfileService.getMessages(authContext.getUserId()); // fill cache
            logger.info("Marking message as read; fromProfileId={}, toProfileId={}, messageId={}", authContext.getUserId(), recipientUserId, message.getId());
        });

        return ResponseEntity.ok().build();
    }

    @GetMapping("/favorites")
    public ResponseEntity<FavoritesDto> getFavorites(
            @RequestHeader(value = "authorization") String authToken
    ) {
        AuthContext authContext = this.authService.verifyToken(authToken);
        return ResponseEntity.ok(recipientProfileService.getFavorites(authContext.getUserId()));
    }

    @GetMapping("/flirts")
    public ResponseEntity<List<ProfileEvent>> getFlirts(
            @RequestHeader(value = "authorization") String authToken
    ) {
        AuthContext authContext = this.authService.verifyToken(authToken);
        return ResponseEntity.ok(recipientProfileService.getFlirts(authContext.getUserId()));
    }

    @GetMapping("/profile-views")
    public ResponseEntity<List<ProfileEvent>> getProfileViews(
            @RequestHeader(value = "authorization") String authToken
    ) {
        AuthContext authContext = this.authService.verifyToken(authToken);
        return ResponseEntity.ok(
                recipientProfileService.getProfileViews(authContext.getUserId())
        );
    }

    @GetMapping("/{recipientUserId}/report-profile")
    public ResponseEntity<Void> reportProfile(
            @RequestHeader(value = "authorization") String authToken,
            @PathVariable("recipientUserId") Long recipientUserId
    ) {
        AuthContext authContext = this.authService.verifyToken(authToken);

        logger.info("Report profile; fromProfileId={}, toProfileId={}", authContext.getUserId(), recipientUserId);

        ContactDto contactDto = new ContactDto();
        contactDto.setMessage("User "+authContext.getUserId()+" reported a bad profile: " + recipientUserId);
        contactDto.setUserId(recipientUserId);
        emailSender.sendEmail(new SupportEmail(contactDto));

        return ResponseEntity.accepted().build();
    }

    @Async
    private void doAsync(Runnable f) {
        f.run();
    }



}
