package com.interview.ammaryali.movieapp.transferdata;

import android.util.Log;

import com.interview.ammaryali.movieapp.datamodel.MovieModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.interview.ammaryali.movieapp.utils.UrlUtils.TMDB_API_KEY;

public class MoviesRequesting {

    private static final String DEBUG_TAG = MoviesRequesting.class.getSimpleName();


    public String stringify(InputStream stream) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(reader);
        return bufferedReader.readLine();
    }

    public ArrayList<MovieModel> searchTMDBMovie(String query) throws IOException {
        // Build URL
        String string = "http://api.themoviedb.org/3/search/movie" +
                "?api_key=" + TMDB_API_KEY +
                "&query=" + query;
        URL url = new URL(string);
        InputStream stream = null;
        try {
            // Establish a connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);/* milliseconds */
            conn.setConnectTimeout(15000);/* milliseconds */
            conn.setRequestMethod("GET");
            conn.addRequestProperty("Accept", "application/json"); // Required to get TMDB to play nicely.
            conn.setDoInput(true);
            conn.connect();

            int responseCode = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response code is: " + responseCode + " " + conn.getResponseMessage());

            stream = conn.getInputStream();
            return new MoviesParsing().movieModelsParse(stringify(stream));
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }
}
