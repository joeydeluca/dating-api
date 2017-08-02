package com.joe.dating.domain.user.models;

import com.joe.dating.common.Util;

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

    public boolean isSubscribedNewMailAlert() {
        return Util.getBooleanFromDb(isSubscribedNewMailAlert);
    }

    public void setIsSubscribedNewMailAlert(boolean isSubscribedNewMailAlert) {
        this.isSubscribedNewMailAlert = Util.getBooleanForDb(isSubscribedNewMailAlert);
    }

    public boolean isSubscribedNewFlirtAlert() {
        return Util.getBooleanFromDb(isSubscribedNewFlirtAlert);
    }

    public void setIsSubscribedNewFlirtAlert(boolean isSubscribedNewFlirtAlert) {
        this.isSubscribedNewFlirtAlert = Util.getBooleanForDb(isSubscribedNewFlirtAlert);
    }

    public boolean isSubscribedFavoritedMeAlert() {
        return Util.getBooleanFromDb(isSubscribedFavoritedMeAlert);
    }

    public void setIsSubscribedFavoritedMeAlert(boolean isSubscribedFavoritedMeAlert) {
        this.isSubscribedFavoritedMeAlert = Util.getBooleanForDb(isSubscribedFavoritedMeAlert);
    }
}
