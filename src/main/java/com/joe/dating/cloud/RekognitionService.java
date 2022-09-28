package com.joe.dating.cloud;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class RekognitionService {
    private final Logger logger = LoggerFactory.getLogger(RekognitionService.class);

    private static Set<String> BANNED_CATAGORIES = new HashSet<String>() {{
        add("Explicit Nudity");
        add("Violence");
        add("Visually Disturbing");
        add("Drugs");
        add("Hate Symbols");
    }};

    @Value("${s3.bucket-name}")
    private String s3BucketName;

    public void executeImageModeration(String s3Filename) {
        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

        DetectModerationLabelsRequest request = new DetectModerationLabelsRequest()
                .withImage(new Image().withS3Object(new S3Object().withName(s3Filename).withBucket(s3BucketName)))
                .withMinConfidence(75F);

        DetectModerationLabelsResult result = null;
        try {
            result = rekognitionClient.detectModerationLabels(request);
        } catch (Exception e) {
            logger.error("AWS Rekognition failed to moderate image", e);
            return;
        }

        List<ModerationLabel> labels = result.getModerationLabels();
        logger.info("Detected labels for " + s3Filename);

        for (ModerationLabel label : labels) {
            if (BANNED_CATAGORIES.contains(label.getParentName())) {
                logger.warn("Inappropriate image detected: " + label.getParentName());
                throw new RuntimeException("Image contains inappropriate content. Moderation Failure: " + label.getParentName());
            }
        }
    }
}
