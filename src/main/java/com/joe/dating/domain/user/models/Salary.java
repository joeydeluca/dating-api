package com.joe.dating.domain.user.models;

import com.joe.dating.domain.fields.ProfileField;

/**
 * Created by Joe Deluca on 11/23/2016.
 */
public enum Salary implements ProfileField {
    F1("01"),
    F2("02"),
    F3("03"),
    F4("04"),
    F5("05"),
    F6("06"),
    F7("07")
    ;

    private final String id;

    Salary(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
