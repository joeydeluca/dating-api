package com.joe.dating.email_verification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("address", email);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        MailgunVerificationResponse response = new RestTemplateBuilder().basicAuthorization("api", verificationKey).build()
                .postForObject(url, httpEntity, MailgunVerificationResponse.class);

        if(response != null && response.isValid() && !response.isDisposableAddress() && !response.isRoleAddress()) {
            logger.info("Email failed BrightVerify check; email={}", email);
            return true;
        }

        logger.info("Email passed Mailgun verification; email={}", email);
        return false;
    }
}
