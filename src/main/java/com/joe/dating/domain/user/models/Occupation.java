package com.joe.dating.domain.user.models;

import com.joe.dating.domain.fields.ProfileField;

/**
 * Created by Joe Deluca on 11/23/2016.
 */
public enum Occupation implements ProfileField {
    FIELD1("01"),
    FIELD2("02"),
    FIELD3("03"),
    FIELD4("04"),
    FIELD5("05"),
    FIELD6("06"),
    FIELD7("07"),
    FIELD8("08"),
    FIELD9("09"),
    FIELD10("10"),
    FIELD11("11"),
    FIELD12("12"),
    FIELD13("13"),
    FIELD14("14"),
    FIELD15("15"),
    FIELD16("16"),
    FIELD17("17"),
    FIELD18("18"),
    FIELD19("19"),
    FIELD20("20"),
    FIELD21("21")
    ;

    private final String id;

    Occupation(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
