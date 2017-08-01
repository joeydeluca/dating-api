package com.joe.dating.domain.user.models;

import com.joe.dating.domain.fields.ProfileField;

/**
 * Created by Joe Deluca on 11/23/2016.
 */
public enum Pet implements ProfileField {
    BIRDS("01"),
    CATS("02"),
    DOGS("03"),
    EXOTIC("04"),
    FISH("05"),
    HORSES("06"),
    OTHER("07")
    ;

    private final String id;

    Pet(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
