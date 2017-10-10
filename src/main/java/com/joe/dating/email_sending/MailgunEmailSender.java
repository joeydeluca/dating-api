package com.joe.dating.email_sending;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
public class MailgunEmailSender implements EmailSender {
    private final Logger logger = LoggerFactory.getLogger(MailgunEmailSender.class);
    private String url;
    private String from;
    private String apiKey;
    private final Boolean isEmailEnabled;

    public MailgunEmailSender(
            @Value("${email.mailgun.url}") String url,
            @Value("${email.mailgun.from}") String from,
            @Value("${email.mailgun.api-key}") String apiKey,
            @Value("${email.sending-enabled}") Boolean isEmailEnabled) {
        this.from = from;
        this.apiKey = apiKey;
        this.url = url;
        this.isEmailEnabled = isEmailEnabled;
    }

    @Async
    @Override
    public void sendEmail(Email email) {
        logger.info("Sending email. type=" + email.getClass().getSimpleName() + ";email="+email.getRecipientEmailAddress());


        if(!isEmailEnabled) {
            logger.info("Email sending is not enabled. email=" + email.getRecipientEmailAddress());
            return;
        }

        if(!email.isSubscribed()) {
            logger.info(email.getClass().getSimpleName() + " is not enabled for " + email.getRecipientEmailAddress());
            return;
        }

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("from", from);
        params.add("to", email.getRecipientEmailAddress());
        params.add("subject", email.getSubject());
        params.add("html", email.getHtml());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        ResponseEntity response = new  RestTemplateBuilder().basicAuthorization("api", apiKey).build()
                .postForEntity(url, httpEntity, String.class);

        if(!response.getStatusCode().is2xxSuccessful()) {
            logger.error("Failed to send email with Mailgun:" + response.getStatusCode());
        }
        else {
            logger.info("Email sent. type=" + email.getClass().getSimpleName() + ";email="+email.getRecipientEmailAddress());
        }
    }

}
