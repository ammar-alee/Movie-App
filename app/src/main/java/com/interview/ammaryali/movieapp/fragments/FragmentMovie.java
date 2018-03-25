package com.interview.ammaryali.movieapp.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.interview.ammaryali.movieapp.MyApplication;
import com.interview.ammaryali.movieapp.R;
import com.interview.ammaryali.movieapp.activities.MovieDetailsActivity;
import com.interview.ammaryali.movieapp.adapters.MovieListAdapter;
import com.interview.ammaryali.movieapp.datamodel.MovieModel;
import com.interview.ammaryali.movieapp.interfaces.HandlingSearchResults;
import com.interview.ammaryali.movieapp.transferdata.MoviesParsing;

import static com.interview.ammaryali.movieapp.interfaces.ConstUtils.movie_key;
import static com.interview.ammaryali.movieapp.interfaces.ConstUtils.api_key;
import static com.interview.ammaryali.movieapp.interfaces.ConstUtils.base_url;
import static com.interview.ammaryali.movieapp.interfaces.ConstUtils.query_key;


import java.util.ArrayList;

public class FragmentMovie extends Fragment implements HandlingSearchResults, AdapterView.OnItemClickListener {
    protected ListView mMovieListView;
    protected MovieListAdapter movieListAdapter;
    //    protected ArrayList<SearchMovieModel> movieModelClassArrayList;
    protected ArrayList<MovieModel> movieModelArrayList;
    private ImageView ivNoResults;
    private Context movieContext;
    private RelativeLayout relativeLayout;
    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_movie, container, false);
        ivNoResults = mView.findViewById(R.id.iv_no_results);
        relativeLayout = mView.findViewById(R.id.relativeLayout_no_result);
        movieModelArrayList = new ArrayList<>();
        mMovieListView = mView.findViewById(R.id.movie_list_view);
        mProgressBar = mView.findViewById(R.id.progressBar);
        movieContext = getContext();
        mMovieListView.setOnItemClickListener(this);
        return mView;
    }

    @Override
    public void handleSearchResults(String query) {
        if (query == null) return;
// relativeLayout.setVisibility(View.GONE);
//        ivNoResults.setVisibility(View.GONE);

        if (query.trim().length() > 0) {
            mProgressBar.setVisibility(View.VISIBLE);
            onAPICall(query);
        } else {
            relativeLayout.setVisibility(View.VISIBLE);
            ivNoResults.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void onAPICall(final String query) {

//        RequestQueue requestQueue = Volley.newRequestQueue(movieContext);
        final String urlMovies = base_url + movie_key + api_key + query_key + query;
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, urlMovies, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                movieModelArrayList = new MoviesParsing().movieModelsParse(response);
                assert movieModelArrayList != null;
                if (movieModelArrayList.size() > 0) {
                    movieListAdapter = new MovieListAdapter(movieContext, movieModelArrayList);
                    mMovieListView.setAdapter(movieListAdapter);
                    movieListAdapter.notifyDataSetChanged();
                    ivNoResults.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    relativeLayout.setVisibility(View.VISIBLE);
                    ivNoResults.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.INVISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
//        requestQueue.add(stringRequest1);
//        MyApplication.getInstance().cancelPendingRequests(this);
        MyApplication.getInstance().addToRequestQueue(stringRequest1);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        try {
            Intent movieDetailsIntent = new Intent(movieContext, MovieDetailsActivity.class);
            Pair<View, String> p1 = Pair.create((View) view.findViewById(R.id.iv_actors_pic), getContext().getString(R.string.movie_image_transition_name));
//                Pair<View, String> p2 = Pair.create((View) movieName, "title");
//                Pair<View, String> p3 = Pair.create((View) year, "year");
//                Pair<View, String> p4 = Pair.create((View) director, "director");
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) getContext(), p1);
            movieDetailsIntent.putExtra("movieData", movieModelArrayList.get(position));
            startActivity(movieDetailsIntent, optionsCompat.toBundle());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Click", e.getMessage());
        }

    }
}
