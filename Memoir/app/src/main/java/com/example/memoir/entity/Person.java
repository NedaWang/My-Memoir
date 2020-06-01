package com.example.memoir.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Person implements Parcelable {
    private String id;
    private String firstname;
    private String surname;
    private String gender;
    private String dob;
    private String address;
    private String state;
    private String postcode;
    private String email;

    public Person(){}

    public Person(String id){
        this.id = id;
    }

    public Person(String id, String firstname, String surname, String gender,
                  String dob, String address, String state, String postcode, String email) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        this.state = state;
        this.postcode = postcode;
        this.email = email;
    }

    public Person (Parcel in){
        this.id = in.readString();
        this.firstname = in.readString();
        this.surname = in.readString();
        this.gender = in.readString();
        this.dob = in.readString();
        this.address = in.readString();
        this.state = in.readString();
        this.postcode = in.readString();
        this.email = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeString(firstname);
        parcel.writeString(surname);
        parcel.writeString(gender);
        parcel.writeString(dob);
        parcel.writeString(address);
        parcel.writeString(state);
        parcel.writeString(postcode);
        parcel.writeString(email);
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @NonNull
    @Override
    public String toString() {
        return "check here" + this.id + "//" + this.email + "//" + this.firstname + "//" + this.dob;
    }
}
