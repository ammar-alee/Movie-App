package com.interview.ammaryali.movieapp.datamodel;

import java.io.Serializable;

public class ActorsListModelClass implements Serializable {
    private String mProfilePic, mActorName, mBackgroundImage;
    private int mID;

    public ActorsListModelClass() {

    }

    public int getID() {
        return mID;
    }

    public void setID(int id) {
        this.mID = id;
    }

    public String getProfilePic() {
        return mProfilePic;
    }

    public void setProfilePic(String profilePic) {
        this.mProfilePic = profilePic;
    }

    public String getActorName() {
        return mActorName;
    }

    public void setActorName(String name) {
        this.mActorName = name;
    }

}
