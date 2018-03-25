package com.interview.ammaryali.movieapp.transferdata;

import com.interview.ammaryali.movieapp.datamodel.SearchMovieModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
public class ParsingMovieModel {
    public ArrayList<SearchMovieModel> movieModelParsingList(String response) {
        ArrayList<SearchMovieModel> movieModels = new ArrayList<>();
        try {
            JSONObject jsonObject_sv = new JSONObject(response);
            JSONArray jsonArray_sv = jsonObject_sv.getJSONArray("results");
            for (int i = 0; i < jsonArray_sv.length(); i++) {
                JSONObject movieObject = jsonArray_sv.getJSONObject(i);
                JSONArray genreArray = movieObject.getJSONArray("genre_ids");
                ArrayList<Integer> genresIDs = new ArrayList<>();
                for (int j = 0; j < genreArray.length(); j++) {
                    genresIDs.add(genreArray.getInt(i));
                }
//                movieModels.add(new SearchMovieModel(movieObject.getString("poster_path"),
//                        movieObject.getString("release_date"),
//                        movieObject.getString("title"), genresIDs));

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieModels;
    }
}

