package com.joe.dating.domain.favorite;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Joe Deluca on 11/21/2016.
 */
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByFromUserIdOrToUserId(Long fromUserId, Long toUserId);
    Favorite findByFromUserIdAndToUserId(Long fromUserId, Long toUserId);
}
