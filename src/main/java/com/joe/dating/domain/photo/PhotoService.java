package com.joe.dating.domain.photo;

import com.joe.dating.cloud.CloudStorage;
import com.joe.dating.domain.user.User;
import com.joe.dating.domain.user.UserRepository;
import com.joe.dating.domain.user.UserService;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Component
public class PhotoService {

    private final CloudStorage cloudStorage;
    private final PhotoRepository photoRepository;
    private final UserService userService;

    private final int PHOTO_INITIAL_UPLOAD_TARGET_SIZE = 1000;
    private final int PHOTO_MEDIUM_WIDTH = 191;
    private final int PHOTO_MEDIUM_HEIGHT = 212;

    public PhotoService(CloudStorage cloudStorage, PhotoRepository photoRepository, UserService userService) {
        this.cloudStorage = cloudStorage;
        this.photoRepository = photoRepository;
        this.userService = userService;
    }

    public Photo createPhoto(Long profileId, MultipartFile file) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        BufferedImage resizedBufferedImage = Scalr.resize(bufferedImage, Scalr.Method.ULTRA_QUALITY,  Scalr.Mode.FIT_TO_WIDTH, PHOTO_INITIAL_UPLOAD_TARGET_SIZE);

        Photo photo = new Photo();
        photo.setProfileId(profileId);
        photo.setProfilePhoto(false);
        photo = photoRepository.save(photo);

        String photoUrl = cloudStorage.uploadImage(getInputStream(resizedBufferedImage), getLargePhotoFilename(photo.getId()));

        photo.setLargeUrl(photoUrl);

        return photoRepository.save(photo);
    }

    public Photo cropPhoto(Long photoId, int x, int y, int width, int height) throws IOException {
        Photo photo = photoRepository.getOne(photoId);

        URL largePhotoUrl = new URL(photo.getLargeUrl());
        BufferedImage largePhoto = ImageIO.read(largePhotoUrl);

        BufferedImage largeBufferedImage = Scalr.crop(largePhoto, x, y, width, height);
        String largeUrl = cloudStorage.uploadImage(getInputStream(largeBufferedImage), getLargePhotoFilename(photoId));
        photo.setLargeUrl(largeUrl);

        BufferedImage mediumBufferedImage = resize(largeBufferedImage, PHOTO_MEDIUM_WIDTH, PHOTO_MEDIUM_HEIGHT);
        String mediumUrl = cloudStorage.uploadImage(getInputStream(mediumBufferedImage), getMediumPhotoFilename(photoId));
        photo.setMediumUrl(mediumUrl);

        return photoRepository.save(photo);
    }

    public Photo setProfilePhoto(Long userId, Long photoId) {
        Photo photo = photoRepository.getOne(photoId);

        if(!userId.equals(photo.getProfileId())) {
            throw new RuntimeException("Nice try");
        }

        User user = userService.findOne(userId);
        if(user.getProfile().getPhotos() != null) {
            for(Photo p : user.getProfile().getPhotos()) {
                if (p.isProfilePhoto()) {
                    p.setProfilePhoto(false);
                    photoRepository.save(p);
                }
            }
        }

        photo.setProfilePhoto(true);
        return photoRepository.save(photo);
    }

    public void deletePhoto(Long userId, Long photoId) {
        Photo photo = photoRepository.getOne(photoId);

        if(!userId.equals(photo.getProfileId())) {
            throw new RuntimeException("Nice try");
        }

        photoRepository.delete(photo);

        cloudStorage.deleteImage(photo.getLargeUrl());
        cloudStorage.deleteImage(photo.getMediumUrl());
    }

    private BufferedImage resize(BufferedImage bufferedImage, int targetWidth, int targetHeight) throws IOException {
        return Scalr.resize(bufferedImage, Scalr.Method.ULTRA_QUALITY,  Scalr.Mode.FIT_TO_WIDTH, targetWidth, targetHeight);
    }

    private InputStream getInputStream(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", os);
        return new ByteArrayInputStream(os.toByteArray());
    }

    private String getLargePhotoFilename(Long photoId) {
        return photoId + "_large.jpg";
    }

    private String getMediumPhotoFilename(Long photoId) {
        return photoId + "_medium.jpg";
    }

}
