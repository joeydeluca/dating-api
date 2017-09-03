package com.joe.dating.rest;

import com.joe.dating.domain.user.User;
import com.joe.dating.domain.user.models.Gender;
import com.joe.dating.domain.user.models.Profile;

import java.time.LocalDate;
import java.time.Period;

public class RecipientProfile {
    private Long userId;
    private String username;
    private Profile profile;
    private int age;
    private String profilePhotoUrl;
    private Gender genderSeeking;

    public RecipientProfile() {
        // no arg
    }

    public RecipientProfile(User user) {
        setUserId(user.getId());
        setProfile(user.getProfile());
        setUsername(user.getUsername());
        setGenderSeeking(user.getGenderSeeking());

        LocalDate birthDate = user.getBirthDate();
        LocalDate today = LocalDate.now();

        setAge(Period.between(birthDate, today).getYears());

        setProfilePhotoUrl(user.getProfile().getProfilePhotoUrl());
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public Gender getGenderSeeking() {
        return genderSeeking;
    }

    public void setGenderSeeking(Gender genderSeeking) {
        this.genderSeeking = genderSeeking;
    }
}
