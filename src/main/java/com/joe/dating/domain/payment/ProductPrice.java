package com.joe.dating.domain.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joe.dating.common.Util;
import com.joe.dating.domain.DatingEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity(name = "product_prices")
public class ProductPrice extends DatingEntity {
    @Column(name = "durration_text", updatable = false, unique = true)
    private String durationText;
    @Column(name = "price", updatable = false, unique = true)
    private Double price;
    @Column(name = "currency", updatable = false, unique = true)
    private String currency;
    @JsonIgnore
    @Column(name = "durration_days", updatable = false, unique = true)
    private int durationDays;
    @Column(name = "position_weight", updatable = false, unique = true)
    private int positionWeight;
    @Column(name = "is_featured", updatable = false, unique = true)
    private String isFeatured;
    @Column(name = "is_enabled", updatable = false, unique = true)
    private String isEnabled;

    @Transient
    private int durationMonths;

    public String getDurationText() {
        return durationText;
    }

    public void setDurationText(String durationText) {
        this.durationText = durationText;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    public int getPositionWeight() {
        return positionWeight;
    }

    public void setPositionWeight(int positionWeight) {
        this.positionWeight = positionWeight;
    }

    public void setIsFeatured(String isFeatured) {
        this.isFeatured = isFeatured;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public boolean getIsFeatured() {
        return Util.getBooleanFromDb(isFeatured);
    }

    public void setIsFeatured(boolean isFeatured) {
        this.isFeatured = Util.getBooleanForDb(isFeatured);
    }

    public boolean getIsEnabled() {
        return Util.getBooleanFromDb(isEnabled);
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = Util.getBooleanForDb(isEnabled);
    }

    public int getDurationMonths() {
        return durationDays / 30;
    }
}
