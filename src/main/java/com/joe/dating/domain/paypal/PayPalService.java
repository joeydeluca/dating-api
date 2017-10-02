package com.joe.dating.domain.paypal;

import com.joe.dating.domain.payment.*;
import com.joe.dating.domain.user.User;
import com.joe.dating.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
public class PayPalService {
    private final Logger logger = LoggerFactory.getLogger(PayPalService.class);
    private final IPNRepository ipnRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final ProductPriceRepository productPriceRepository;
    private final UserRepository userRepository;

    public PayPalService(IPNRepository ipnRepository, SubscriptionRepository subscriptionRepository, ProductPriceRepository productPriceRepository, UserRepository userRepository) {
        this.ipnRepository = ipnRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.productPriceRepository = productPriceRepository;
        this.userRepository = userRepository;
    }

    @Async
    public void processIPN(String rawIPN, Map<String, String> paypalParams) {
        recordIPN(rawIPN, paypalParams);

        IPNPayment ipnPayment = new IPNPayment(paypalParams);

        if(!TransactionType.subscr_payment.equals(ipnPayment.getTransactionType())) {
            logger.info("Not processing paypal transaction type: {}", ipnPayment.getTransactionType());
            return;
        }
        if(!PaymentStatus.Completed.equals(ipnPayment.getPaymentStatus())) {
            logger.info("Paypal payment status is not Complete: " + ipnPayment.getPaymentStatus());
            return;
        }

        User user = getUser(ipnPayment.getUserId());
        ProductPrice productPrice = getProductPrice(ipnPayment.getItemNumber());
        Subscription subscription = getSubscription(user, productPrice, ipnPayment.getSubscrId());

        checkForDuplicateTransaction(subscription, ipnPayment.getTxnId());

        Transaction transaction = getNewTransaction(user, subscription, ipnPayment,productPrice);

        saveSubscriptionWithNewTransaction(subscription, transaction);

        setUserAsPaid(user);
    }

    private User getUser(Long userId) {
        User user = userRepository.findOne(userId);
        if(user == null) {
            throw new IllegalArgumentException("Could not find user for: " + userId);
        }

        return user;
    }

    private ProductPrice getProductPrice(Long productPriceId) {
        ProductPrice productPrice = productPriceRepository.findOne(productPriceId);
        if(productPrice == null) {
            throw new IllegalArgumentException("Could not find productprice: " + productPriceId);
        }
        return productPrice;
    }

    private Subscription getSubscription(User user, ProductPrice productPrice, String processorSubscriptionId) {
        Date startDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_YEAR, productPrice.getDurationDays());
        Date endDate = calendar.getTime();

        Subscription subscription = subscriptionRepository.findByUserId(user.getId());
        if(subscription == null) {
            logger.info("Creating new subscription for user {}", user.getId());
            subscription = new Subscription();
            subscription.setUser(user);
            subscription.setStartDate(startDate);
            subscription.setProcessorSubscriptionId(processorSubscriptionId);
        }
        subscription.setEndDate(endDate);
        return subscription;
    }

    private Transaction getNewTransaction(User user, Subscription subscription, IPNPayment ipnPayment, ProductPrice productPrice) {
        Transaction transaction = new Transaction();
        transaction.setDate(subscription.getStartDate());
        transaction.setUser(user);
        transaction.setAmountPaid(ipnPayment.getPaymentGross().intValue());
        transaction.setCurrency(ipnPayment.getCurrency());
        transaction.setProductPrice(productPrice);
        transaction.setProcessorTransactionId(ipnPayment.getTxnId());
        return transaction;
    }

    private void checkForDuplicateTransaction(Subscription subscription, String paypalTransactionId) {
        if(subscription.getTransactions().stream().filter(t -> paypalTransactionId.equals(t.getProcessorTransactionId())).count() == 1) {
            throw new RuntimeException("Duplicate paypal transaction. paypal transaction id = " + paypalTransactionId);
        }
    }

    private void setUserAsPaid(User user) {
        logger.info("Setting user as paid. user: {}", user.getId());
        user.setIsPaid(true);
        userRepository.save(user);
    }

    private void saveSubscriptionWithNewTransaction(Subscription subscription, Transaction transaction) {
        subscription.getTransactions().add(transaction);
        subscriptionRepository.save(subscription);
    }

    private void recordIPN(String rawIPN, Map<String, String> paypalParams) {
        IPNEntity ipnEntity = new IPNEntity(rawIPN, paypalParams.get(IPNPayment.PAYPAL_CUSTOM));
        ipnRepository.save(ipnEntity);
    }
}
