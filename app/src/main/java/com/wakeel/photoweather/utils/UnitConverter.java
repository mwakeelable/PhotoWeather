package com.wakeel.photoweather.utils;

public class UnitConverter {
    public static float convertTemperatureToCelsius(float temperature) {
        return UnitConverter.kelvinToCelsius(temperature);
    }

    public static float convertTemperatureToFahrenheit(float temperature) {
        return UnitConverter.kelvinToFahrenheit(temperature);
    }

    public static float kelvinToCelsius(float kelvinTemp) {
        return kelvinTemp - 273.15f;
    }

    public static float kelvinToFahrenheit(float kelvinTemp) {
        return (((9 * kelvinToCelsius(kelvinTemp)) / 5) + 32);
    }
}
