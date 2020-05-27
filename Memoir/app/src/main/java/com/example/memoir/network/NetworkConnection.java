package com.example.memoir.network;

import android.util.Log;

import com.example.memoir.entity.Credential;
import com.example.memoir.entity.Person;
import com.example.memoir.util.Encrypt;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkConnection {

    private static OkHttpClient client = null;
    private static String results;

    private static final String BASE_URL = "http://10.0.2.2:8080/MemoirDB/webresources/";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public NetworkConnection() {
        client = new OkHttpClient();
    }

    // login
    public String login(String username, String password) {
        String param = username + "/" + Encrypt.md5(password);
        final String methodPath = "memoir.credential/login/" + param;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                results = response.body().string();
            } else {
                results = "fail";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    // count person for calculate id
    public int countPerson() {
        final String methodPath = "memoir.person/count";
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.parseInt(results);
    }

    // add person
    //int id, String firstname, String surname, char gender, Date dob, String address, String state, int postcode, String email
    public String addPerson(Person person) {
        Gson gson = new Gson();
        String personJson = gson.toJson(person);
        String result = "";
        Log.i("personJson", personJson);
        final String methodPath = "memoir.person";
        RequestBody body = RequestBody.create(personJson, JSON);
        Request request = new Request.Builder().url(BASE_URL + methodPath).post(body).build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // add credential
    public String addCredential(Credential credential) {
        Gson gson = new Gson();
        String credentialJson = gson.toJson(credential);
        String result = "";
        final String methodPath = "memoir.credential";
        RequestBody body = RequestBody.create(credentialJson, JSON);
        Request request = new Request.Builder().url(BASE_URL + methodPath).post(body).build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String topFiveMovies(String personId) {
        final String methodPath = "memoir.memoir/findTopFiveByIdInCurrentYear/" + personId;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                results = response.body().string();
            } else {
                results = "fail";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static String getCinemas(){
        client = new OkHttpClient();
        final String methodPath = "memoir.cinema";
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
