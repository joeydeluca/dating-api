package com.joe.dating.email_sending;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.Map;

public abstract class Email {
    private final Configuration freemarkerConfiguration;

    public Email(Configuration freemarkerConfiguration) {
        this.freemarkerConfiguration = freemarkerConfiguration;
    }

    abstract String getRecipientEmailAddress();
    abstract String getSubject();
    abstract String getHtml();
    abstract boolean isSubscribed();
    abstract String getTemplateName();

    protected String getHtml(Map<String, Object> params) {
        try {
            Template template = freemarkerConfiguration.getTemplate(getTemplateName());
            StringWriter stringWriter = new StringWriter();
            template.process(params, stringWriter);
            String content = stringWriter.toString();
            stringWriter.close();
            return content;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
