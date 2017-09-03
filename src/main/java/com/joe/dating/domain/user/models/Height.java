package com.joe.dating.domain.user.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by Joe Deluca on 11/21/2016.
 */
@Embeddable
public class Height {
    @Column(name = "height_feet")
    private String feet;
    @Column(name = "height_inch")
    private String inches;

    public String getFeet() {
        return feet;
    }

    public void setFeet(String feet) {
        this.feet = feet;
    }

    public String getInches() {
        return inches;
    }

    public void setInches(String inches) {
        this.inches = inches;
    }
}
