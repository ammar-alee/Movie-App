package com.interview.ammaryali.movieapp.activities;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.interview.ammaryali.movieapp.MyApplication;
import com.interview.ammaryali.movieapp.R;
import com.interview.ammaryali.movieapp.adapters.MovieDetailsSectionAdapter;
import com.interview.ammaryali.movieapp.datamodel.CastModel;
import com.interview.ammaryali.movieapp.datamodel.CrewModel;
import com.interview.ammaryali.movieapp.datamodel.MovieModel;
import com.interview.ammaryali.movieapp.fragments.MovieDetailsCastFragment;
import com.interview.ammaryali.movieapp.fragments.MovieDetailsInfoFragment;
import com.interview.ammaryali.movieapp.transferdata.CastAndCrewParsing;
import com.interview.ammaryali.movieapp.utils.UrlUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieDetailsActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener, View.OnClickListener {
    ImageView imageView;
    Toolbar toolbar;
    ViewPager viewPager;
    ArrayList<CastModel> castModels;
    ArrayList<CrewModel> crewModels;
    ArrayList<Fragment> fragments;
    TabLayout tabLayout;
    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    CoordinatorLayout coordinatorLayout;
    MovieDetailsSectionAdapter movieDetailsSectionAdapter;
    boolean isShow = false;
    int scrollRange = -1;
    String movieTitle = " ", YoutubeKey = null;
    String poster, backdropPoster;
    int movieID;
    TextView title, genres;
    ProgressBar progressBar;
    LinearLayout linearLayoutTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        init();

    }

    private void init() {
        linearLayoutTabs = findViewById(R.id.ll_tabs);
        progressBar = findViewById(R.id.progressBar);
        coordinatorLayout = findViewById(R.id.detailRoot);
        title = (TextView) findViewById(R.id.movieName);
        genres = (TextView) findViewById(R.id.movieGenere);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabs);
        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(this);
        viewPager = findViewById(R.id.movieDetails_viewPager);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(null);
        setUpViews();
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        FloatingActionButton fabYoutube = findViewById(R.id.fab_youtube);
        fabYoutube.setOnClickListener(this);
        findViewById(R.id.imgMovieDetails).setOnClickListener(this);
    }

    @SuppressLint("CheckResult")
    private void setUpViews() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            assert bundle != null;
            if (bundle.containsKey("movieData")) {
                MovieModel movieModel = (MovieModel) bundle.getSerializable("movieData");
                assert movieModel != null;
                title.setText(movieModel.getTitle());
                genres.setText(movieModel.getGenres());
//                toolbar.setTitle(movieModel.getTitle());
                movieTitle = movieModel.getTitle();
                movieID = movieModel.getMovieID();
                getYouTubeKEY(movieID);
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.error_image_backdrop);
//                        .error(R.drawable.noimage_horiz);

                backdropPoster = UrlUtils.IMAGE_BASE_URL_500 + movieModel.getBackdropPath();
                Glide.with(this).applyDefaultRequestOptions(requestOptions).load(backdropPoster).into((ImageView) findViewById(R.id.imgMovieDetails));

                RequestOptions requestOptions2 = new RequestOptions();
                requestOptions2.placeholder(R.drawable.error_image_1);//.error(R.drawable.loading_placeholder_ver);
                requestOptions2.useAnimationPool(true).onlyRetrieveFromCache(true);
                poster = UrlUtils.IMAGE_BASE_URL_185 + movieModel.getPosterPath();
                setAppBarLayoutColor(poster);
                Glide.with(this).applyDefaultRequestOptions(requestOptions2).load(poster).into((ImageView) findViewById(R.id.imgMovieImage));
                setUpViewPager(movieModel.getMovieID(), movieModel);
