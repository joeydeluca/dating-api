package com.joe.dating.cloud;

import java.io.InputStream;

public interface CloudStorage {

    String uploadImage(InputStream inputStream, String filename);
    void deleteImage(String url);
}
