package com.joe.dating.domain.user.models;

import com.joe.dating.domain.fields.ProfileField;

/**
 * Created by Joe Deluca on 11/23/2016.
 */
public enum HairColor implements ProfileField {
    BLACK("01"),
    BLONDE("02"),
    DARK_BLONDE("03"),
    LIGHT_BROWN("04"),
    DARK_BROWN("05"),
    AUBURN_RED("06"),
    GREY("07"),
    SALT_PEPPER("08")
    ;

    private final String id;

    HairColor(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
