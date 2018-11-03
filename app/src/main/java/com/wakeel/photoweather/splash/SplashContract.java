package com.wakeel.photoweather.splash;

import com.wakeel.photoweather.base.BasePresenter;
import com.wakeel.photoweather.base.BaseView;

public class SplashContract {
    interface SplashPresenter extends BasePresenter {
        void checkIsLogged();
    }

    interface SplashView extends BaseView<SplashPresenter> {
        void userIsLoggedIn();

        void anonymousUser();
    }
}
