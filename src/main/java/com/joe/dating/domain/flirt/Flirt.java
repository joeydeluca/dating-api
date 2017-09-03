package com.joe.dating.domain.flirt;

import com.joe.dating.domain.DatingEntity;
import com.joe.dating.domain.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "flirts")
public class Flirt extends DatingEntity {
    @OneToOne()
    @JoinColumn(name = "from_user_id", updatable = false)
    private User fromUser;

    @OneToOne()
    @JoinColumn(name = "to_user_id", updatable = false)
    private User toUser;

    @Column(name = "date", columnDefinition="DATETIME", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @PrePersist
    public void before() {
        this.date = new Date();
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
