package de.bcxp.challenge.analysis;

import de.bcxp.challenge.model.WeatherRecord;

import java.util.Comparator;
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

        return weatherData.stream()
                .min(Comparator.comparingInt(WeatherRecord::temperatureSpread))
                .map(WeatherRecord::day)
                .orElseThrow(() -> new IllegalArgumentException("Weather data can't be empty."));
    }
}
