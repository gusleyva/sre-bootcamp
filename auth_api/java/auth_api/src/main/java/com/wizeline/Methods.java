package com.wizeline;

import com.wizeline.model.User;
import com.wizeline.utils.DataSource;
import com.wizeline.utils.Token;

import com.wizeline.exception.WizeLineException;
import com.wizeline.exception.DataBaseConnectionException;

import java.sql.SQLException;
import java.lang.Exception;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    if (validateUser(password,user)){
      Token token = new Token();
      String jwtToken = token.createJWT(user.getRole());
      System.out.println("jwtToken " + jwtToken);
      return jwtToken;
    }else{
      throw new WizeLineException("It was not possible to validate the user, please validate the credentials.");
    }
  }

  public static String accessData(String authorization){
    return "test";
  }


  public static boolean validateUser(String password, User user){
    String encodedPassword = user.getPassword();
    String hashedPassword = getHashSHA512(password,user.getSalt());
    return encodedPassword.equals(hashedPassword);
  }

  public static String getHashSHA512(String StringToHash, String salt){
    String generatedPassword = null;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-512");
      byte[] bytes = md.digest(StringToHash.concat(salt).getBytes(StandardCharsets.UTF_8));
      generatedPassword = Hex.encodeHexString(bytes);
    } catch (Exception ex){
      throw new WizeLineException("Something went wrong !");
    }
    return generatedPassword;
  }
}

