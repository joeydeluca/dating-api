package com.joe.dating.domain.passwordreset;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetRequestRepository extends JpaRepository<PasswordResetRequest, String> {
}
