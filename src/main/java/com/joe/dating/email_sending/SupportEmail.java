package com.joe.dating.email_sending;

import com.joe.dating.rest.ContactDto;

public class SupportEmail extends Email {
    private final ContactDto contactDto;

    public SupportEmail(ContactDto contactDto) {
        super(null);
        this.contactDto = contactDto;
    }

    @Override
    public String getRecipientEmailAddress() {
        return "joeydeluca123@gmail.com";
    }

    @Override
    public String getSubject() {
        return "Dating Contact Form";
    }

    @Override
    public String getHtml() {
        StringBuilder sb = new StringBuilder();
        if(contactDto.getUserId() != null) {
            sb.append("User Id: " + contactDto.getUserId());
            sb.append("<BR/>");
        }
        sb.append("Name: " + contactDto.getName());
        sb.append("<BR/>");
        sb.append("Email: " + contactDto.getEmail());
        sb.append("<BR/>");
        sb.append("Message: " + contactDto.getMessage());

        return sb.toString();
    }

    public String getTemplateName() {
        return "action-received.ftlh";
    }

    @Override
    boolean isSubscribed() {
        return true;
    }
}
