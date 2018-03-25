package com.interview.ammaryali.movieapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.interview.ammaryali.movieapp.MyApplication;
import com.interview.ammaryali.movieapp.R;
import com.interview.ammaryali.movieapp.adapters.RecyclerAdapterHome;
import com.interview.ammaryali.movieapp.adapters.SectionsPagerAdapter;
import com.interview.ammaryali.movieapp.datamodel.MovieModel;
import com.interview.ammaryali.movieapp.transferdata.MoviesParsing;
import com.interview.ammaryali.movieapp.utils.UrlUtils;


import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    static ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init() {
        progressBar = findViewById(R.id.progressBar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);
        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
//        mViewPager.setPageTransformer(true, new CubeOutTransformer());

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.image_search_home:
                gotoSearch();
                break;
            case R.id.action_learn_more:
                Intent welcomeIntent = new Intent(this, WelcomeActivity.class);
                startActivity(welcomeIntent);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void gotoSearch() {
        Intent searchIntent = new Intent(this, MainSearchActivity.class);
        startActivity(searchIntent);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        //        private static final String ARG_SECTION_DATA = "section_data";
        private static final String ARG_SECTION_URL = "section_url";
        private static String[] urls;

        RecyclerAdapterHome recyclerAdapterHome;
        ArrayList<MovieModel> modelArrayListAll;

        public PlaceholderFragment() {
            modelArrayListAll = new ArrayList<>();
            urls = new String[]{UrlUtils.NOW_PLAYING_MOVIES, UrlUtils.UPCOMING_MOVIES, UrlUtils.POPULAR_MOVIES, UrlUtils.TOP_RATED_MOVIES};
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(ARG_SECTION_URL, urls[sectionNumber - 1]);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            final RecyclerView recyclerView = rootView.findViewById(R.id.recyclerHome);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
            recyclerAdapterHome = new RecyclerAdapterHome(getContext(), modelArrayListAll);
            recyclerView.setAdapter(recyclerAdapterHome);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, getArguments().getString(ARG_SECTION_URL),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            modelArrayListAll.addAll(new MoviesParsing().movieModelsParse(response));
                            recyclerAdapterHome = new RecyclerAdapterHome(getContext(), modelArrayListAll);


                            progressBar.setVisibility(View.GONE);
                            recyclerView.setAdapter(recyclerAdapterHome);
                            recyclerAdapterHome.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                            showError();
                }
            });
            MyApplication.getInstance().addToRequestQueue(stringRequest);
            return rootView;
        }
    }
}
