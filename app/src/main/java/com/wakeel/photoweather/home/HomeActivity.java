package com.wakeel.photoweather.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.wakeel.photoweather.R;
import com.wakeel.photoweather.base.BaseActivity;
import com.wakeel.photoweather.utils.Constants;
import com.wakeel.photoweather.utils.RxEventBus;

public class HomeActivity extends BaseActivity {
    TextView welcomeTxt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        welcomeTxt = findViewById(R.id.welcome_txt);
        welcomeTxt.setText("Welcome: " + sessionManager.getUserDetails().get(Constants.USER_NAME));
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.camera_container, new HomeFragment())
                .commit();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        RxEventBus.getEventBus().sendToBus(new com.wakeel.photoweather.model.Image(data, resultCode));
    }
}
