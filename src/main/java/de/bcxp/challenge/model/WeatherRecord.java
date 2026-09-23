package de.bcxp.challenge.model;

/**
 * Record class for weather data
 */
public record WeatherRecord(int day, int MxT, int MnT) {

    public int temperatureSpread() {
        return MxT - MnT;
    }
}
