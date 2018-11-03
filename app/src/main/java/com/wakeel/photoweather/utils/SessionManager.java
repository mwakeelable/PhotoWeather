package com.wakeel.photoweather.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.wakeel.photoweather.PhotoWeatherApplication;
import com.wakeel.photoweather.login.LoginActivity;

import java.util.HashMap;

public class SessionManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context mContext;
    private int PRIVATE_MODE = 0;
    private final String PREF_NAME = PhotoWeatherApplication.TAG;

    public SessionManager(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String username) {
        editor.putBoolean(Constants.IS_LOGIN, true);
        editor.putString(Constants.USER_NAME, username);
        editor.apply();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(Constants.USER_NAME, pref.getString(Constants.USER_NAME, null));
        return user;
    }

    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            Intent i = new Intent(mContext, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
        }
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
        Intent i = new Intent(mContext, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(i);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(Constants.IS_LOGIN, false);
    }
}
