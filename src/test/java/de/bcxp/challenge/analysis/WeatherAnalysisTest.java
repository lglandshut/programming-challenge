package de.bcxp.challenge.analysis;

import de.bcxp.challenge.model.WeatherRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeatherAnalysisTest {

    @Test
    @DisplayName("Returns day with smallest temperature spread")
    void getDayWithSmallestTemperatureSpread_whenMultipleRecords_returnsDayWithSmallestSpread() {
        // Arrange
        List<WeatherRecord> weatherData = List.of(
                new WeatherRecord(1, 88, 59),  // spread 29
                new WeatherRecord(2, 79, 63),  // spread 16
                new WeatherRecord(3, 77, 55)   // spread 22
        );

        // Test
        int day = WeatherAnalysis.getDayWithSmallestTemperatureSpread(weatherData);

        // Assert
        assertEquals(2, day);
    }

    @Test
    @DisplayName("Returns first day when it has the smallest temperature spread")
    void getDayWithSmallestTemperatureSpread_whenFirstRecordIsSmallest_returnsFirstDay() {
        // Arrange
        List<WeatherRecord> weatherData = List.of(
                new WeatherRecord(1, 70, 65),  // spread 5
                new WeatherRecord(2, 79, 63),  // spread 16
                new WeatherRecord(3, 77, 55)   // spread 22
        );

        // Test
        int day = WeatherAnalysis.getDayWithSmallestTemperatureSpread(weatherData);

        // Assert
        assertEquals(1, day);
    }

    @Test
    @DisplayName("Returns last day when it has the smallest temperature spread")
    void getDayWithSmallestTemperatureSpread_whenLastRecordIsSmallest_returnsLastDay() {
        // Arrange
        List<WeatherRecord> weatherData = List.of(
                new WeatherRecord(1, 88, 59),  // spread 29
                new WeatherRecord(2, 79, 63),  // spread 16
                new WeatherRecord(3, 70, 65)   // spread 5
        );

        // Test
        int day = WeatherAnalysis.getDayWithSmallestTemperatureSpread(weatherData);

        // Assert
        assertEquals(3, day);
    }

    @Test
    @DisplayName("Returns first day when several days have the same smallest temperature spread")
    void getDayWithSmallestTemperatureSpread_whenTie_returnsFirstDay() {
        // Arrange
        List<WeatherRecord> weatherData = List.of(
                new WeatherRecord(1, 88, 59),  // spread 29
                new WeatherRecord(2, 70, 65),  // spread 5
                new WeatherRecord(3, 60, 55)   // spread 5
        );

        // Test
        int day = WeatherAnalysis.getDayWithSmallestTemperatureSpread(weatherData);

        // Assert
        assertEquals(2, day);
    }

    @Test
    @DisplayName("Returns the only day when there is a single record")
    void getDayWithSmallestTemperatureSpread_whenSingleRecord_returnsThatDay() {
        // Arrange
        List<WeatherRecord> weatherData = List.of(new WeatherRecord(7, 80, 60));

        // Test
        int day = WeatherAnalysis.getDayWithSmallestTemperatureSpread(weatherData);

        // Assert
        assertEquals(7, day);
    }

    @Test
    @DisplayName("Handles negative temperatures")
    void getDayWithSmallestTemperatureSpread_whenNegativeTemperatures_returnsDayWithSmallestSpread() {
        // Arrange
        List<WeatherRecord> weatherData = List.of(
                new WeatherRecord(1, -2, -15), // spread 13
                new WeatherRecord(2, 3, -4),   // spread 7
                new WeatherRecord(3, 5, -10)   // spread 15
        );

        // Test
        int day = WeatherAnalysis.getDayWithSmallestTemperatureSpread(weatherData);

        // Assert
        assertEquals(2, day);
    }

    @Test
    @DisplayName("Throws IllegalArgumentException when weather data is empty")
    void getDayWithSmallestTemperatureSpread_whenEmptyList_throwsIllegalArgumentException() {
        // Arrange
        List<WeatherRecord> weatherData = Collections.emptyList();

        // Test
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> WeatherAnalysis.getDayWithSmallestTemperatureSpread(weatherData)
        );

        // Assert
        assertEquals("Weather data can't be null or empty.", thrown.getMessage());
    }

    @Test
    @DisplayName("Throws IllegalArgumentException when weather data is null")
    void getDayWithSmallestTemperatureSpread_whenNull_throwsIllegalArgumentException() {
        // Test
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> WeatherAnalysis.getDayWithSmallestTemperatureSpread(null)
        );

        // Assert
        assertEquals("Weather data can't be null or empty.", thrown.getMessage());
    }
}
