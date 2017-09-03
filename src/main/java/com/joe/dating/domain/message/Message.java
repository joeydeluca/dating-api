package com.joe.dating.domain.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joe.dating.domain.DatingEntity;
import com.joe.dating.domain.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "messages")
public class Message extends DatingEntity {
    @OneToOne()
    @JoinColumn(name = "from_user_id", updatable = false)
    private User fromUser;

    @OneToOne()
    @JoinColumn(name = "to_user_id", updatable = false)
    private User toUser;

    @Column(name = "subject")
    private String subject;

    @Column(name = "message")
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy")
    @Column(name = "send_date", columnDefinition="DATETIME", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDate;

    @Column(name = "read_date", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date readDate;

    @PrePersist
    public void before() {
        setSendDate(new Date());
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getReadDate() {
        return readDate;
    }

    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }
}
