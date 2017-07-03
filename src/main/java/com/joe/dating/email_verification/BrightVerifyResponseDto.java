package com.joe.dating.email_verification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Joe Deluca on 11/30/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BrightVerifyResponseDto {
    public static final transient String STATUS_INVALID = "invalid";

    @JsonProperty("status")
    private String status;
    @JsonProperty("role_address")
    private boolean roleAddress;
    @JsonProperty("disposable")
    private boolean disposable;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isRoleAddress() {
        return roleAddress;
    }

    public void setRoleAddress(boolean roleAddress) {
        this.roleAddress = roleAddress;
    }

    public boolean isDisposable() {
        return disposable;
    }

    public void setDisposable(boolean disposable) {
        this.disposable = disposable;
    }
}
