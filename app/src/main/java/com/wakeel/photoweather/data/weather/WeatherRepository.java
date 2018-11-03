package com.wakeel.photoweather.data.weather;

import io.reactivex.annotations.NonNull;

public class WeatherRepository implements WeatherDataSource {
    private static WeatherRepository INSTANCE = null;
    private static WeatherDataSource weatherDataSource;

    private WeatherRepository(@NonNull WeatherDataSource weatherDataSource) {
        this.weatherDataSource = weatherDataSource;
    }

    public static WeatherRepository getInstance(WeatherDataSource weatherDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new WeatherRepository(weatherDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getWeather(double lat, double lon, GetWeatherCallback callback) {
        weatherDataSource.getWeather(lat,lon, callback);
    }
}
