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
import com.joe.dating.domain.user.User;
import com.joe.dating.domain.user.UserService;
import com.joe.dating.security.AuthContext;
import com.joe.dating.security.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Joe Deluca on 11/21/2016.
 */
@RestController
@RequestMapping("/api/recipient-profile")
public class RecipientProfileResource {

    private static final Logger logger = LoggerFactory.getLogger(RecipientProfileResource.class);

    private final AuthService authService;
    private final FlirtRepository flirtRepository;
    private final FavoriteRepository favoriteRepository;
    private final UserService userService;
    private final MessageRepository messageRepository;
    private final ProfileViewRepository profileViewRepository;

    public RecipientProfileResource(AuthService authService, FlirtRepository flirtRepository, FavoriteRepository favoriteRepository, UserService userService, MessageRepository messageRepository, ProfileViewRepository profileViewRepository) {
        this.authService = authService;
        this.flirtRepository = flirtRepository;
        this.favoriteRepository = favoriteRepository;
        this.userService = userService;
        this.messageRepository = messageRepository;
        this.profileViewRepository = profileViewRepository;
    }

    @GetMapping("/{recipientUserId}")
    public ResponseEntity<RecipientProfile> findOne(@PathVariable("recipientUserId") Long recipientUserId) {
        User user = userService.findOne(recipientUserId);
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

        Flirt flirt = new Flirt();
        flirt.setFromUser(new User(authContext.getUserId()));
        flirt.setToUser(new User(recipientUserId));
        flirtRepository.save(flirt);

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

        Favorite favorite = new Favorite();
        favorite.setFromUser(new User(authContext.getUserId()));
        favorite.setToUser(new User(recipientUserId));
        favoriteRepository.save(favorite);

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

        favoriteRepository.delete(favorite);

        return ResponseEntity.accepted().build();
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages(
            @RequestHeader(value = "authorization") String authToken
    ) {
        AuthContext authContext = this.authService.verifyToken(authToken);

        return ResponseEntity.ok(
            messageRepository.findByFromUserIdOrToUserId(authContext.getUserId(), authContext.getUserId())
        );
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

        User fromUser = new User();
        fromUser.setId(authContext.getUserId());
        User toUser = new User();
        toUser.setId(recipientUserId);

        message.setFromUser(fromUser);
        message.setToUser(toUser);
        message.setMessage(messageText);
        message.setSubject("");

        messageRepository.save(message);

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
            logger.info("Marking message as read; fromProfileId={}, toProfileId={}, messageId={}", authContext.getUserId(), recipientUserId, message.getId());
        });

        messageRepository.save(readMessages);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/favorites")
    public ResponseEntity<FavoritesDto> getFavorites(
            @RequestHeader(value = "authorization") String authToken
    ) {
        AuthContext authContext = this.authService.verifyToken(authToken);

        FavoritesDto favoritesDto = new FavoritesDto();

        favoriteRepository.findByFromUserIdOrToUserId(authContext.getUserId(), authContext.getUserId())
                .stream().forEach(favorite -> {
                    if(favorite.getFromUser().getId().equals(authContext.getUserId())) {
                        favoritesDto.getMyFavorites().add(new ProfileEvent(favorite.getToUser(), favorite.getDate()));
                    } else {
                        favoritesDto.getFavoritedMe().add(new ProfileEvent(favorite.getFromUser(), favorite.getDate()));
                    }
                }
        );

        return ResponseEntity.ok(favoritesDto);
    }

    @GetMapping("/flirts")
    public ResponseEntity<List<ProfileEvent>> getFlirts(
            @RequestHeader(value = "authorization") String authToken
    ) {
        AuthContext authContext = this.authService.verifyToken(authToken);

        return ResponseEntity.ok(
                flirtRepository.findByToUserId(authContext.getUserId())
                        .stream().map(flirt -> new ProfileEvent(flirt.getFromUser(), flirt.getDate())).collect(Collectors.toList())

        );
    }

    @GetMapping("/profile-views")
    public ResponseEntity<List<ProfileEvent>> getProfileViews(
            @RequestHeader(value = "authorization") String authToken
    ) {
        AuthContext authContext = this.authService.verifyToken(authToken);

        return ResponseEntity.ok(
                profileViewRepository.findByToUserId(authContext.getUserId())
                    .stream().map(view -> new ProfileEvent(view.getFromUser(), view.getDate())).collect(Collectors.toList())
        );
    }

}
