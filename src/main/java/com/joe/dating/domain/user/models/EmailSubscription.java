package com.joe.dating.domain.user.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.joe.dating.common.Util;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by Joe Deluca on 11/24/2016.
 */
@Embeddable
public class EmailSubscription {
    @Column(name = "is_subscribed_new_mail")
    private String isSubscribedNewMailAlert;

    @Column(name = "is_subscribed_new_flirt")
    private String isSubscribedNewFlirtAlert;

    @Column(name = "is_subscribed_favorited_me_alert")
    private String isSubscribedFavoritedMeAlert;

    @JsonProperty(value="isSubscribedNewMailAlert")
    public boolean isSubscribedNewMailAlert() {
        return StringUtils.isEmpty(isSubscribedNewMailAlert) || Util.getBooleanFromDb(isSubscribedNewMailAlert);
    }

    public void setIsSubscribedNewMailAlert(boolean isSubscribedNewMailAlert) {
        this.isSubscribedNewMailAlert = Util.getBooleanForDb(isSubscribedNewMailAlert);
    }

    @JsonProperty(value="isSubscribedNewFlirtAlert")
    public boolean isSubscribedNewFlirtAlert() {
        return StringUtils.isEmpty(isSubscribedNewFlirtAlert) || Util.getBooleanFromDb(isSubscribedNewFlirtAlert);
    }

    public void setIsSubscribedNewFlirtAlert(boolean isSubscribedNewFlirtAlert) {
        this.isSubscribedNewFlirtAlert = Util.getBooleanForDb(isSubscribedNewFlirtAlert);
    }

    @JsonProperty(value="isSubscribedFavoritedMeAlert")
    public boolean isSubscribedFavoritedMeAlert() {
        return StringUtils.isEmpty(isSubscribedFavoritedMeAlert) || Util.getBooleanFromDb(isSubscribedFavoritedMeAlert);
    }

    public void setIsSubscribedFavoritedMeAlert(boolean isSubscribedFavoritedMeAlert) {
        this.isSubscribedFavoritedMeAlert = Util.getBooleanForDb(isSubscribedFavoritedMeAlert);
    }
}
