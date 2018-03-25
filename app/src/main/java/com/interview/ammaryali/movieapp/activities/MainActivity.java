package com.interview.ammaryali.movieapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.interview.ammaryali.movieapp.R;

public class MainActivity extends AppCompatActivity {
    private static final String PREF_NAME = "movieapp_welcome";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = null;
                if (isFirstTimeLaunch()) {
                    setFirstTimeLaunch();
                    intent = new Intent(MainActivity.this, WelcomeActivity.class);
                } else {
                    intent = new Intent(MainActivity.this, HomeActivity.class);
                }

                startActivity(intent);
                finish();

            }
        }, 2000);
    }

    public boolean isFirstTimeLaunch() {
        return (getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)).getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstTimeLaunch() {
        (getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)).edit().putBoolean(IS_FIRST_TIME_LAUNCH, false).apply();
    }
}
