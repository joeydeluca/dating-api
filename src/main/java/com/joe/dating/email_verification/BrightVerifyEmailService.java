package com.joe.dating.email_verification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;


/**
 * Created by Joe Deluca on 11/30/2016.
 */
public class BrightVerifyEmailService implements EmailVerificationService {

    private final Logger logger = LoggerFactory.getLogger(BrightVerifyEmailService.class);

    @Value("${email.validation.brightverify-url:}")
    private String brightVerifyUrl;

    private RestOperations restOperations;

    public BrightVerifyEmailService(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    @Override
    public boolean verify(String email) {
        logger.info("Performing BrightVerify check; email={}", email);
        BrightVerifyResponseDto responseDto = restOperations.getForObject(brightVerifyUrl, BrightVerifyResponseDto.class);

        if (BrightVerifyResponseDto.STATUS_INVALID.equals(responseDto.getStatus()) || responseDto.isDisposable() || responseDto.isRoleAddress()) {
            logger.info("Email failed BrightVerify check; email={}", email);
            return false;
        }

        logger.info("Email passed BrightVerify check; email={}", email);
        return true;
    }
}
