package com.joe.dating.domain.user.models;

import com.joe.dating.domain.fields.ProfileField;

/**
 * Created by Joe Deluca on 11/23/2016.
 */
public enum BodyType implements ProfileField {
    SLENDER("01"),
    AVERAGE("02"),
    ATHLETIC("03"),
    HEAVYSET("04"),
    EXTRA_POUNDS("05"),
    STOCKY("06");

    private final String id;

    BodyType(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
