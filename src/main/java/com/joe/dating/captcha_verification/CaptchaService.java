package com.joe.dating.captcha_verification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@Component
public class CaptchaService {
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
        CaptchaResponse response =
                restOperations.getForObject(String.format(verificationUrl, secret, clientCaptchaResponse), CaptchaResponse.class);

        return response.isSuccess();
    }
}
