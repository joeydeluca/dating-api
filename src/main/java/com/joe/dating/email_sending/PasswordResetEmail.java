package com.joe.dating.email_sending;

public class PasswordResetEmail extends Email {
    private final String recipientEmail;
    private final String requestId;

    public PasswordResetEmail(String requestId, String recipientEmail) {
        super(null);
        this.requestId = requestId;
        this.recipientEmail = recipientEmail;
    }

    @Override
    public String getRecipientEmailAddress() {
        return this.recipientEmail;
    }

    @Override
    public String getSubject() {
        return "UglySchmucks Password Reset";
    }

    @Override
    public String getHtml() {
        String link = "http://www.uglyschmucks.com/password-reset?requestId=" + requestId;
        StringBuilder sb = new StringBuilder();
        sb.append("You have requested a password reset. Please click on the following link to reset your password.");
        sb.append("<BR/>");
        sb.append("<a href=\"" + link + "\">" + link + "</a>");
        return sb.toString();
    }

    public String getTemplateName() {
        return null;
    }

    @Override
    boolean isSubscribed() {
        return true;
    }
}
