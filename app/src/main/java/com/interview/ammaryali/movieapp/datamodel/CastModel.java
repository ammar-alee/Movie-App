package com.interview.ammaryali.movieapp.datamodel;

import java.io.Serializable;

public class CastModel implements Serializable {
    private String actorName, characterName, castID, creditID, actorProfilePath;
    private int actorID, directorID;
    private String directorName;

    public void setActorID(int actorID) {
        this.actorID = actorID;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public void setActorProfilePath(String actorProfilePath) {
        this.actorProfilePath = actorProfilePath;
    }

    public void setCastID(String castID) {
        this.castID = castID;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public void setCreditID(String creditID) {
        this.creditID = creditID;
    }

    public void setDirectorID(int directorID) {
        this.directorID = directorID;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public int getActorID() {
        return actorID;
    }

    public int getDirectorID() {
        return directorID;
    }

    public String getActorName() {
        return actorName;
    }

    public String getActorProfilePath() {
        return actorProfilePath;
    }

    public String getCastID() {
        return castID;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getCreditID() {
        return creditID;
    }

    public String getDirectorName() {
        return directorName;
    }

}
