package com.wakeel.photoweather.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.wakeel.photoweather.R;
import com.wakeel.photoweather.base.BaseActivity;
import com.wakeel.photoweather.home.HomeActivity;
import com.wakeel.photoweather.login.LoginActivity;
import com.wakeel.photoweather.utils.FactoryInjection;

public class SplashActivity extends BaseActivity implements SplashContract.SplashView {
    SplashContract.SplashPresenter splashPresenter;
    int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashPresenter = new SplashPresenter(FactoryInjection.provideUserRepository(sessionManager),
                this, sessionManager);
        splashPresenter.checkIsLogged();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void userIsLoggedIn() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Go to Home
                Intent homeIntent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void anonymousUser() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Go to Login
                Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void setPresenter(SplashContract.SplashPresenter presenter) {
        splashPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean showLoading) {

    }
}
