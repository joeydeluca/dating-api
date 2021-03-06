package com.joe.dating.email_sending;

import com.joe.dating.domain.user.User;
import freemarker.template.Configuration;

import java.util.HashMap;
import java.util.Map;

public class FavoriteAlertEmail extends Email {
    private final User sender;
    private final User recipient;

    public FavoriteAlertEmail(User sender, User recipient, Configuration freemarkerConfiguration) {
        super(freemarkerConfiguration);
        this.sender = sender;
        this.recipient = recipient;
    }

    @Override
    public String getRecipientEmailAddress() {
        return recipient.getEmail();
    }

    @Override
    public String getSubject() {
        return "Someone has added you to their favorites";
    }

    @Override
    public String getHtml() {
        Map<String, Object> params = new HashMap<>();
        params.put("sender", sender);
        params.put("s1", sender.getUsername() + " added you to their favorites.");

        return super.getHtml(params);
    }

    public String getTemplateName() {
        return "action-received.ftlh";
    }

    @Override
    boolean isSubscribed() {
        return recipient.getEmailSubscription() == null || recipient.getEmailSubscription().isSubscribedFavoritedMeAlert();
    }
}
