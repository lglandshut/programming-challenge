package de.bcxp.challenge.analysis;

import de.bcxp.challenge.model.CountryRecord;

import java.util.Comparator;
import java.util.List;

/**
 * Analyzes Country data
 */
public class CountryAnalysis {

    /**
     * Calculates the country with the highest population density in the given countryRecords
     * @param countryRecords List of countryRecords
     * @return Name of the country with the highest population density
     */
    public static String getCountryWithHighestPopulationDensity(List<CountryRecord> countryRecords) {

        return countryRecords.stream()
                .max(Comparator.comparingDouble(CountryRecord::populationDensity))
                .map(CountryRecord::name)
                .orElseThrow(() -> new IllegalArgumentException("Country data can't be empty."));
    }
}
