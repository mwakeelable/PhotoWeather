package com.wakeel.photoweather.data.weather;

import android.util.Log;

import com.wakeel.photoweather.data.network.APIClient;
import com.wakeel.photoweather.model.WeatherResponse;
import com.wakeel.photoweather.utils.Constants;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherRemoteDataSource implements WeatherDataSource {
    private static WeatherRemoteDataSource INSTANCE;

    private WeatherRemoteDataSource() {
    }

    public static WeatherRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WeatherRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getWeather(double lat, double lon, final GetWeatherCallback callback) {
        APIClient.getWeatherWebServices().getWeatherByCity(lat, lon, Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeatherResponse response) {
                        callback.onGetWeather(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailed("Something went wrong!!");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("OnComplete", "...");
                    }
                });
    }
}
