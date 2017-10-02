package com.joe.dating.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class PayPalProperties {
    @Value("${paypal.purchase-url}")
    private String paypalPurchaseUrl;
    @Value("${paypal.notify-url}")
    private String paypalNotifyUrl;
    @Value("${paypal.cancel-url}")
    private String paypalCancelUrl;
    @Value("${paypal.return-url}")
    private String paypalReturnUrl;
    @Value("${paypal.unsubscribe-url}")
    private String paypalUnsubscribeUrl;
    @Value("${paypal.business-name}")
    private String paypalBusinessName;

    public String getPaypalPurchaseUrl() {
        return paypalPurchaseUrl;
    }

    public String getPaypalNotifyUrl() {
        return paypalNotifyUrl;
    }

    public String getPaypalCancelUrl() {
        return paypalCancelUrl;
    }

    public String getPaypalReturnUrl() {
        return paypalReturnUrl;
    }

    public String getPaypalBusinessName() {
        return paypalBusinessName;
    }

    public String getPaypalUnsubscribeUrl() {
        return paypalUnsubscribeUrl;
    }
}
