package de.bcxp.challenge.analysis;

import de.bcxp.challenge.model.WeatherRecord;

import java.util.List;

/**
 * Analyzes weather data
 */
public class WeatherAnalysis {

    /**
     * Calculates the day with the smallest Temperature Spread of a given List of WeatherRecords
     * @param weatherData List of WeatherRecords
     * @return Day number of the smallest Temperature Spread
     */
    public static int getDayWithSmallestTemperatureSpread(List<WeatherRecord> weatherData) {

        if (weatherData == null || weatherData.isEmpty()) {
            throw new IllegalArgumentException("Weather data can't be null or empty.");
        }

        WeatherRecord smallestTemperatureSpread = weatherData.get(0);
        for (WeatherRecord record : weatherData) {
            if(record.temperatureSpread() < smallestTemperatureSpread.temperatureSpread()) {
                smallestTemperatureSpread = record;
            }
        }
        return smallestTemperatureSpread.day();
    }
}
