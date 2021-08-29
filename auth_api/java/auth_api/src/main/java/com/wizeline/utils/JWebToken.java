package com.wizeline.utils;

import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.wizeline.exception.WizeLineException;

public class JWebToken {
    private static final String SECRET_KEY = "my2w7wjd7yXF64FIADfJxNs1oupTGAuW";

    public static String createJWT(String role) {
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ","JWT")
                .claim("role",role)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY);

        return builder.compact();
    }

    public static Claims decodeJWT(String webToken) {
        System.out.println("decodeJWT: " + webToken);
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(webToken)
                .getBody();
    }
}