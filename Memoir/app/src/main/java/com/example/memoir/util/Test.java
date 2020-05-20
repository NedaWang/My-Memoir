package com.example.memoir.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Test {
    public static void main(String[] args) {
        String s = "[{\"name\":\"Onward\",\"releasedate\":\"2020-04-02\",\"score\":71},{\"name\":\"Black Widow\",\"releasedate\":\"2020-04-24\",\"score\":70}]";
        System.out.println(s);

            JsonArray a = new JsonParser().parse(s).getAsJsonArray();
            //JSONObject a = new JSONObject(s);
            System.out.println(a);


    }
}
