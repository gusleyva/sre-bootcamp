package com.wizeline;

import com.wizeline.model.User;
import com.wizeline.utils.DataSource;
import com.wizeline.utils.JWebToken;

import com.wizeline.exception.WizeLineException;
import com.wizeline.exception.DataBaseConnectionException;
import com.wizeline.exception.UnauthorizedException;

import java.sql.SQLException;
import java.lang.Exception;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.jsonwebtoken.Claims;
import org.apache.commons.codec.binary.Hex;

public class Methods {
  public static String generateToken(String username, String password) {
    User user = null;
    try{
      user = DataSource.getUser(username);
    } catch (Exception ex){
      throw new DataBaseConnectionException("It was not possible to connect to the database and fetch the user. Error: " + ex.getMessage());
    }

    if(user == null) {
      throw new WizeLineException("User was NOT Found.");
    }

    if (validateCredentials(user, password)){
      return JWebToken.createJWT(user.getRole());
    }else{
      throw new WizeLineException("It was not possible to validate the user, please validate the credentials.");
    }
  }

  public static String accessData(String authorization){
    String protectedData = "You are under protected data";
    try {
        Claims claim = JWebToken.decodeJWT(authorization);
        if(claim.isEmpty()) {
          System.out.println("empty claim");
          throw new UnauthorizedException("Unauthenticated, invalid token.");
        }
    } catch (Exception exp){
      System.out.println("Exception: " + exp.getMessage());
      throw new UnauthorizedException("Unauthenticated, invalid token. Please validate the JWT token.");
    }

    return protectedData;
  }

  public static boolean validateCredentials(User user, String password){
    String encodedPassword = user.getPassword();
    String hashedPassword = getHashSHA512(password,user.getSalt());
    return encodedPassword.equals(hashedPassword);
  }

  public static String getHashSHA512(String passwordToHash, String salt){
    String generatedPassword = null;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-512");
      byte[] bytes = md.digest(passwordToHash.concat(salt).getBytes(StandardCharsets.UTF_8));
      generatedPassword = Hex.encodeHexString(bytes);
    } catch (Exception ex){
      throw new WizeLineException("Exception decoding password. Exception message: " + ex.getMessage());
    }
    return generatedPassword;
  }

}

