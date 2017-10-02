package com.joe.dating.cloud;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class S3CloudStorage implements CloudStorage {
    private final Logger logger = LoggerFactory.getLogger(S3CloudStorage.class);
    private final String bucketName;
    private final AmazonS3 s3;

    public S3CloudStorage(@Value("${s3.bucket-name}") String bucketName) {
        this.bucketName = bucketName;
        this.s3 = new AmazonS3Client();
        Region region = Region.getRegion(Regions.US_EAST_1);
        s3.setRegion(region);
    }

    @Override
    public void uploadImage(InputStream inputStream, String filename) {
        logger.info("Uploading image to S3. image_name={}", filename);

        s3.putObject(
                new PutObjectRequest(bucketName, filename, inputStream, null)
                .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        logger.info("Uploaded image. image_name={};url={}", filename, String.valueOf(s3.getUrl(bucketName, filename)));
    }

    @Override
    public void deleteImage(String filename) {
        s3.deleteObject(bucketName, filename);
    }
}
