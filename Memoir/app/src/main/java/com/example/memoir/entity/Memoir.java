package com.example.memoir.entity;


public class Memoir {
    private String id;
    private String name;
    private String releasedate;
    private String watchdate;
    private String comment;
    private String score;
    private Person personid;
    private Cinema cinema;


    public Memoir() {
    }

    public Memoir(String id, String movieName, String releaseDate, String watchDate, String comment, String score, Cinema cinema, Person personid) {
        this.id = id;
        this.name = movieName;
        this.releasedate = releaseDate;
        this.watchdate = watchDate;
        this.comment = comment;
        this.score = score;
        this.cinema = cinema;
        this.personid = personid;
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

    public void setName(String movieName) {
        this.name = movieName;
    }

    public String getReleaseDate() {
        return releasedate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releasedate = releaseDate;
    }

    public String getWatchDate() {
        return watchdate;
    }

    public void setWatchDate(String watchDate) {
        this.watchdate = watchDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Person getPerson() {
        return personid;
    }

    public void setPerson(Person person) {
        this.personid = person;
    }
}
