package com.joe.dating.domain.user.models;

import com.joe.dating.domain.fields.ProfileField;

/**
 * Created by Joe Deluca on 11/23/2016.
 */
public enum HeightFeet implements ProfileField {
    ONE("01"),
    TWO("02"),
    THREE("03"),
    FOUR("04"),
    FIVE("05"),
    SIX("06")
    ;

    private final String id;

    HeightFeet(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
