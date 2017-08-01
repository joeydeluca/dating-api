package com.joe.dating.domain.location;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Joe Deluca on 11/23/2016.
 */
@Entity(name = "Cities")
public class City {
    @Column(name = "CITYID", updatable = false, insertable = false)
    @Id
    private String cityId;

    @Column(name = "City", updatable = false, insertable = false)
    private String cityName;

    @Column(name = "REGIONID", updatable = false, insertable = false)
    private String regionId;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }
}
