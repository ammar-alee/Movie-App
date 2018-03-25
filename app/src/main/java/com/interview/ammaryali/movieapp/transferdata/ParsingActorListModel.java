package com.interview.ammaryali.movieapp.transferdata;

import com.interview.ammaryali.movieapp.datamodel.ActorsListModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParsingActorListModel {
    public ArrayList<ActorsListModelClass> actorModelClassArrayList(String response) {
        ArrayList<ActorsListModelClass> actorModels = new ArrayList<>();
        try {
            JSONObject jsonObject_sv = new JSONObject(response);
            JSONArray jsonArray_sv = jsonObject_sv.getJSONArray("results");
            for (int i = 0; i < jsonArray_sv.length(); i++) {
                JSONObject actorJsonObject = jsonArray_sv.getJSONObject(i);
                ActorsListModelClass modelClass = new ActorsListModelClass();
                modelClass.setID(Integer.parseInt(actorJsonObject.getString("id")));
                modelClass.setProfilePic(actorJsonObject.getString("profile_path"));
                modelClass.setActorName(actorJsonObject.getString("name"));
                actorModels.add(modelClass);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return actorModels;
    }
}
