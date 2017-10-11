package com.joe.dating.rest;

import com.joe.dating.cloud.CloudStorage;
import com.joe.dating.domain.photo.Photo;
import com.joe.dating.domain.photo.PhotoRepository;
import com.joe.dating.domain.photo.PhotoService;
import com.joe.dating.security.AuthContext;
import com.joe.dating.security.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Joe Deluca on 11/21/2016.
 */
@RestController
@RequestMapping("/api/photos")
public class PhotoResource {

    private PhotoService photoService;
    private AuthService authService;

    public PhotoResource(PhotoService photoService, AuthService authService, PhotoRepository photoRepository, CloudStorage cloudStorage) {
        this.photoService = photoService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<Photo> handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestHeader(value = "authorization") String authToken) throws IOException {

        AuthContext authContext = this.authService.verifyToken(authToken);

        Photo photo = photoService.createPhoto(authContext.getUserId(), file);

        return ResponseEntity.ok(photo);
    }

    @PutMapping("/{id}/crop")
    public ResponseEntity<Photo> crop(
            @PathVariable("id") Long photoId,
            @RequestHeader(value = "authorization") String authToken,
            @RequestBody PhotoCropDto photoCropDto) throws IOException {

        AuthContext authContext = this.authService.verifyToken(authToken);

        Photo photo = photoService.cropPhoto(
                authContext.getUserId(),
                photoId,
                photoCropDto.getX(),
                photoCropDto.getY(),
                photoCropDto.getWidth(),
                photoCropDto.getHeight()
        );

        return ResponseEntity.ok(photo);
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<Photo> setProfilePhoto(
            @PathVariable("id") Long photoId,
            @RequestHeader(value = "authorization") String authToken) throws IOException {

        AuthContext authContext = this.authService.verifyToken(authToken);

        Photo photo = photoService.setProfilePhoto(authContext.getUserId(), photoId);


        return ResponseEntity.ok(photo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Long photoId,
            @RequestHeader(value = "authorization") String authToken) throws IOException {

        AuthContext authContext = this.authService.verifyToken(authToken);

        photoService.deletePhoto(authContext.getUserId(), photoId);

        return ResponseEntity.ok().build();
    }
}
