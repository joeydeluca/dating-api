package com.joe.dating.email_verification;

/**
 * Created by Joe Deluca on 11/30/2016.
 */
public interface EmailVerificationService {
    boolean verify(String email);
}
