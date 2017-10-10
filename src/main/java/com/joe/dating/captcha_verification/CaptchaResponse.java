package com.joe.dating.captcha_verification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptchaResponse {
    private boolean success;
    private String hostname;
    @JsonProperty("error-codes")
    private List<String> errorCodes;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public List<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(List<String> errorCodes) {
        this.errorCodes = errorCodes;
    }

    @Override
    public String toString() {
        return "CaptchaResponse{" +
                "success=" + success +
                ", hostname='" + hostname + '\'' +
                ", errorCodes=" + errorCodes != null ? Arrays.toString(errorCodes.toArray()) : "" +
                '}';
    }
}
