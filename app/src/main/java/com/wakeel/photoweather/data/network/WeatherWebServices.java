package com.wakeel.photoweather.data.network;

import com.wakeel.photoweather.model.WeatherResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherWebServices {
    @GET("weather")
    Observable<WeatherResponse> getWeatherByCity(@Query("lat") double lat,
                                                 @Query("lon") double lon,
                                                 @Query("APPID") String AppID);
}
