package com.example.memoir.entity;

public class Cinema {
    private String id;
    private String name;
    private String location;
    private String postcode;

    public Cinema() {
    }

    public Cinema(String id){
        this.id = id;
    }

    public Cinema(String id, String name, String location, String postcode) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.postcode = postcode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
