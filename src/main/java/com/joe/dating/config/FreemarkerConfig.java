package com.joe.dating.config;

import freemarker.template.TemplateExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;

@Configuration
public class FreemarkerConfig {

    @Primary
    @Bean
    public freemarker.template.Configuration getConfig() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration();
        cfg.setClassForTemplateLoading(this.getClass(), "/emails/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setNumberFormat("computer");
        return cfg;
    }
}
