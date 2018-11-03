package com.wakeel.photoweather.data.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private static WeatherWebServices weatherWebServices;
    private static Retrofit retrofit;


    public static WeatherWebServices getWeatherWebServices() {
        weatherWebServices = getRetrofit().create(WeatherWebServices.class);
        return weatherWebServices;
    }

    private static Retrofit getRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }
}
