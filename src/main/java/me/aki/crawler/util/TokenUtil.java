package me.aki.crawler.util;

import java.util.UUID;

/**
 * Created by Akis on 5/9/2017.
 */
public class TokenUtil {

    private static String modulus = "00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b725152b3ab17a876" +
            "aea8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbda92557c93870114af6c9d05c4f7f0c3685b7a46bee2559325" +
            "75cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7";

    private static String nonce = "0CoJUm6Qyw8W8jud";
    private static String pubKey = "010001";
    private static String text = "{\"username\": \"\", \"password\": \"\", \"rememberLogin\": \"true\"}"; // 模拟用户登录


    public static String genSecretKey() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }

    public static String genEncText(String key) {
        try {
            return AES.encrypt(AES.encrypt(text, nonce), key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String genEncSecKey(String key) {
        return RSA.encrypt(key, pubKey, modulus);
    }


    public static void main(String[] args) throws Exception {
        String key = "1234567890123456";
        String src = "djhrteimkvndahtreafmdfa";


        System.out.println(RSA.encrypt(src, pubKey, modulus));
    }

}
