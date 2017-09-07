package com.joe.dating.domain.paypal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class PayPalService {
    private final IPNRepository ipnRepository;

    public PayPalService(IPNRepository ipnRepository) {
        this.ipnRepository = ipnRepository;
    }

    @Async
    public void processIPN(String rawIpnMessage) {
        IPNMessage ipnMessage = parseIPN(rawIpnMessage);

        ipnRepository.save(ipnMessage);

        validateIPN(rawIpnMessage);

        processIPN(ipnMessage);
    }

    private IPNMessage parseIPN(String rawIpnMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        IPNMessage ipnMessage;
        try {
            ipnMessage = objectMapper.readValue(rawIpnMessage, IPNMessage.class);
        } catch (IOException e) {
            throw new RuntimeException("Could not parse paypal ipn message. message=" + rawIpnMessage);
        }
        ipnMessage.setRawIpn(rawIpnMessage);
        return ipnMessage;
    }

    private void validateIPN(String rawIpnMessage) {
        if(!isIPNValid(rawIpnMessage)) {
            throw new RuntimeException("PayPal ipn is not valid. IPN=" + rawIpnMessage);
        }
    }

    private boolean isIPNValid(String rawIpnMessage) {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject("", rawIpnMessage, String.class);
        return "VERIFIED".equals(result);
    }

    private void processIPN(IPNMessage ipnMessage) {

    }


}
