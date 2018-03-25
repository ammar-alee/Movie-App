package com.interview.ammaryali.movieapp.transferdata;

import com.interview.ammaryali.movieapp.datamodel.ActorModel;

import org.json.JSONException;
import org.json.JSONObject;

public class ParsingActorInfoDetails {
    public ActorModel parseActorModel(String detailsResponse) {
        ActorModel actorModel = new ActorModel();
        try {
            JSONObject object = new JSONObject(detailsResponse);
//            ActorModel detailsModelClass = new ActorModel();
            actorModel.setActorID(Integer.parseInt(object.getString("id")));
            actorModel.setActorName(object.getString("name"));
            actorModel.setActorProfilePic(object.getString("profile_path"));
            actorModel.setBirthDate(object.getString("birthday"));
            actorModel.setBirthPlace(object.getString("place_of_birth"));
            actorModel.setIMDB_ID(object.getString("imdb_id"));
            actorModel.setBiography(object.getString("biography"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return actorModel;
    }
}
