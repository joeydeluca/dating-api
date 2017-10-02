package com.joe.dating.domain.paypal;

import com.joe.dating.domain.DatingEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity(name = "ipn_messages")
public class IPNEntity extends DatingEntity {
    @Column(name = "ipn", length = 2000)
    private String ipn;
    @Column(name = "date")
    private Date date;
    @Column(name = "custom")
    private String custom;

    public IPNEntity() {
    }

    public IPNEntity(String ipn, String custom) {
        this.ipn = ipn;
        this.date = new Date();
        this.custom = custom;
    }

    public String getIpn() {
        return ipn;
    }

    public void setIpn(String ipn) {
        this.ipn = ipn;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
