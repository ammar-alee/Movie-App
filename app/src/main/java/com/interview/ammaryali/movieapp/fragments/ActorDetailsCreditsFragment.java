package com.interview.ammaryali.movieapp.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.interview.ammaryali.movieapp.R;
import com.interview.ammaryali.movieapp.adapters.RecyclerAdapterHome;
import com.interview.ammaryali.movieapp.datamodel.MovieModel;

import java.util.ArrayList;

public class ActorDetailsCreditsFragment extends Fragment {
    RecyclerAdapterHome recyclerAdapterHome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        try {
            Bundle bundle = getArguments();
            ArrayList<MovieModel> modelArrayList = (ArrayList<MovieModel>) bundle.getSerializable("actorCredits");
            final RecyclerView recyclerView = rootView.findViewById(R.id.recyclerHome);
//            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            recyclerAdapterHome = new RecyclerAdapterHome(getContext(), modelArrayList);
            recyclerView.setAdapter(recyclerAdapterHome);
            recyclerAdapterHome.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }
}
