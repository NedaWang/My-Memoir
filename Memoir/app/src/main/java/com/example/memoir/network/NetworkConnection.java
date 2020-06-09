package com.example.memoir.network;


import android.util.Log;

import com.example.memoir.entity.Cinema;
import com.example.memoir.entity.Credential;
import com.example.memoir.entity.Memoir;
import com.example.memoir.entity.Person;
import com.example.memoir.util.Encrypt;
import com.example.memoir.util.JsonConvert;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

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

    // get cinemas
    public static List<Cinema> getCinemas(){
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
        List<Cinema> cinemas = new ArrayList<>();
        JsonArray jsons = new JsonParser().parse(results).getAsJsonArray();
        for (JsonElement e : jsons){
            JsonObject j = e.getAsJsonObject();
            Cinema cinema = new Cinema(j.get("id").getAsString(),j.get("name").getAsString(),j.get("location").getAsString(),j.get("postcode").getAsString());
            cinemas.add(cinema);
        }
        return cinemas;
    }

    // count cinemas
    public static int countCinema(){
        client = new OkHttpClient();
        final String methodPath = "memoir.cinema/count";
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

    // add cinima
    public static String addCinema(Cinema cinema){
        Gson gson = new Gson();
        String cinemaJson = gson.toJson(cinema);
        String result = "";
        final String methodPath = "memoir.cinema";
        RequestBody body = RequestBody.create(cinemaJson, JSON);
        Request request = new Request.Builder().url(BASE_URL + methodPath).post(body).build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // count memoir for calculate id
    public static int countMemoir(){
        client = new OkHttpClient();
        final String methodPath = "memoir.memoir/count";
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

    // add memoir
    public static String addMemoir(Memoir memoir){
        Gson gson = new Gson();
        String cinemaJson = gson.toJson(memoir);
        Log.i("memoir json: ", cinemaJson);
        String result = "";
        final String methodPath = "memoir.memoir";
        RequestBody body = RequestBody.create(cinemaJson, JSON);
        Request request = new Request.Builder().url(BASE_URL + methodPath).post(body).build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Log.i("result of add a memoir", results);
        return result;
    }

    // get memoir by id
    public static String getMemoirByPersonID(String id){
        client = new OkHttpClient();
        final String methodPath = "memoir.memoir/findByPersonID/" + id;
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

    public String login1(String username, String password) {
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
}
