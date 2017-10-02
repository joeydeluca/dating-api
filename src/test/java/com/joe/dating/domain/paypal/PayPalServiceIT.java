package com.joe.dating.domain.paypal;

import com.joe.dating.domain.payment.*;
import com.joe.dating.domain.user.User;
import com.joe.dating.domain.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;

@Transactional
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PayPalServiceIT {
    @Autowired
    PayPalService payPalService;
    @Autowired
    IPNRepository ipnRepository;
    @Autowired
    SubscriptionRepository subscriptionRepository;
    @Autowired
    ProductPriceRepository productPriceRepository;
    @Autowired
    UserRepository userRepository;

    private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private long userId = 7978;

    private final String IPN = "mc_gross=49.99&protection_eligibility=Eligible&address_status=confirmed&payer_id=DE8NYAC3R44LG&address_street=1+Maire-Victorin&payment_date=18%3A12%3A39+Sep+26%2C+2017+PDT&payment_status=Completed&charset=windows-1252&address_zip=M5A+1E1&first_name=test&mc_fee=2.25&address_country_code=CA&address_name=test+buyer&notify_version=3.8&subscr_id=I-8VCJ4C6TLLNG&custom=7978&payer_status=verified&business=joeydeluca-facilitator%40hotmail.com&address_country=Canada&address_city=Toronto&verify_sign=AFcWxV21C7fd0v3bYYYRCpSSRl31A7-kPzTKV.JDZm4nZXbBkAnz6jU0&payer_email=joeydeluca-buyer%40hotmail.com&txn_id=61064249CG729423H&payment_type=instant&last_name=buyer&address_state=Ontario&receiver_email=joeydeluca-facilitator%40hotmail.com&payment_fee=2.25&receiver_id=HJT77MK8ZQTMA&txn_type=subscr_payment&item_name=6+Months+Membership&mc_currency=USD&item_number=2&residence_country=CA&test_ipn=1&transaction_subject=6+Months+Membership&payment_gross=49.99&ipn_track_id=4c459becf1";
    private final Map<String, String> IPN_PARAMS = toMap(IPN);

    @Before
    public void setup() {
    }

    @Test
    public void testNewSubscriptionPayment() {

        assertEquals(userRepository.getOne(userId).isPaid(), false);

        payPalService.processIPN(IPN, IPN_PARAMS);

        User user = userRepository.getOne(userId);
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        List<Transaction> transactions = subscription.getTransactions();
        ProductPrice productPrice = productPriceRepository.findOne(2l);

        assertEquals(true, user.isPaid());
        assertEquals(df.format(new Date()), df.format(subscription.getStartDate()));
        assertEquals(df.format(addDaysToDate(new Date(), productPrice.getDurationDays())), df.format(subscription.getEndDate()));
        assertEquals(IPN_PARAMS.get("subscr_id"), subscription.getProcessorSubscriptionId());
        assertEquals(user.getId(), subscription.getUser().getId());

        assertEquals(1, transactions.size());
    }

    private Map<String, String> toMap(String rawIpn) {
        Map<String, String> params = new HashMap<>();
        for(String keyvalue : rawIpn.split("&")) {
            String[] tuple = keyvalue.split("=");
            params.put(tuple[0], tuple[1]);
        }
        return params;
    }

    private Date addDaysToDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }
}
