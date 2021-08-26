package com.wizeline.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.wizeline.exception.WizeLineException;

public class Token {
    private final String SECRET_KEY = "my2w7wjd7yXF64FIADfJxNs1oupTGAuW";
    private final String HMAC_SHA_S256 = "HmacSHA256";

    public String createJWT(String role) {
        try {
            byte[] hash = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
            Mac sha256Hmac = Mac.getInstance(HMAC_SHA_S256);

            SecretKeySpec secretKey = new SecretKeySpec(hash, HMAC_SHA_S256);
            sha256Hmac.init(secretKey);

            byte[] signedBytes = sha256Hmac.doFinal(role.getBytes(StandardCharsets.UTF_8));
            return encode(signedBytes);
        } catch (Exception ex) {
            throw new WizeLineException("Creation token exception. Error: " + ex.getMessage());
        }
    }

    private String encode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}