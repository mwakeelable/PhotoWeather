package com.wakeel.photoweather.splash;

import android.support.annotation.NonNull;

import com.wakeel.photoweather.data.user.UserDataSource;
import com.wakeel.photoweather.data.user.UserRepository;
import com.wakeel.photoweather.utils.SessionManager;

public class SplashPresenter implements SplashContract.SplashPresenter {
    private final UserRepository userRepository;
    private final SplashContract.SplashView mView;
    private SessionManager manager;

    public SplashPresenter(@NonNull UserRepository userRepository,
                           @NonNull SplashContract.SplashView splashView,
                           @NonNull SessionManager manager) {
        this.userRepository = userRepository;
        this.mView = splashView;
        this.manager = manager;
        mView.setPresenter(this);
    }

    @Override
    public void checkIsLogged() {
        userRepository.checkUserLogged(manager, new UserDataSource.CheckingCallback() {
            @Override
            public void userLoggedIn() {
                mView.userIsLoggedIn();
            }

            @Override
            public void anonymousUser() {
                mView.anonymousUser();
            }
        });
    }

    @Override
    public void start() {

    }
}
