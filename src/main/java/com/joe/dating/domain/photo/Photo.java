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
    @Column(name = "profile_id", updatable = false)
    private Long profileId;

    @Column(name = "medium_filename")
    private String mediumUrl;

    @Column(name = "large_filename")
    private String largeUrl;

    @Column(name = "is_profile_photo")
    private String isProfilePhoto;

    @Column(name = "insert_date", columnDefinition="DATETIME", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertDate;

    public Photo() {
        setProfilePhoto(false);
    }

    @PrePersist
    public void before() {
        this.insertDate = new Date();
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getMediumUrl() {
        return mediumUrl;
    }

    public void setMediumUrl(String mediumUrl) {
        this.mediumUrl = mediumUrl;
    }

    public String getLargeUrl() {
        return largeUrl;
    }

    public void setLargeUrl(String largeUrl) {
        this.largeUrl = largeUrl;
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
}
