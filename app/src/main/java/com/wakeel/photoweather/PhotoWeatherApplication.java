package com.wakeel.photoweather;

import android.app.Application;

import com.facebook.FacebookSdk;

public class PhotoWeatherApplication extends Application {
    public static final String TAG = PhotoWeatherApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate();
    }
}
