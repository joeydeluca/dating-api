package com.joe.dating.domain.payment;

import com.joe.dating.domain.DatingEntity;
import com.joe.dating.domain.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "transactions")
public class Transaction extends DatingEntity {
    @OneToOne()
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @OneToOne()
    @JoinColumn(name = "product_price_id", updatable = false)
    private ProductPrice productPrice;

    @Column(name = "date")
    private Date date;

    @Column(name = "processor_transaction_id")
    private String processorTransactionId;

    @Column(name = "amount_paid")
    private int amountPaid;

    @Column(name = "currency")
    private String currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProductPrice getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(ProductPrice productPrice) {
        this.productPrice = productPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getProcessorTransactionId() {
        return processorTransactionId;
    }

    public void setProcessorTransactionId(String processorTransactionId) {
        this.processorTransactionId = processorTransactionId;
    }

    public int getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(int amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }
}
