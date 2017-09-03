package com.joe.dating.domain.location;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Joe Deluca on 11/23/2016.
 */
@Entity(name = "Regions")
public class Region {
    @Column(name = "REGIONID", updatable = false, insertable = false)
    @Id
    private String regionId;

    @Column(name = "Region", updatable = false, insertable = false)
    private String regionName;

    @JsonIgnore
    @Column(name = "COUNTRYID", updatable = false, insertable = false)
    private String countryId;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }
}
