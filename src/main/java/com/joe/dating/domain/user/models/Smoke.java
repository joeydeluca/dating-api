package com.joe.dating.domain.user.models;


import com.joe.dating.domain.fields.ProfileField;

/**
 * Created by Joe Deluca on 11/23/2016.
 */
public enum Smoke implements ProfileField {
    YES("01"),
    NO("02"),
    SOMETIMES("03"),
    QUITTING("04")
    ;

    private final String id;

    Smoke(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
