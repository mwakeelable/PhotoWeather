package com.wakeel.photoweather.home;

import android.support.annotation.NonNull;

import com.wakeel.photoweather.data.weather.WeatherDataSource;
import com.wakeel.photoweather.data.weather.WeatherRepository;
import com.wakeel.photoweather.model.WeatherResponse;

public class HomePresenter implements HomeContract.HomePresenter {
    private final WeatherRepository weatherRepository;
    private final HomeContract.HomeView mView;

    public HomePresenter(@NonNull WeatherRepository weatherRepository, @NonNull HomeContract.HomeView homeView) {
        this.weatherRepository = weatherRepository;
        this.mView = homeView;
        mView.setPresenter(this);
    }

    @Override
    public void getWeatherByCity(double lat, double lon) {
        mView.setLoadingIndicator(true);
        weatherRepository.getWeather(lat, lon, new WeatherDataSource.GetWeatherCallback() {
            @Override
            public void onGetWeather(WeatherResponse response) {
                mView.setLoadingIndicator(false);
                mView.onGetWeatherSuccess(response);
            }

            @Override
            public void onFailed(String message) {
                mView.setLoadingIndicator(false);
                mView.onGetWeatherFailed(message);
            }
        });
    }

    @Override
    public void start() {

    }
}
