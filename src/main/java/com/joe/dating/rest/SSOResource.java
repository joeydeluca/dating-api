package com.joe.dating.rest;

import com.joe.dating.domain.user.User;
import com.joe.dating.domain.user.UserService;
import com.joe.dating.security.AuthContext;
import com.joe.dating.security.AuthService;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;

@RestController
@RequestMapping("/api/sso")
public class SSOResource {
    private static final Logger logger = LoggerFactory.getLogger(SSOResource.class);

    private final AuthService authService;
    private final UserService userService;
    private final String discourseSSOSecret;

    public SSOResource(AuthService authService, UserService userService, @Value("${discourse.sso-secret}") String discourseSSOSecret) {
        this.authService = authService;
        this.userService = userService;
        this.discourseSSOSecret = discourseSSOSecret;
    }

    @GetMapping("discourse-callback")
    public RedirectView ssoCallback(@RequestParam String sso, @RequestParam String sig, @RequestParam String token) {
        RedirectView redirectView = new RedirectView();

        try {
            AuthContext authContext = this.authService.verifyToken(token);

            String incomingPayload = URLDecoder.decode(new String(Base64.getDecoder().decode(sso)), "UTF-8");

            MultiValueMap<String, String> parameters = UriComponentsBuilder.fromUriString("/?" + incomingPayload).build().getQueryParams();
            String nonce = parameters.getFirst("nonce");

            if (sig.equals(hmacSHA256(sso,discourseSSOSecret))) {
                User user = userService.findOne(authContext.getUserId());
                String payload =
                        "nonce=" + urlencode(nonce) + "&" +
                        "email=" + urlencode(user.getEmail()) + "&" +
                        "external_id=" + user.getId() + "&" +
                        "username=" + urlencode(user.getUsername()) + "&" +
                        "avatar_url=" + urlencode(user.getProfile().getProfilePhotoUrl());

                payload = Base64.getEncoder().encodeToString(payload.getBytes("UTF-8"));

                String signedPayload = hmacSHA256(payload, discourseSSOSecret);

                redirectView.setUrl(String.format("%s?sso=%s&sig=%s", parameters.getFirst("return_sso_url"), payload, signedPayload));
                return redirectView;

            } else {
                throw new RuntimeException("Sig not match, cannot sso");
            }

        } catch (Exception e) {
            logger.error("Bad JWT token in Discourse SSO Callback URL", e);
            redirectView.setUrl("https://www.uglyschmucks.com");
            return redirectView;
        }
    }

    private String hmacSHA256(String data, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKey);
        byte[] hmacData = mac.doFinal(data.getBytes("UTF-8"));
        return Hex.encodeHexString(hmacData);
    }

    private String urlencode(String input) throws UnsupportedEncodingException {
        return URLEncoder.encode(input, "UTF-8");
    }



}
