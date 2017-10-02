package com.joe.dating.domain.photo;

import com.joe.dating.common.Util;
import com.joe.dating.domain.DatingEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Joe Deluca on 11/21/2016.
 */
@Entity(name = "photos")
public class Photo extends DatingEntity {

    public static final String basePhotoUrl = "https://s3.amazonaws.com/dating-bucket/";

    @Column(name = "profile_id", updatable = false)
    private Long profileId;

    @Column(name = "medium_filename")
    private String mediumFilename;

    @Column(name = "large_filename")
    private String largeFilename;

    @Column(name = "is_profile_photo")
    private String isProfilePhoto;

    @Column(name = "insert_date", columnDefinition="DATETIME", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertDate;

    @Column(name = "version")
    private int version = 0;

    @Transient
    private String mediumUrl;
    @Transient
    private String largeUrl;

    public Photo() {
        setProfilePhoto(false);
    }

    @PrePersist
    public void before() {
        this.insertDate = new Date();
    }

    public void incrementVersion() {
        this.version++;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getMediumFilename() {
        return mediumFilename;
    }

    public void setMediumFilename(String mediumFilename) {
        this.mediumFilename = mediumFilename;
    }

    public String getLargeFilename() {
        return largeFilename;
    }

    public void setLargeFilename(String largeFilename) {
        this.largeFilename = largeFilename;
    }

    public boolean isProfilePhoto() {
        return Util.getBooleanFromDb(isProfilePhoto);
    }

    public void setProfilePhoto(boolean profilePhoto) {
        isProfilePhoto = Util.getBooleanForDb(profilePhoto);
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public String getMediumUrl() {
        return mediumFilename != null ? basePhotoUrl + mediumFilename + "?v=" + version : null;
    }

    public void setMediumUrl(String mediumUrl) {
        this.mediumUrl = mediumUrl;
    }

    public String getLargeUrl() {
        return largeFilename != null ? basePhotoUrl + largeFilename + "?v=" + version : null;
    }

    public void setLargeUrl(String largeUrl) {
        this.largeUrl = largeUrl;
    }
}
