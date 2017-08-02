package com.joe.dating.domain.user.models;

import com.joe.dating.domain.fields.ProfileField;

/**
 * Created by Joe Deluca on 11/23/2016.
 */
public enum EyeColor implements ProfileField {
    BLACK("01"),
    BLUE("02"),
    BROWN("03"),
    GREY("04"),
    GREEN("05"),
    HAZEL("06");

    private final String id;

    EyeColor(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
