package com.joe.dating.captcha_verification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@Component
public class CaptchaService {
    private static final Logger logger = LoggerFactory.getLogger(CaptchaService.class);
    private RestOperations restOperations;
    private String secret;
    private String verificationUrl;

    public CaptchaService(
            RestOperations restOperations,
            @Value("${captcha.secret}") String secret,
            @Value("${captcha.verification-url}") String verificationUrl) {
        this.restOperations = restOperations;
        this.secret = secret;
        this.verificationUrl = verificationUrl;
    }

    public boolean verifyCaptcha(String clientCaptchaResponse) {
        String url = String.format(verificationUrl, secret, clientCaptchaResponse);
        logger.error("Invoking Captcha. url: {}", url);
        CaptchaResponse response = restOperations.getForObject(url, CaptchaResponse.class);

        if(response == null || !response.isSuccess()) {
            logger.error("Captcha failed. response: {}", response);
            return false;
        }

        return true;
    }
}
