package com.joe.dating.domain.flirt;

import com.joe.dating.domain.favorite.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Joe Deluca on 11/21/2016.
 */
public interface FlirtRepository extends JpaRepository<Flirt, Long> {

    @Query("SELECT COUNT(f) FROM flirts f WHERE from_user_id = :fromUserId AND to_user_id = :toUserId AND f.date > :yesterday")
    Long getFlirtsForToday(
            @Param("fromUserId") Long fromUserId,
            @Param("toUserId") Long toUserId,
            @Param("yesterday") Date yesterday);

    List<Flirt> findByToUserId(Long toUserId);

}
