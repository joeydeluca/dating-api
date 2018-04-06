package com.joe.dating.domain.paypal;

import org.springframework.util.StringUtils;

import java.util.Map;

public class IPNPayment {
    public final static String PAYPAL_CUSTOM = "custom";
    public final static String PAYPAL_TXN_TYPE = "txn_type";
    public final static String PAYPAL_PAYMENT_STATUS = "payment_status";
    public final static String PAYPAL_ITEM_NUMBER = "item_number";
    public final static String PAYPAL_SUBSCR_ID = "subscr_id";
    public final static String PAYPAL_PAYMENT_GROSS = "payment_gross";
    public final static String PAYPAL_CURRENCY = "currency";
    public final static String PAYPAL_MC_CURRENCY = "mc_currency";
    public final static String PAYPAL_TXN_ID = "txn_id";

    private TransactionType transactionType;
    private PaymentStatus paymentStatus;
    private Long userId;
    private Long itemNumber;
    private String subscrId;
    private Double paymentGross;
    private String currency;
    private String txnId;

    public IPNPayment(Map<String, String> params) {
        transactionType = TransactionType.valueOf(params.get(PAYPAL_TXN_TYPE));
        paymentStatus = params.containsKey(PAYPAL_PAYMENT_STATUS) ? PaymentStatus.valueOf(params.get(PAYPAL_PAYMENT_STATUS)) : null;
        currency = getCurrencyFromIPN(params);
        itemNumber = Long.parseLong(params.get(PAYPAL_ITEM_NUMBER));
        subscrId = params.get(PAYPAL_SUBSCR_ID);
        userId = params.containsKey(PAYPAL_CUSTOM) ? Long.parseLong(params.get(PAYPAL_CUSTOM)) : Long.valueOf(0);
        paymentGross = params.containsKey(PAYPAL_PAYMENT_GROSS) ? Double.parseDouble(params.get(PAYPAL_PAYMENT_GROSS)) : Double.valueOf(0);
        txnId = params.get(PAYPAL_TXN_ID);
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getItemNumber() {
        return itemNumber;
    }

    public String getSubscrId() {
        return subscrId;
    }

    public Double getPaymentGross() {
        return paymentGross;
    }

    public String getCurrency() {
        return currency;
    }

    public String getTxnId() {
        return txnId;
    }

    private String getCurrencyFromIPN(Map<String, String> params) {
        if(!StringUtils.isEmpty(params.get(PAYPAL_CURRENCY))) {
            return params.get(PAYPAL_CURRENCY);
        }

        if(!StringUtils.isEmpty(params.get(PAYPAL_MC_CURRENCY))) {
            return params.get(PAYPAL_MC_CURRENCY);
        }

        return "USD";
    }
}
