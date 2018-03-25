package com.interview.ammaryali.movieapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferenceManager {
    private static final String PREF_NAME = "movieflix_welcome";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static Context mcontext;

    public MyPreferenceManager(Context context) {
        mcontext = context;
    }

    public static boolean isFirstTimeLaunch() {
        return (mcontext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)).getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public static void setFirstTimeLaunch(boolean isFirstTime) {
        (mcontext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)).edit().putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime).apply();
    }

}
