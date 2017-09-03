package com.joe.dating.cloud;

import com.google.cloud.storage.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class GoogleCloudStorage implements CloudStorage {

    private final Logger logger = LoggerFactory.getLogger(GoogleCloudStorage.class);
    private final String bucketName;

    public GoogleCloudStorage(@Value("${gcp.storage.bucket-name}") String bucketName) {
        this.bucketName = bucketName;
    }

    @Override
    public String uploadImage(InputStream inputStream, String filename) {
        logger.info("Uploading image to GCP. image_name={}", filename);
        Storage storage = StorageOptions.getDefaultInstance().getService();
        BlobInfo blobInfo =
                storage.create(
                        BlobInfo
                                .newBuilder(bucketName, filename)
                                // Modify access list to allow all users with link to read file
                                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
                                .build(),
                        inputStream);
        logger.info("Uploaded image. image_name={};url={}", filename, blobInfo.getMediaLink());
        return blobInfo.getMediaLink();
    }

    @Override
    public void deleteImage(String url) {
        logger.info("Attempting to delete image. url={}", url);

        if(url == null || url.isEmpty()) {
            logger.info("How does not delete which doesn't exist.");
            return;
        }

        String imageName = tryToParseNameFromUrl(url);
        if(imageName == null) {
            return;
        }

        BlobId blobId = BlobId.of(bucketName, imageName);

        Storage storage = StorageOptions.getDefaultInstance().getService();
        boolean result = storage.delete(blobId);
        if(result) {
            logger.info("Image deleted. blobId={}", blobId);
        } else {
            logger.error("Image not deleted. Probably did not exist. blobId={}", blobId);
        }

    }

    private String tryToParseNameFromUrl(String url) {
        final String startSequence = "/o/";
        final String endSequence = "?";

        if(!url.contains(startSequence) || !url.contains(endSequence)) {
            logger.error("I cannot parse a name from this url. url={}", url);
            return null;
        }

        return url.substring(url.lastIndexOf(startSequence) + startSequence.length(), url.lastIndexOf(endSequence));
    }
}
