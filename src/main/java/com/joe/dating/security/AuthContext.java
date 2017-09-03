package com.joe.dating.security;

import com.joe.dating.domain.user.models.Gender;

public final class AuthContext {
    private String token;
    private Long userId;
    private boolean isPaid;
    private Gender gender;
    private Gender genderSeeking;
    private int completionStatus;
    private int siteId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Gender getGenderSeeking() {
        return genderSeeking;
    }

    public void setGenderSeeking(Gender genderSeeking) {
        this.genderSeeking = genderSeeking;
    }

    public int getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(int completionStatus) {
        this.completionStatus = completionStatus;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }
}
