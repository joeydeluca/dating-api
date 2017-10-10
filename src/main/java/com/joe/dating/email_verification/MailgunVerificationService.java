package com.joe.dating.email_verification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;

@Component
public class MailgunVerificationService implements EmailVerificationService {
    private final Logger logger = LoggerFactory.getLogger(MailgunVerificationService.class);

    private final String url;
    private final String verificationKey;

    public MailgunVerificationService(
            @Value("${email.mailgun.verification-url}") String url,
            @Value("${email.mailgun.verification-key}") String verificationKey) {
        this.url = url;
        this.verificationKey = verificationKey;
    }

    @Override
    public boolean verify(String email) {
        logger.info("Performing Mailgun email verification; email={}", email);

        MailgunVerificationResponse response;
        try {
            response = new RestTemplateBuilder().basicAuthorization("api", verificationKey).build()
                    .getForObject(url + "?address=" + email, MailgunVerificationResponse.class);
        }
        catch(Exception e) {
            logger.info("Email failed BrightVerify check; exception={}", e);
            return false;
        }

        if(response != null && (!response.isValid() || response.isDisposableAddress() || response.isRoleAddress())) {
            logger.info("Email failed BrightVerify check; email={}", email);
            return false;
        }

        logger.info("Email passed Mailgun verification; email={}", email);
        return true;
    }
}
