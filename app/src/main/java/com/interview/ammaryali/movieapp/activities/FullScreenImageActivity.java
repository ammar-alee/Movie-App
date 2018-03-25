package com.interview.ammaryali.movieapp.activities;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.interview.ammaryali.movieapp.R;


public class FullScreenImageActivity extends AppCompatActivity {

    View rootLayout;
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          startWithAnim(savedInstanceState);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Bundle bundle = getIntent().getExtras();
            assert bundle != null;
            getSupportActionBar().setTitle(bundle.getString("title", "Movie Flix"));
            Glide.with(this).load(bundle.getString("image"))//.transition(DrawableTransitionOptions.withCrossFade())
                    .apply(RequestOptions.errorOf(R.drawable.error_image_1).useAnimationPool(true)
                            .placeholder(R.drawable.loading_placehoder_hor))
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }



    private void startWithAnim(Bundle savedInstanceState) {

        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);
        setContentView(R.layout.activity_image_fullscreen);
        imageView = findViewById(R.id.imgFullscreen);

        rootLayout = findViewById(R.id.full_root);
        if (savedInstanceState == null) {
            imageView.setVisibility(View.INVISIBLE);
            ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        circularRevealActivity();
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
            }
        }
    }


    private void circularRevealActivity() {

        int cx = imageView.getWidth() / 2;
        int cy = imageView.getHeight() / 2;

        float finalRadius = Math.max(imageView.getWidth(), imageView.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            circularReveal = ViewAnimationUtils.createCircularReveal(imageView, cx, cy, 0, finalRadius);
        }
        assert circularReveal != null;
        circularReveal.setDuration(1000);

        // make the view visible and start the animation
        imageView.setVisibility(View.VISIBLE);
        circularReveal.start();
    }


    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = imageView.getWidth() / 2;
            int cy = imageView.getHeight() / 2;
            float finalRadius = Math.max(imageView.getWidth(), imageView.getHeight());
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(imageView, cx, cy, finalRadius, 0);
            circularReveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    imageView.setVisibility(View.INVISIBLE);
                    overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);
                    FullScreenImageActivity.super.onBackPressed();
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
            circularReveal.setDuration(800);
            circularReveal.start();
        } else {
            super.onBackPressed();
        }
    }
}
