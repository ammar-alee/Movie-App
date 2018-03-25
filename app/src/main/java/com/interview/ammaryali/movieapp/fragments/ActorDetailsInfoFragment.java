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
import com.interview.ammaryali.movieapp.datamodel.ActorModel;


public class ActorDetailsInfoFragment extends Fragment {
    private ActorModel infoModel;
    private TextView tvBirthDate, tvBirthPlace, tvBiography,tvDot,tvEmptyBiography;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actor_info, container, false);
        try {
            Bundle bundle = getArguments();
            infoModel = (ActorModel) bundle.getSerializable("actorInfo");
            tvBirthDate = view.findViewById(R.id.tv_BirthDate);
            tvBirthPlace = view.findViewById(R.id.tv_BirthPlace);
            tvBiography = view.findViewById(R.id.tv_Biography);
            tvDot=view.findViewById(R.id.tv_dot);
            tvEmptyBiography=view.findViewById(R.id.tv_empty_Biography);

            if (infoModel != null) {
                if (infoModel.getBirthDate() != null && !infoModel.getBirthDate().equalsIgnoreCase("null")) {
                    tvBirthDate.setText(getDateInFormat(infoModel.getBirthDate()));
                } else {
                    tvBirthDate.setText("N/A");
                }
                if (infoModel.getBirthPlace() != null && !infoModel.getBirthPlace().equalsIgnoreCase("null")) {
                    tvBirthPlace.setText(infoModel.getBirthPlace());
                } else {
                    tvBirthPlace.setText("N/A");
                }
                if (infoModel.getBiography() != null && !infoModel.getBiography().equalsIgnoreCase("null")) {
                    tvBiography.setText(infoModel.getBiography());
                } else {
                    tvDot.setVisibility(View.VISIBLE);
                    tvEmptyBiography.setText("N/A");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("acInfo", e.getMessage());

        }

        return view;
    }

    private String getDateInFormat(String date) {
        String[] strings = date.split("-");
        try {
            return getMonthName(strings[1]) + " " + strings[2] + ", " + strings[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    private String getMonthName(String s) {
        int month = Integer.parseInt(s);
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return months[month];

    }
}
