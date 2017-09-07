package com.joe.dating.domain.paypal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.joe.dating.domain.DatingEntity;

import javax.persistence.Entity;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "ipn_messages")
public class IPNMessage extends DatingEntity {
    @JsonIgnore
    private String rawIpn;

    @JsonProperty("txn_type")
    private TransactionType transactionType;
    @JsonProperty("payment_status")
    private PaymentStatus paymentStatus;
    @JsonProperty("mc_gross")
    private String mcGross;
    @JsonProperty("mc_currency")
    private String mcCurrency;
    @JsonProperty("custom")
    private String custom;
    @JsonProperty("item_number")
    private String itemNumber;
    @JsonProperty("item_name")
    private String itemName;
    @JsonProperty("txn_id")
    private String txnId;
    @JsonProperty("txn_type")
    private String txnType;
    @JsonProperty("payer_id")
    private String payerId;
    @JsonProperty("payer_email")
    private String payerEmail;

    @JsonProperty("subscr_id")
    private String subscrId;
    @JsonProperty("subscr_date")
    private Date subscriptionDate;

    public String getRawIpn() {
        return rawIpn;
    }

    public void setRawIpn(String rawIpn) {
        this.rawIpn = rawIpn;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getMcGross() {
        return mcGross;
    }

    public void setMcGross(String mcGross) {
        this.mcGross = mcGross;
    }

    public String getMcCurrency() {
        return mcCurrency;
    }

    public void setMcCurrency(String mcCurrency) {
        this.mcCurrency = mcCurrency;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getPayerEmail() {
        return payerEmail;
    }

    public void setPayerEmail(String payerEmail) {
        this.payerEmail = payerEmail;
    }

    public String getSubscrId() {
        return subscrId;
    }

    public void setSubscrId(String subscrId) {
        this.subscrId = subscrId;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }
}
