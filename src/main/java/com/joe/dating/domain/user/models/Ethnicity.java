package com.joe.dating.domain.user.models;

import com.joe.dating.domain.fields.ProfileField;

/**
 * Created by Joe Deluca on 11/23/2016.
 */
public enum Ethnicity implements ProfileField {
    ASIAN("01"),
    AFRICAN("02"),
    EAST_INDIAN("03"),
    LATINO("04"),
    MIDDLE_EASTERN("05"),
    NATIVE_AMERICAN("06"),
    PACIFIC_ISLANDER("07"),
    CAUCASIAN("08"),
    OTHER("09")
    ;

    private final String id;

    Ethnicity(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
