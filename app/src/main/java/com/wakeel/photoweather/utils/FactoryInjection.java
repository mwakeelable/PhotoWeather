package com.wakeel.photoweather.utils;

import android.support.annotation.NonNull;

import com.wakeel.photoweather.data.gallery.GalleryLocalDataSource;
import com.wakeel.photoweather.data.gallery.GalleryRepository;
import com.wakeel.photoweather.data.user.UserLocalDataSource;
import com.wakeel.photoweather.data.user.UserRepository;
import com.wakeel.photoweather.data.weather.WeatherRemoteDataSource;
import com.wakeel.photoweather.data.weather.WeatherRepository;

public class FactoryInjection {
    public static WeatherRepository provideWeatherRepository() {
        return WeatherRepository.getInstance(WeatherRemoteDataSource.getInstance());
    }

    public static UserRepository provideUserRepository(@NonNull SessionManager manager) {
        return UserRepository.getInstance(UserLocalDataSource.getInstance(manager));
    }

    public static GalleryRepository provideGalleryRepository() {
        return GalleryRepository.getInstance(GalleryLocalDataSource.getInstance());
    }
}
