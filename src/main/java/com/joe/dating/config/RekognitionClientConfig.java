package com.joe.dating.config;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RekognitionClientConfig {
    @Bean
    public AmazonRekognition amazonRekognition() {
        return AmazonRekognitionClientBuilder.defaultClient();
    }
}
