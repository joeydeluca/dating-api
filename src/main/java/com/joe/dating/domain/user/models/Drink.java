package com.joe.dating.domain.user.models;

import com.joe.dating.domain.fields.ProfileField;

/**
 * Created by Joe Deluca on 11/23/2016.
 */
public enum Drink implements ProfileField {
    YES("01"),
    NO("02"),
    SOCIALLY("03")
    ;

    private final String id;

    Drink(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
