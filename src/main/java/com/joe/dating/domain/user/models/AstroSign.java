package com.joe.dating.domain.user.models;

import com.joe.dating.domain.fields.ProfileField;

/**
 * Created by Joe Deluca on 11/23/2016.
 */
public enum AstroSign implements ProfileField {
    CAPRICORN("01"),
    AQUARIUS("02"),
    PISCES("03"),
    ARIES("04"),
    TAURUS("05"),
    GEMINI("06"),
    CANCER("07"),
    LEO("08"),
    VIRGO("09"),
    LIBRA("10"),
    SCORPIO("11"),
    SAGITTARIUS("12")
    ;

    private final String id;

    AstroSign(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
