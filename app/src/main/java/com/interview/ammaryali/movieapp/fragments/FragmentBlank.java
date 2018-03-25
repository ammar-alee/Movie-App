package com.interview.ammaryali.movieapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.interview.ammaryali.movieapp.R;
import com.interview.ammaryali.movieapp.interfaces.HandlingSearchResults;


public class FragmentBlank extends Fragment implements HandlingSearchResults {
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view= inflater.inflate(R.layout.fragment_blank,container,false);
        return view;
    }

    @Override
    public void handleSearchResults(String searchQuery) {
        view.setVisibility(View.VISIBLE);
    }
}
