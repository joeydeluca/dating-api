package com.joe.dating.rest;

import com.joe.dating.domain.payment.ProductPriceRepository;
import com.joe.dating.domain.paypal.PayPalService;
import com.joe.dating.security.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static com.joe.dating.config.CacheConfig.PAYMENT_CACHE;

@RestController
@RequestMapping("/api/payment")
public class PaymentResource {
    private final Logger logger = LoggerFactory.getLogger(PaymentResource.class);
    private final static String RESP_VERIFIED = "VERIFIED";
    private final static String PARAM_NAME_CMD = "cmd";
    private final static String PARAM_VAL_CMD = "_notify-validate";
    private String paypalVerifyUrl;
    private boolean enablePaypalIpnVerification;

    private final PayPalService payPalService;
    private final AuthService authService;
    private final ProductPriceRepository productPriceRepository;
    private final PayPalProperties payPalProperties;

    public PaymentResource(PayPalService payPalService,
                           AuthService authService,
                           ProductPriceRepository productPriceRepository,
                           PayPalProperties payPalProperties,
                           @Value("${paypal.verify-url}") String paypalVerifyUrl,
                           @Value("${paypal.enable-ipn-verification}") boolean enablePaypalIpnVerification) {
        this.payPalService = payPalService;
        this.authService = authService;
        this.productPriceRepository = productPriceRepository;
        this.payPalProperties = payPalProperties;
        this.paypalVerifyUrl = paypalVerifyUrl;
        this.enablePaypalIpnVerification = enablePaypalIpnVerification;
    }

    @PostMapping("/ipn")
    public ResponseEntity<Void> ipn(@RequestBody String rawBody, HttpServletRequest request) {
        logger.info("IPN received. ipn={}", rawBody);

        try {
            Map<String, String> paramsForService = new HashMap<>();
            MultiValueMap<String, String> paramsForHttp = new LinkedMultiValueMap<>();

            setParams(request, paramsForService, paramsForHttp);

            if (verifyWithPayPal(paramsForHttp)) {
                payPalService.processIPN(rawBody, paramsForService);
            }

        } catch (Exception e) {
            logger.error("IPN failed. ipn={}", rawBody);
        }

        return ResponseEntity.ok().build();
    }

    private void setParams(HttpServletRequest request, Map<String, String> paramsForService, MultiValueMap<String, String> paramsForHttp) {
        paramsForHttp.add(PARAM_NAME_CMD, PARAM_VAL_CMD);
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String param = names.nextElement();
            String value = request.getParameter( param );
            paramsForHttp.add(param, value);
            paramsForService.put(param, value);
        }
    }

    private boolean verifyWithPayPal(MultiValueMap<String, String> paramsForHttp) {
        if(!enablePaypalIpnVerification) {
            logger.warn("Paypal IPN verification is disabled. Skipping verification.");
            return true;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(paramsForHttp, headers);
        ResponseEntity<String> response = new RestTemplate().postForEntity( paypalVerifyUrl, httpEntity , String.class);

        if(response == null) {
            logger.error("No response from paypal when validating IPN");
            return false;
        }

        if(!RESP_VERIFIED.equals(response.getBody())) {
            logger.error("Paypal IPN validation failed");
            return false;
        }

        return true;
    }

    @Cacheable(cacheNames = PAYMENT_CACHE, key = "#root.methodName")
    @GetMapping("/request")
    public ResponseEntity<PaymentPageData> request(@RequestHeader(value = "authorization") String authToken) {
        this.authService.verifyToken(authToken);

        PaymentPageData paymentPageData = new PaymentPageData();
        paymentPageData.setProductPrices(productPriceRepository.findAll());
        paymentPageData.setPaypalBusinessName(payPalProperties.getPaypalBusinessName());
        paymentPageData.setPaypalCancelUrl(payPalProperties.getPaypalCancelUrl());
        paymentPageData.setPaypalNotifyUrl(payPalProperties.getPaypalNotifyUrl());
        paymentPageData.setPaypalPurchaseUrl(payPalProperties.getPaypalPurchaseUrl());
        paymentPageData.setPaypalReturnUrl(payPalProperties.getPaypalReturnUrl());
        paymentPageData.setPaypalUnsubscribeUrl(payPalProperties.getPaypalUnsubscribeUrl());

        return ResponseEntity.ok(paymentPageData);

    }
}
