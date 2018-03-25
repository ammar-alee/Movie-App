package com.interview.ammaryali.movieapp.datamodel;

import java.io.Serializable;

public class ActorModel implements Serializable {
    private String actorName, actorProfilePath, birthDate, birthPlace, biography, IMDB_ID, backDropPath;
    private int actorID;

    public void setActorID(int id) {
        this.actorID = id;
    }

    public int getActorID() {
        return actorID;
    }

    public void setActorName(String name) {
        this.actorName = name;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorProfilePic(String profilePic) {
        this.actorProfilePath = profilePic;
    }

    public String getActorProfilePath() {
        return actorProfilePath;
    }

    public void setBirthDate(String birthday) {
        this.birthDate = birthday;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getBiography() {
        return biography;
    }

    public void setIMDB_ID(String imdb_id) {
        this.IMDB_ID = imdb_id;
    }

    public String getIMDB_ID() {
        return IMDB_ID;
    }

    public void setBackDropPath(String backDropPath) {
        this.backDropPath = backDropPath;
    }

    public String getBackDropPath() {
        return backDropPath;
    }
}
