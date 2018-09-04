package com.auvx.melloo.context;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.auvx.melloo.constant.AppLocalDataDir;

public class Melloo extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public static SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(AppLocalDataDir.APP_SHARED_PREFERENCE_NAME, MODE_PRIVATE);
    }
}
