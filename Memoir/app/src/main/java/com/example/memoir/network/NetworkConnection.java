package com.example.memoir.network;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkConnection {

    private OkHttpClient client = null;
    private String results;

    private static final String BASE_URL = "http://10.0.2.2:8080/MemoirDB/webresources/";

    public NetworkConnection(){
        client = new OkHttpClient();
    }

    public String login(String username, String password){
        String param = username + "/" + Encrypt.md5(password);
        final String methodPath = "memoir.credential/login/" + param ;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }



}
