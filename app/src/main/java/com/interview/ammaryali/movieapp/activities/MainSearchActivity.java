package com.interview.ammaryali.movieapp.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import com.interview.ammaryali.movieapp.R;
import com.interview.ammaryali.movieapp.adapters.SearchActivityFragmentAdapter;
import com.interview.ammaryali.movieapp.fragments.FragmentActors;
import com.interview.ammaryali.movieapp.fragments.FragmentBlank;
import com.interview.ammaryali.movieapp.fragments.FragmentMovie;
import com.interview.ammaryali.movieapp.interfaces.HandlingSearchResults;

import java.util.ArrayList;

public class MainSearchActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, View.OnKeyListener {

    private ImageView ivSearch_sv, iv_clear;
    private EditText etSearch_sv;
    private String mUserQuery, searchQuery;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ArrayList<Fragment> fragmentArrayList;
    private Fragment fragmentMovie, fragmentActors, fragmentBlank;
    private SearchActivityFragmentAdapter mSearchActivityAdapter;
    private Fragment currentFragment;
    private Toolbar toolbar_sv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);
        init();
        setListeners();
        addFragments();
    }


    private void init() {
        iv_clear = findViewById(R.id.iv_clear);
        ivSearch_sv = findViewById(R.id.iv_search);
        etSearch_sv = findViewById(R.id.et_searchTextView);
        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabLayout);
        toolbar_sv = findViewById(R.id.toolbar_sv);

        setSupportActionBar(toolbar_sv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragmentArrayList = new ArrayList<>();
        fragmentMovie = new FragmentMovie();
        fragmentActors = new FragmentActors();
        fragmentBlank = new FragmentBlank();
        currentFragment = fragmentMovie;
        etSearch_sv.setOnKeyListener(this);
        etSearch_sv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().startsWith(" ")) {
                    etSearch_sv.setText("");
                    return;
                }
                if (s.toString().length() > 0) {
                    iv_clear.setVisibility(View.VISIBLE);

                } else {
                    iv_clear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }

    private void setListeners() {
        iv_clear.setOnClickListener(this);
        ivSearch_sv.setOnClickListener(this);
        mViewPager.setOnPageChangeListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
                fragmentState();
                break;
            case R.id.iv_clear:
                etSearch_sv.setText("");
                searchQuery = "";
                if (currentFragment instanceof HandlingSearchResults)
                    ((HandlingSearchResults) currentFragment).handleSearchResults(searchQuery);


                break;
        }

    }


    private void fragmentState() {
        mUserQuery = etSearch_sv.getText().toString();
        searchQuery = mUserQuery.replace(" ", "+");
        if (currentFragment instanceof HandlingSearchResults)
            if (searchQuery.length() > 0) {
                ((HandlingSearchResults) currentFragment).handleSearchResults(searchQuery);

            }
    }

    private void addFragments() {
        fragmentArrayList.add(fragmentMovie);
        fragmentArrayList.add(fragmentActors);
        setViews();
    }

    private void setViews() {
        mSearchActivityAdapter = new SearchActivityFragmentAdapter(getSupportFragmentManager(), fragmentArrayList);
        mViewPager.setAdapter(mSearchActivityAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        setTabTitles();
    }

    private void setTabTitles() {
        mTabLayout.getTabAt(0).setText("Movies");

        mTabLayout.getTabAt(1).setText("Actors");
        etSearch_sv.setHint("Search Movies...");
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                currentFragment = fragmentMovie;
                if (searchQuery != null && !searchQuery.equalsIgnoreCase("")) {
                    fragmentState();
                }
                etSearch_sv.setHint("Search Movies....");
                break;
            case 1:
                currentFragment = fragmentActors;
                if (searchQuery != null) {
                    fragmentState();
                }
                etSearch_sv.setHint("Search Actors....");
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == EditorInfo.IME_ACTION_SEARCH) {
            fragmentState();
//            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if ((i == KeyEvent.KEYCODE_SEARCH) || (i == KeyEvent.KEYCODE_ENTER)) {
            fragmentState();
            return true;
        }
        return false;
    }
}