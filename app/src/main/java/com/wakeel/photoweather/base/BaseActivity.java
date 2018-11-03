package com.wakeel.photoweather.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wakeel.photoweather.utils.SessionManager;

public abstract class BaseActivity extends AppCompatActivity {
    public static final String TAG = BaseActivity.class.getCanonicalName();
    public SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        sessionManager = new SessionManager(this);
    }

    protected abstract int getLayout();
}
