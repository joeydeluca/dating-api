package com.joe.dating.domain.payment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joe.dating.domain.DatingEntity;
import com.joe.dating.domain.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "subscriptions")
public class Subscription extends DatingEntity {
    @OneToOne()
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "processor_subscription_id")
    private String processorSubscriptionId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Transaction> getTransactions() {
        if(transactions == null) {
            transactions = new ArrayList<>();
        }

        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getProcessorSubscriptionId() {
        return processorSubscriptionId;
    }

    public void setProcessorSubscriptionId(String processorSubscriptionId) {
        this.processorSubscriptionId = processorSubscriptionId;
    }
}
