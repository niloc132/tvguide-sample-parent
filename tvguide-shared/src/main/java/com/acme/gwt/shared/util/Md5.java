package com.acme.gwt.shared.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by IntelliJ IDEA.
 * User: jim
 * Date: 3/11/11
 * Time: 11:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class Md5 {
  public static final String HTTP = "http://";
  public static final String[] GRAVPREFIX = {HTTP, HTTP + "0.", HTTP + "1.",
      HTTP + "2.", HTTP + "www.",};
  public static int c;

  public static String md5Hex(String input) {
    MessageDigest md5;

    try {
      md5 = MessageDigest.getInstance("MD5");
      md5.reset();
      md5.update(input.getBytes());

      byte messageDigest[] = md5.digest();
      final int length = messageDigest.length;
      final Byte[] bytes = new Byte[length];
      for (int i = 0; i < bytes.length; i++) {
        bytes[i] = messageDigest[i];

      }

      return rgb2hex(bytes);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace(); //Todo: verify for a purpose
    }
    return null;
  }

  public static <T extends Number> String rgb2hex(final String prefix, T... c) {
    final StringBuilder sb = prefix == null
        ? new StringBuilder()
        : new StringBuilder(prefix);
    for (T b : c) {
      sb.append(Integer.toHexString(((b.intValue())) & 0xff | 0x100)
          .substring(1));
    }
    return sb.toString();
  }

  public static <T extends Number> String rgb2hex(T... c) {
    return rgb2hex(null, c);
  }
}
