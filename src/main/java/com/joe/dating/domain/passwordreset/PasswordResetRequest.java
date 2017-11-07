package com.joe.dating.domain.passwordreset;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "password_reset_request")
public class PasswordResetRequest {
    @Id
    @Column(name = "request_id")
    private String requestId;

    @Column(name = "email")
    private String email;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "requested_date")
    private Date requestedDate;

    @Column(name = "reset_completed_date")
    private Date resetCompletedDate;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(Date requestedDate) {
        this.requestedDate = requestedDate;
    }

    public Date getResetCompletedDate() {
        return resetCompletedDate;
    }

    public void setResetCompletedDate(Date resetCompletedDate) {
        this.resetCompletedDate = resetCompletedDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
