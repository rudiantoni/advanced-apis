package com.myapps.advancedapijava.util;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptUtil {
  private CryptUtil() {
  }

  public static String encodeBase64(String text) {
    return Encoders.BASE64.encode(text.getBytes(StandardCharsets.UTF_8));
  }

  public static String decodeBase64(String text) {
    return new String(Decoders.BASE64.decode(text));
  }

  public static String hashSha256(String text) {
    try {
      MessageDigest msgDigest = MessageDigest.getInstance("SHA-256");
      byte[] hash = msgDigest.digest(text.getBytes(StandardCharsets.UTF_8));
      return bytesToHexStr(hash);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String bytesToHexStr(byte[] bytes) {
    StringBuilder builder = new StringBuilder();
    for (byte b : bytes) {
      builder.append(String.format("%02x", b));
    }
    return builder.toString();
  }

}
