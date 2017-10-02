package com.joe.dating.email_verification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MailgunVerificationResponse {
    @JsonProperty("address")
    private String address;

    @JsonProperty("is_role_address")
    private boolean isRoleAddress;

    @JsonProperty("is_disposable_address")
    private boolean isDisposableAddress;

    @JsonProperty("is_valid")
    private boolean isValid;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isRoleAddress() {
        return isRoleAddress;
    }

    public void setRoleAddress(boolean roleAddress) {
        isRoleAddress = roleAddress;
    }

    public boolean isDisposableAddress() {
        return isDisposableAddress;
    }

    public void setDisposableAddress(boolean disposableAddress) {
        isDisposableAddress = disposableAddress;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}