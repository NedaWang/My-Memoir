package com.example.memoir.util;

import com.example.memoir.entity.Cinema;
import com.example.memoir.entity.Credential;
import com.example.memoir.entity.Memoir;
import com.example.memoir.entity.Person;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonConvert {

    public static Person stringToPerson(String s) {
        System.out.println(s);
        Person p = null;
        try {
            JSONObject j = new JSONObject(s);
            p = jsonToPerson(j);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return p;
    }

    // one person object
    public static Person jsonToPerson(JSONObject j) {
        Person p = new Person();
        try {
            p.setId(j.getString("id"));
            p.setAddress(j.get("address").toString());
            p.setDob(j.getString("dob"));
            p.setEmail(j.getString("email"));
            p.setFirstname(j.getString("firstname"));
            p.setPostcode(j.getString("postcode"));
            p.setState(j.getString("state"));
            p.setSurname(j.getString("surname"));
            p.setGender(j.getString("gender"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return p;
    }

    // one credential object
    public static Credential stringToCredential(String s) {
        Credential c = new Credential();
        try {
            JSONObject json = new JSONObject(s);
            c.setId(json.getString("id"));
            JSONObject pJson = json.getJSONObject("username");
            Person p = new Person();
            c.setUsername(jsonToPerson(pJson));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }



    // multiple memoir objects
    public static List<Memoir> stringToMemoirs(String s) {
        List<Memoir> memoirs = new ArrayList<Memoir>();
        Memoir m = null;
/*
        try {
            JSONArray jsons = new JSONArray(s);
            for (int i = 0; i < jsons.length(); i++) {
                JSONObject j = jsons.getJSONObject(i);
                m = new Memoir();
                m.setName(j.getString("name"));
                m.setReleaseDate(j.getString("releasedate"));
                m.setScore(j.getString("score"));
                memoirs.add(m);
            } catch(JSONException e){
                e.printStackTrace();
            }

 */
        JsonArray jsons = new JsonParser().parse(s).getAsJsonArray();
        for (JsonElement e : jsons) {
            JsonObject j = e.getAsJsonObject();
            m = new Memoir();
            m.setName(j.get("name").getAsString());
            m.setReleaseDate(j.get("releasedate").getAsString());
            m.setWatchDate(j.get("watchdate").getAsString());
            m.setComment(j.get("comment").getAsString());
            m.setScore(j.get("score").getAsString());

            JsonObject cinemaJson = j.getAsJsonObject("cinema");
            Cinema cinema = new Cinema(cinemaJson.get("id").getAsString(),cinemaJson.get("name").getAsString(),
                    cinemaJson.get("location").getAsString(),cinemaJson.get("postcode").getAsString());
            m.setCinema(cinema);
            memoirs.add(m);
        }
        return memoirs;
    }

}
