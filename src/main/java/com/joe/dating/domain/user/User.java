package com.joe.dating.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joe.dating.common.Util;
import com.joe.dating.domain.DatingEntity;
import com.joe.dating.domain.user.models.CompletionStatus;
import com.joe.dating.domain.user.models.EmailSubscription;
import com.joe.dating.domain.user.models.Gender;
import com.joe.dating.domain.user.models.Profile;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Joe Deluca on 11/21/2016.
 */
@Entity(name = "users")
public class User extends DatingEntity {
    @Column(name = "email", updatable = false, unique = true)
    private String email;

    @Column(name = "site_id", updatable = false)
    private int siteId;

    @Column(name = "username", updatable = false)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "is_paid")
    private String isPaid;

    @Column(name = "gender", updatable = false)
    private String gender;

    @Column(name = "gender_seeking", updatable = false)
    private String genderSeeking;

    @Column(name = "birth_date", columnDefinition="DATETIME", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;

    @Column(name = "created_date", updatable = false, columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "is_deleted")
    private String isDeleted;

    @Column(name = "ref_id")
    private String refId;

    @Column(name = "completion_status")
    private int completionStatus;

    @Embedded
    private Profile profile;

    @Embedded
    private EmailSubscription emailSubscription;

    @PrePersist
    public void before() {
        setCreatedDate(new Date());
        setCompletionStatus(CompletionStatus.INCOMPLETE);
        setIsDeleted(false);
        setIsPaid(false);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPaid() {
        return Util.getBooleanFromDb(isPaid);
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = Util.getBooleanForDb(isPaid);
    }

    public Gender getGender() {
        return "M".equals(gender) ? Gender.M : Gender.W;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender.name();
    }

    public Gender getGenderSeeking() {
        return "M".equals(genderSeeking) ? Gender.M : Gender.W;
    }

    public void setGenderSeeking(String genderSeeking) {
        this.genderSeeking = genderSeeking;
    }

    public void setGenderSeeking(Gender genderSeeking) {
        this.genderSeeking = genderSeeking.name();
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isDeleted() {
        return Util.getBooleanFromDb(isDeleted);
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = Util.getBooleanForDb(isDeleted);
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public int getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(CompletionStatus completionStatus) {
        this.completionStatus = completionStatus.getId();
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public EmailSubscription getEmailSubscription() {
        return emailSubscription;
    }

    public void setEmailSubscription(EmailSubscription emailSubscription) {
        this.emailSubscription = emailSubscription;
    }
}
