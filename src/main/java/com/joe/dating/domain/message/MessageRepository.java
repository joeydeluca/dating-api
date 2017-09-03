package com.joe.dating.domain.message;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByFromUserIdOrToUserId(Long fromUserId, Long toUserId);
    List<Message> findByFromUserIdAndToUserId(Long fromUserId, Long toUserId);
}
