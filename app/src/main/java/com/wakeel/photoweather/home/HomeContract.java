package com.wakeel.photoweather.home;

import com.wakeel.photoweather.base.BasePresenter;
import com.wakeel.photoweather.base.BaseView;
import com.wakeel.photoweather.model.WeatherResponse;

public class HomeContract {
    interface HomePresenter extends BasePresenter {
        void getWeatherByCity(double lat, double lon);
    }

    interface HomeView extends BaseView<HomePresenter> {
        void onGetWeatherSuccess(WeatherResponse response);

        void onGetWeatherFailed(String message);
    }
}
