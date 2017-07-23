package com.joe.dating.domain.user.models;

import com.joe.dating.domain.fields.ProfileField;

/**
 * Created by Joe Deluca on 11/23/2016.
 */
public enum Language implements ProfileField{
    CHINESE("01"),
    ENGLISH("02"),
    FRENCH("03"),
    HINDI("04"),
    NORWEGIAN("05"),
    SPANISH("06"),
    URDU("07"),
    ARABIC("08"),
    DUTCH("09"),
    GERMAN("10"),
    HEBREW("11"),
    ITALIAN("12"),
    JAPANESE("13"),
    PORTUGUESE("14"),
    RUSSIAN("15"),
    SWEDISH("16"),
    TAGALOG("17"),
    OTHER("18")
    ;

    private final String id;

    Language(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
