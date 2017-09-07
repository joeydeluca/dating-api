package com.joe.dating.rest;

import com.joe.dating.domain.payment.ProductPriceRepository;
import com.joe.dating.domain.paypal.PayPalService;
import com.joe.dating.security.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentResource {
    private final Logger logger = LoggerFactory.getLogger(PaymentResource.class);

    private final PayPalService payPalService;
    private final AuthService authService;
    private final ProductPriceRepository productPriceRepository;
    private final PayPalProperties payPalProperties;

    public PaymentResource(PayPalService payPalService, AuthService authService, ProductPriceRepository productPriceRepository, PayPalProperties payPalProperties) {
        this.payPalService = payPalService;
        this.authService = authService;
        this.productPriceRepository = productPriceRepository;
        this.payPalProperties = payPalProperties;
    }

    @PostMapping("/ipn")
    public ResponseEntity<Void> ipn(@RequestBody String rawIpnMessage) {
        logger.info("IPN received. ipn={}", rawIpnMessage);

        payPalService.processIPN(rawIpnMessage);
        return ResponseEntity.ok().build();
    }

    @GetMapping("request")
    public ResponseEntity<PaymentPageData> request(@RequestHeader(value = "authorization") String authToken) {
        this.authService.verifyToken(authToken);

        PaymentPageData paymentPageData = new PaymentPageData();
        paymentPageData.setProductPrices(productPriceRepository.findAll());
        paymentPageData.setPaypalBusinessName(payPalProperties.getPaypalBusinessName());
        paymentPageData.setPaypalCancelUrl(payPalProperties.getPaypalCancelUrl());
        paymentPageData.setPaypalNotifyUrl(payPalProperties.getPaypalNotifyUrl());
        paymentPageData.setPaypalPurchaseUrl(payPalProperties.getPaypalPurchaseUrl());
        paymentPageData.setPaypalReturnUrl(payPalProperties.getPaypalReturnUrl());

        return ResponseEntity.ok(paymentPageData);

    }
}
