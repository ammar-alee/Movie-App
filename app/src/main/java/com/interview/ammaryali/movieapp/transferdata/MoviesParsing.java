package com.interview.ammaryali.movieapp.transferdata;

import android.util.Log;

import com.interview.ammaryali.movieapp.datamodel.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MoviesParsing {

    public ArrayList<MovieModel> movieModelsParse(String response) {
        ArrayList<MovieModel> movieModels = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = null;

            if (jsonObject.has("results")) {
                jsonArray = (JSONArray) jsonObject.get("results");

            } else if (jsonObject.has("cast")) {
                jsonArray = (JSONArray) jsonObject.get("cast");
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonMovieObject = jsonArray.getJSONObject(i);
                MovieModel movieModel = new MovieModel();
                movieModel.setMovieID(Integer.parseInt(jsonMovieObject.getString("id")));
                movieModel.setTitle(jsonMovieObject.getString("title"));
                movieModel.setBackdropPath(jsonMovieObject.getString("backdrop_path"));
                movieModel.setOriginalTitle(jsonMovieObject.getString("original_title"));
                movieModel.setPosterPath(jsonMovieObject.getString("poster_path"));
                movieModel.setReleaseDate(jsonMovieObject.getString("release_date"));
                movieModel.setOverView(jsonMovieObject.getString("overview"));
                movieModel.setRating(jsonMovieObject.getString("vote_average"));
//                movieModel.setDirector(jsonMovieObject.getString(""));
                JSONArray genresIDs = jsonMovieObject.getJSONArray("genre_ids");
                StringBuilder generes = new StringBuilder();
                for (int j = 0; j < genresIDs.length(); j++) {
                    generes.append(getGeneres(genresIDs.getInt(j)));
                    if (j < genresIDs.length() - 1) {
                        generes.append(", ");
                    }
                }
                movieModel.setGenres(generes.toString());
                if (!jsonMovieObject.getBoolean("adult")) {
                    movieModels.add(movieModel);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Parsing Movie", "Error");
        }
        return movieModels;
    }

    String getGeneres(int genreId) {
        String[] genreList = {"Action", "Adventure", "Animation", "Comedy", "Crime",
                "Documentary", "Drama", "Family", "Fantasy", "History",
                "Horror", "Music", "Mystery", "Romance", "Science Fiction",
                "TV Movie", "Thriller", "War", "Western"};
        int[] ids = {28, 12, 16, 35, 80, 99, 18, 10751, 14, 36, 27, 10402, 9648, 10749, 878, 10770, 53, 10572, 37};
        for (int i = 0; i < genreList.length; i++) {
            if (genreId == ids[i]) {
                return genreList[i];
            }
        }
        return genreList[0];
    }
}