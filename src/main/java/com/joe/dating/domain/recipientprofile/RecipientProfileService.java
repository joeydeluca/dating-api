package com.joe.dating.domain.recipientprofile;

import com.joe.dating.domain.favorite.FavoriteRepository;
import com.joe.dating.domain.flirt.FlirtRepository;
import com.joe.dating.domain.message.Message;
import com.joe.dating.domain.message.MessageRepository;
import com.joe.dating.domain.profileview.ProfileViewRepository;
import com.joe.dating.rest.FavoritesDto;
import com.joe.dating.rest.ProfileEvent;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.joe.dating.config.CacheConfig.*;

@Component
public class RecipientProfileService {
    private final ProfileViewRepository profileViewRepository;
    private final FlirtRepository flirtRepository;
    private final FavoriteRepository favoriteRepository;
    private final MessageRepository messageRepository;

    public RecipientProfileService(ProfileViewRepository profileViewRepository, FlirtRepository flirtRepository, FavoriteRepository favoriteRepository, MessageRepository messageRepository) {
        this.profileViewRepository = profileViewRepository;
        this.flirtRepository = flirtRepository;
        this.favoriteRepository = favoriteRepository;
        this.messageRepository = messageRepository;
    }

    @Cacheable(cacheNames = PROFILE_VIEWS_BY_ID_CACHE, key = "#recipientUserId")
    public List<ProfileEvent> getProfileViews(Long recipientUserId) {
        return profileViewRepository.findByToUserId(recipientUserId)
                .stream()
                .map(view -> new ProfileEvent(view.getFromUser(), view.getDate())).collect(Collectors.toList());
    }

    @CacheEvict(cacheNames = PROFILE_VIEWS_BY_ID_CACHE, key = "#recipientUserId")
    public void evictProfileViewCache(Long recipientUserId) {
    }

    @Cacheable(cacheNames = FLIRTS_BY_ID_CACHE, key = "#recipientUserId")
    public List<ProfileEvent> getFlirts(Long recipientUserId) {
        return flirtRepository.findByToUserId(recipientUserId)
                .stream().map(flirt -> new ProfileEvent(flirt.getFromUser(), flirt.getDate())).collect(Collectors.toList());
    }

    @CacheEvict(cacheNames = FLIRTS_BY_ID_CACHE, key = "#recipientUserId")
    public void evictFlirtsCache(Long recipientUserId) {
    }

    @Cacheable(cacheNames = FAVORITES_BY_ID_CACHE, key = "#userId")
    public FavoritesDto getFavorites(Long userId) {
        FavoritesDto favoritesDto = new FavoritesDto();

        favoriteRepository.findByFromUserIdOrToUserId(userId, userId)
                .stream().forEach(favorite -> {
                    if(favorite.getFromUser().getId().equals(userId)) {
                        favoritesDto.getMyFavorites().add(new ProfileEvent(favorite.getToUser(), favorite.getDate()));
                    } else {
                        favoritesDto.getFavoritedMe().add(new ProfileEvent(favorite.getFromUser(), favorite.getDate()));
                    }
                }
        );

        return favoritesDto;
    }

    @CacheEvict(cacheNames = FAVORITES_BY_ID_CACHE, key = "#userId")
    public void evictFavoritesCache(Long userId) {
    }

    @Cacheable(cacheNames = MESSAGES_BY_ID_CACHE, key = "#userId")
    public List<Message> getMessages(Long userId) {
        return messageRepository.findByFromUserIdOrToUserId(userId, userId);
    }

    @CacheEvict(cacheNames = MESSAGES_BY_ID_CACHE, key = "#userId")
    public void evictMessagesCache(Long userId) {
    }
}
