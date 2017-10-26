package com.joe.dating.rest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joe.dating.domain.location.City;
import com.joe.dating.domain.location.Country;
import com.joe.dating.domain.location.Region;
import com.joe.dating.domain.user.User;
import com.joe.dating.domain.user.models.Profile;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class ProfileEvent {
    private Long userId;
    private String username;
    private int age;
    private String profilePhotoUrl;
    private Country country;
    private Region region;
    private City city;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy")
    private Date eventCreatedDate;


    public ProfileEvent() {
    }

    public ProfileEvent(User user, Date eventCreatedDate) {
        Profile profile = user.getProfile();

        setUserId(user.getId());
        setUsername(user.getUsername());
        setProfilePhotoUrl(profile.getProfilePhotoUrl());
        setCountry(profile.getCountry());
        setRegion(profile.getRegion());
        setCity(profile.getCity());
        setEventCreatedDate(eventCreatedDate);

        LocalDate today = LocalDate.now();
        LocalDate birthDate = user.getBirthDate();
        if(birthDate != null) {
            setAge(Period.between(birthDate, today).getYears());
        }

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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Date getEventCreatedDate() {
        return eventCreatedDate;
    }

    public void setEventCreatedDate(Date eventCreatedDate) {
        this.eventCreatedDate = eventCreatedDate;
    }
}
