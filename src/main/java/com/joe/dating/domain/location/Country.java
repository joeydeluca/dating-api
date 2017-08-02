package com.joe.dating.domain.location;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Joe Deluca on 11/23/2016.
 */
@Entity(name = "Countries")
public class Country {
    @Column(name = "COUNTRYID", updatable = false, insertable = false)
    @Id
    private String countryId;

    @Column(name = "Country", updatable = false, insertable = false)
    private String countryName;

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
