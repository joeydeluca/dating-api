package com.joe.dating.domain.user.models;

import com.joe.dating.domain.fields.ProfileField;

/**
 * Created by Joe Deluca on 11/23/2016.
 */
public enum Education implements ProfileField {
    high_school("01"),
    some_college("02"),
    associates("03"),
    bachelors("04"),
    graduate("05"),
    phd("06");

    private final String id;

    Education(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
