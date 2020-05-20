package com.example.memoir.util;

import com.example.memoir.entity.Credential;
import com.example.memoir.entity.Person;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonConvert {

    public static Person stringToPerson(String s) {
        System.out.println(s);
        Person p = new Person();
        try {
            JSONObject j = new JSONObject(s);
            p = jsonToPerson(j,p);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return p;
    }

    public static Person jsonToPerson(JSONObject j, Person p){
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

    public static Credential stringToCredential(String s) {
        Credential c = new Credential();
        try {
            JSONObject json = new JSONObject(s);
            c.setId(json.getString("id"));
            JSONObject pJson = json.getJSONObject("username");
            Person p = new Person();
            c.setUsername(jsonToPerson(pJson,p));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }
}
