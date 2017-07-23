package com.joe.dating.domain.user.models;

import com.joe.dating.domain.fields.ProfileField;

/**
 * Created by Joe Deluca on 11/23/2016.
 */
public enum ChildrenStatus implements ProfileField{
    NO("01"),
    YES_AT_HOME("02"),
    YES_SOMETIMES_AT_HOME("03"),
    YES_AWAY_FROM_HOME("04")
    ;

    private final String id;

    ChildrenStatus(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
