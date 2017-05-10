package me.aki.crawler.util;

import java.math.BigInteger;

/**
 * Created by Akis on 5/9/2017.
 */
public class RSA {
    public static String encrypt(String text, String pubKey, String modulus) {

        String reverseText = new StringBuilder(text).reverse().toString();
        BigInteger src = new BigInteger(stringToHex(reverseText), 16);
        BigInteger pub = new BigInteger(pubKey, 16);
        BigInteger modu = new BigInteger(modulus, 16);

        BigInteger res = src.modPow(pub, modu);
        return String.format("%0256x", res);
    }

    private static String stringToHex(String strPart) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strPart.length(); i++) {
            int ch = (int) strPart.charAt(i);
            String strHex = Integer.toHexString(ch);
            sb.append(strHex);
        }
        return sb.toString();
    }
}
