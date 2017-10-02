package com.joe.dating.cloud;

import java.io.InputStream;

public interface CloudStorage {

    void uploadImage(InputStream inputStream, String filename);
    void deleteImage(String filename);
}
