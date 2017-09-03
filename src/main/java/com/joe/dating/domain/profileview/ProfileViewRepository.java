package com.joe.dating.domain.profileview;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileViewRepository extends JpaRepository<ProfileView, Long> {
    List<ProfileView> findByToUserId(Long toUserId);
}
