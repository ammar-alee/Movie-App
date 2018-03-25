package com.interview.ammaryali.movieapp.transferdata;

import android.util.Log;

import com.interview.ammaryali.movieapp.datamodel.CastModel;
import com.interview.ammaryali.movieapp.datamodel.CrewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CastAndCrewParsing {

    public ArrayList<CastModel> movieCastModelsParse(String response) {
        ArrayList<CastModel> castModels = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = (JSONArray) jsonObject.get("cast");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonMovieCastObject = jsonArray.getJSONObject(i);
                CastModel castModel = new CastModel();
                castModel.setActorID(Integer.parseInt(jsonMovieCastObject.getString("id")));
                castModel.setActorName(jsonMovieCastObject.getString("name"));
                castModel.setActorProfilePath(jsonMovieCastObject.getString("profile_path"));
                castModel.setCharacterName(jsonMovieCastObject.getString("character"));
                castModel.setCastID(jsonMovieCastObject.getString("cast_id"));
                castModel.setCreditID(jsonMovieCastObject.getString("credit_id"));
//                castModel.setDirectorID(Integer.parseInt(jsonMovieCastObject.getString("overview")));
                castModels.add(castModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Parsing Cast", "Error");
        }
        return castModels;
    }

    public ArrayList<CrewModel> getCrewModels(String response) {
        ArrayList<CrewModel> crewModels = new ArrayList<>();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONArray crewJsonArray = jsonObject.getJSONArray("crew");
            for (int i = 0; i < crewJsonArray.length(); i++) {
                JSONObject crewObject = crewJsonArray.getJSONObject(i);
                CrewModel crewModel = new CrewModel();
                crewModel.setDirectorID(crewObject.getInt("id"));
                crewModel.setDirectorName(crewObject.getString("name"));
                crewModel.setDepartment(crewObject.getString("department"));
                crewModel.setJob(crewObject.getString("job"));
                crewModel.setProfilePath(crewObject.getString("profile_path"));
                crewModels.add(crewModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Parsing Crew", "Error");

        }
        return crewModels;
    }

    public String getDirector(String response) {
        ArrayList<CrewModel> crewModels = getCrewModels(response);
        try {
            for (int i = 0; i < crewModels.size(); i++) {
                if (crewModels.get(i).getJob().equalsIgnoreCase("Director")) {
                    return crewModels.get(i).getDirectorName();
                }
            }
            return crewModels.get(0).getDirectorName();
        } catch (Exception e) {

        }
        return "N/A";
//        return crewModels.get(0).getDirectorName();
    }

}