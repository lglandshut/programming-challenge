package de.bcxp.challenge.model;

/**
 * Record class for weather data
 */
public record WeatherRecord(int day, int maxTemp, int minTemp) {

    public int temperatureSpread() {
        return maxTemp - minTemp;
    }
}
