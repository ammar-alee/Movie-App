package com.interview.ammaryali.movieapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
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
import com.interview.ammaryali.movieapp.activities.ActorDetailsActivity;
import com.interview.ammaryali.movieapp.adapters.ActorListAdapter;
import com.interview.ammaryali.movieapp.datamodel.ActorsListModelClass;
import com.interview.ammaryali.movieapp.interfaces.HandlingSearchResults;
import com.interview.ammaryali.movieapp.transferdata.ParsingActorListModel;

import static com.interview.ammaryali.movieapp.interfaces.ConstUtils.actor_key;
import static com.interview.ammaryali.movieapp.interfaces.ConstUtils.api_key;
import static com.interview.ammaryali.movieapp.interfaces.ConstUtils.base_url;
import static com.interview.ammaryali.movieapp.interfaces.ConstUtils.query_key;


import java.util.ArrayList;

public class FragmentActors extends Fragment implements HandlingSearchResults, AdapterView.OnItemClickListener {
    private static final String TAG = FragmentActors.class.getSimpleName();
    protected ListView mListView;
    protected ArrayList<ActorsListModelClass> actorsListModelClassArrayList;
    protected ActorListAdapter actorListAdapter;
    private ArrayList<ActorsListModelClass> actorModel_sv;
    private Context mContext;
    private RelativeLayout relativeLayout;
    private ImageView ivNoResults;
    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_actors, container, false);
        actorsListModelClassArrayList = new ArrayList<>();
        mContext = getContext();
        relativeLayout = mView.findViewById(R.id.relativeLayout_no_actors_result);
        ivNoResults = mView.findViewById(R.id.iv_no_actors_results_);
        mListView = mView.findViewById(R.id.actor_list_view);
        mProgressBar=mView.findViewById(R.id.actorsProgressBar);
        mListView.setOnItemClickListener(this);
        return mView;
    }

    @Override
    public void handleSearchResults(String query) {
        if (query.trim().length() > 0) {
            mProgressBar.setVisibility(View.VISIBLE);
            onAPICall(query);
        } else {
            relativeLayout.setVisibility(View.VISIBLE);
            ivNoResults.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.INVISIBLE);


        }
    }

    private void onAPICall(String query) {
        if (query == null) return;
        String urlActors = base_url + actor_key + api_key + query_key + query;
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, urlActors, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                actorsListModelClassArrayList = new ParsingActorListModel().actorModelClassArrayList(response);
//                actorsListModelClassArrayList = actorModel_sv;
                assert actorsListModelClassArrayList != null;
                if (actorsListModelClassArrayList.size() > 0) {
                    actorListAdapter = new ActorListAdapter(mContext, actorsListModelClassArrayList);
                    mListView.setAdapter(actorListAdapter);
                    actorListAdapter.notifyDataSetChanged();
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
            //Do your stuff
            }
        });
        MyApplication.getInstance().cancelPendingRequests(TAG);
        MyApplication.getInstance().addToRequestQueue(stringRequest1, TAG);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageView ivProfileImage = view.findViewById(R.id.iv_actors_pic);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, ivProfileImage, getContext().getString(R.string.actor_image_transition_name));
        Intent actorDetailsIntent = new Intent(getContext(), ActorDetailsActivity.class);
        actorDetailsIntent.putExtra("actorData", actorsListModelClassArrayList.get(position));
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        startActivity(actorDetailsIntent, options.toBundle());
    }

}