//                Glide.with(this).load(poster)
//                        .apply(bitmapTransform(new BlurTransformation(40)))
//                        .into((ImageView) findViewById(R.id.imgBackBlur));
            }
        }
    }

    private void setUpViewPager(int movieID, final MovieModel movieModel) {
        castModels = new ArrayList<>();
        crewModels = new ArrayList<>();
        fragments = new ArrayList<>();
        String movieCastUrl = UrlUtils.MOVIE + movieID + UrlUtils.CREDITS_MOVIE_CAST_AND_CREW;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, movieCastUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CastAndCrewParsing castAndCrewParsing = new CastAndCrewParsing();
                castModels = castAndCrewParsing.movieCastModelsParse(response);
//                crewModels = new CastAndCrewParsing().getCrewModels(response);
                String directorName = castAndCrewParsing.getDirector(response);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("cast", castModels);
                MovieDetailsCastFragment castFragment = new MovieDetailsCastFragment();
                castFragment.setArguments(bundle1);
                Bundle bundle2 = new Bundle();
                bundle2.putString("director", directorName);
                bundle2.putSerializable("movieData", movieModel);
                MovieDetailsInfoFragment movieDetailsInfoFragment = new MovieDetailsInfoFragment();
                movieDetailsInfoFragment.setArguments(bundle2);
                fragments.add(movieDetailsInfoFragment);
                fragments.add(castFragment);
                movieDetailsSectionAdapter = new MovieDetailsSectionAdapter(getSupportFragmentManager(), fragments);
                progressBar.setVisibility(View.GONE);
                viewPager.setAdapter(movieDetailsSectionAdapter);
//                tabLayout.setupWithViewPager(viewPager);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(coordinatorLayout, "Error Occured", Snackbar.LENGTH_LONG).show();

            }
        });
        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_detailsl, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_home:
                gotoHome();
                break;
            case R.id.action_share:
                shareUrl();
                break;
            case R.id.action_open_tmdb:
                openWithBrowser();
                break;
            default:
                break;

        }
        return true;
//        return super.onOptionsItemSelected(item);
    }

    private void gotoHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private void shareUrl() {
        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intentShare.setType("text/*");
        intentShare.putExtra(Intent.EXTRA_TEXT, UrlUtils.TMDB_URL_MOVIE + movieID);
        startActivity(Intent.createChooser(intentShare, "Share '" + movieTitle + "' via"));
    }

    private void openWithBrowser() {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setType("text/html");
        i.setData(Uri.parse(UrlUtils.TMDB_URL_MOVIE + movieID));
        startActivity(Intent.createChooser(i, "Open With"));
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (scrollRange == -1) {
            scrollRange = appBarLayout.getTotalScrollRange();
        }
        if (scrollRange + verticalOffset == 0) {
            collapsingToolbarLayout.setTitle(movieTitle);
            isShow = true;
        } else if (isShow) {
            collapsingToolbarLayout.setTitle(" ");
            isShow = false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_youtube:
                openYoutube();
                break;
            case R.id.imgMovieDetails:
                openFullImage();
                break;

        }
    }

    void openFullImage() {
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, findViewById(R.id.imgMovieDetails), "image");
        Intent intent = new Intent(this, FullScreenImageActivity.class);
        intent.putExtra("title", movieTitle);
        intent.putExtra("image", backdropPoster);
        startActivity(intent, optionsCompat.toBundle());
//        startActivity(intent);
    }

    void openYoutube() {
        try {
            if (YoutubeKey != null) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + YoutubeKey));
                intent.putExtra("VIDEO_ID", YoutubeKey);
                startActivity(intent);
            } else {
                CoordinatorLayout coordinatorLayout = findViewById(R.id.detailRoot);
                Snackbar.make(coordinatorLayout, "No link found", Snackbar.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("Open Youtube", e.getMessage());
        }
    }


    private void getYouTubeKEY(int movieID) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlUtils.TMDB_YOUTUBE_VIDEO_LIST_URL + movieID + UrlUtils.VIDEOS_KEY_END,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            JSONObject trailerObject = jsonArray.getJSONObject(0);
                            YoutubeKey = trailerObject.getString("key");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }

    private void setAppBarLayoutColor(String imagePath) {
        RequestOptions requestOptions = new RequestOptions().onlyRetrieveFromCache(true);
        Glide.with(this).asBitmap().apply(requestOptions).load(imagePath).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                if (resource != null) {
                    Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(@NonNull Palette palette) {
                            final Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                            if (vibrantSwatch == null) {
                                return;
                            }
                            try {
                                int defaultValue = 0x000000;
                                final int vibrant = palette.getVibrantColor(defaultValue);
                                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), R.color.colorPrimary, vibrant);
                                colorAnimation.setDuration(250); // milliseconds
                                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animator) {
//                                        textView.setBackgroundColor((int) animator.getAnimatedValue());
                                        appBarLayout.setBackgroundColor((int) animator.getAnimatedValue());
                                        linearLayoutTabs.setBackgroundColor((int) animator.getAnimatedValue());
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            getWindow().setStatusBarColor((int) animator.getAnimatedValue());
                                        }
                                        collapsingToolbarLayout.setContentScrimColor((int) animator.getAnimatedValue());

                                        title.setTextColor(vibrantSwatch.getBodyTextColor());
                                        genres.setTextColor(vibrantSwatch.getBodyTextColor());
                                    }

                                });
                                colorAnimation.start();

                            } catch (Exception e) {
                                title.setTextColor(getResources().getColor(R.color.colorPrimary));
                                genres.setTextColor(getResources().getColor(R.color.colorPrimary));
                            }
                        }
                    });

                }

            }
        });
    }

}
