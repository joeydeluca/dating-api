package com.joe.dating.rest;

import com.joe.dating.domain.payment.ProductPrice;

import java.util.List;

public class PaymentPageData {
    private List<ProductPrice> productPrices;
    private String paypalPurchaseUrl;
    private String paypalNotifyUrl;
    private String paypalCancelUrl;
    private String paypalReturnUrl;
    private String paypalUnsubscribeUrl;
    private String paypalBusinessName;

    public List<ProductPrice> getProductPrices() {
        return productPrices;
    }

    public void setProductPrices(List<ProductPrice> productPrices) {
        this.productPrices = productPrices;
    }

    public String getPaypalPurchaseUrl() {
        return paypalPurchaseUrl;
    }

    public void setPaypalPurchaseUrl(String paypalPurchaseUrl) {
        this.paypalPurchaseUrl = paypalPurchaseUrl;
    }

    public String getPaypalNotifyUrl() {
        return paypalNotifyUrl;
    }

    public void setPaypalNotifyUrl(String paypalNotifyUrl) {
        this.paypalNotifyUrl = paypalNotifyUrl;
    }

    public String getPaypalCancelUrl() {
        return paypalCancelUrl;
    }

    public void setPaypalCancelUrl(String paypalCancelUrl) {
        this.paypalCancelUrl = paypalCancelUrl;
    }

    public String getPaypalReturnUrl() {
        return paypalReturnUrl;
    }

    public void setPaypalReturnUrl(String paypalReturnUrl) {
        this.paypalReturnUrl = paypalReturnUrl;
    }

    public String getPaypalBusinessName() {
        return paypalBusinessName;
    }

    public void setPaypalBusinessName(String paypalBusinessName) {
        this.paypalBusinessName = paypalBusinessName;
    }

    public String getPaypalUnsubscribeUrl() {
        return paypalUnsubscribeUrl;
    }

    public void setPaypalUnsubscribeUrl(String paypalUnsubscribeUrl) {
        this.paypalUnsubscribeUrl = paypalUnsubscribeUrl;
    }
}
