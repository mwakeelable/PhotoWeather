package com.wakeel.photoweather.data.weather;

import com.wakeel.photoweather.model.WeatherResponse;

public interface WeatherDataSource {

    void getWeather(double lat, double lon, GetWeatherCallback callback);

    interface GetWeatherCallback {
        void onGetWeather(WeatherResponse response);

        void onFailed(String message);
    }
}
