package com.interview.ammaryali.movieapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.interview.ammaryali.movieapp.R;
import com.interview.ammaryali.movieapp.datamodel.MovieModel;


public class MovieDetailsInfoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_info_page, container, false);
        try {
            Bundle bundle = getArguments();
            assert bundle != null;
            MovieModel movieModel = (MovieModel) bundle.getSerializable("movieData");
            String director = bundle.getString("director");
            TextView tvDirector = view.findViewById(R.id.movieDirector);
            TextView tvOverView = view.findViewById(R.id.movieOverview);
            TextView tvreleaseDate = view.findViewById(R.id.movieReleaseDate);
            TextView tvDot=view.findViewById(R.id.tv_dot);
            TextView tvEmptDirector=view.findViewById(R.id.tv_empty_director);
            assert movieModel != null;
            if ((movieModel.getOverView() == null || movieModel.getOverView().equalsIgnoreCase("null"))) {
                tvOverView.setText("N/A");
            } else {
                tvOverView.setText(movieModel.getOverView());
            }

            if ((movieModel.getReleaseDate() == null || movieModel.getReleaseDate().equalsIgnoreCase("null"))) {
                tvreleaseDate.setText("N/A");
            } else {
                tvreleaseDate.setText(getDateInFormat(movieModel.getReleaseDate()));
            }

            if (director == null || director.equalsIgnoreCase("null")) {
                tvDot.setVisibility(View.VISIBLE);
                tvEmptDirector.setText("N/A");
            } else {
                tvDirector.setText(director);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Info", "Error");
        }
        return view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    String getDateInFormat(String date) {
        String[] strings = date.split("-");
        try {
            return getMonthName(strings[1]) + " " + strings[2] + ", " + strings[0];
        } catch (Exception e) {
        }
        return date;
    }

    String getMonthName(String s) {
        int month = Integer.parseInt(s);
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return months[month];

    }
}
