package de.bcxp.challenge;

import de.bcxp.challenge.analysis.WeatherAnalysis;
import de.bcxp.challenge.io.CsvDataReader;
import de.bcxp.challenge.io.DataReader;
import de.bcxp.challenge.mapping.CountryRecordMapper;
import de.bcxp.challenge.mapping.WeatherRecordMapper;
import de.bcxp.challenge.model.CountryRecord;
import de.bcxp.challenge.model.WeatherRecord;

import java.util.List;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 */
public final class App {

    /**
     * This is the main entry method of your program.
     * @param args The CLI arguments passed
     */
    public static void main(String... args) {

        // --- TASK 1 ---
        DataReader<WeatherRecord> weatherReader = new CsvDataReader<>(
                "src/main/resources/de/bcxp/challenge/weather.csv", ",", new WeatherRecordMapper());
        List<WeatherRecord> weatherRecords = weatherReader.readData();
        System.out.printf("Day with smallest temperature spread: %s%n", WeatherAnalysis.getDayWithSmallestTemperatureSpread(weatherRecords));

        // --- TASK 2 ---
        DataReader<CountryRecord> countryReader = new CsvDataReader<>(
                "src/main/resources/de/bcxp/challenge/countries.csv", ";", new CountryRecordMapper());
        List<CountryRecord> countryRecords = countryReader.readData();
        countryRecords.forEach(System.out::println);
        String countryWithHighestPopulationDensity = "Some country"; // Your population density analysis function call …
        System.out.printf("Country with highest population density: %s%n", countryWithHighestPopulationDensity);
    }
}
