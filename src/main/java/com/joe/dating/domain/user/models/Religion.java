package com.joe.dating.domain.user.models;

import com.joe.dating.domain.fields.ProfileField;

/**
 * Created by Joe Deluca on 11/23/2016.
 */
public enum Religion implements ProfileField {
    AGNOSTIC("01"),
    ATHIEST("02"),
    BUDDHIST("03"),
    CHRISTIAN("04"),
    HINDU("05"),
    JEWISH("06"),
    MUSLIM("07"),
    SPIRITUAL("08"),
    OTHER("09")
    ;

    private final String id;

    Religion(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
