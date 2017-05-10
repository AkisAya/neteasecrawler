package me.aki.crawler;

import okhttp3.*;

import java.io.IOException;

/**
 * Created by Akis on 5/9/2017.
 */
public class OkHttpUtil {

    private static OkHttpClient client = new OkHttpClient();

    public static String fetch(String url) {
        try {
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String post(String url, String encText, String encSecKey) {

        RequestBody requestBody = new FormBody.Builder()
                .add("params", encText)
                .add("encSecKey", encSecKey)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
