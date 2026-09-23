package de.bcxp.challenge.analysis;

import de.bcxp.challenge.model.CountryRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CountryAnalysisTest {

    /**
     * Only name, population and area are relevant for the population density
     */
    private static CountryRecord country(String name, long population, long area) {
        return new CountryRecord(name, "Capital", "Founder", population, area, 0, 0.0, 0);
    }

    @Test
    @DisplayName("Returns country with highest population density")
    void getCountryWithHighestPopulationDensity_whenMultipleRecords_returnsCountryWithHighestDensity() {
        // Arrange
        List<CountryRecord> countryData = List.of(
                country("Austria", 8926000, 83855),      // density ~106.4
                country("Netherlands", 17614840, 41543), // density ~424.0
                country("Finland", 5527493, 338424)      // density ~16.3
        );

        // Test
        String country = CountryAnalysis.getCountryWithHighestPopulationDensity(countryData);

        // Assert
        assertEquals("Netherlands", country);
    }

    @Test
    @DisplayName("Returns first country when it has the highest population density")
    void getCountryWithHighestPopulationDensity_whenFirstRecordIsHighest_returnsFirstCountry() {
        // Arrange
        List<CountryRecord> countryData = List.of(
                country("Malta", 516100, 316),           // density ~1633.2
                country("Austria", 8926000, 83855),      // density ~106.4
                country("Finland", 5527493, 338424)      // density ~16.3
        );

        // Test
        String country = CountryAnalysis.getCountryWithHighestPopulationDensity(countryData);

        // Assert
        assertEquals("Malta", country);
    }

    @Test
    @DisplayName("Returns last country when it has the highest population density")
    void getCountryWithHighestPopulationDensity_whenLastRecordIsHighest_returnsLastCountry() {
        // Arrange
        List<CountryRecord> countryData = List.of(
                country("Austria", 8926000, 83855),      // density ~106.4
                country("Finland", 5527493, 338424),     // density ~16.3
                country("Malta", 516100, 316)            // density ~1633.2
        );

        // Test
        String country = CountryAnalysis.getCountryWithHighestPopulationDensity(countryData);

        // Assert
        assertEquals("Malta", country);
    }

    @Test
    @DisplayName("Returns first country when several countries have the same highest population density")
    void getCountryWithHighestPopulationDensity_whenTie_returnsFirstCountry() {
        // Arrange
        List<CountryRecord> countryData = List.of(
                country("Finland", 5527493, 338424),     // density ~16.3
                country("A", 1000, 10),                  // density 100
                country("B", 2000, 20)                   // density 100
        );

        // Test
        String country = CountryAnalysis.getCountryWithHighestPopulationDensity(countryData);

        // Assert
        assertEquals("A", country);
    }

    @Test
    @DisplayName("Decimal places of the population density are taken into account")
    void getCountryWithHighestPopulationDensity_whenDensitiesDifferOnlyInDecimals_returnsCountryWithHighestDensity() {
        // Arrange
        List<CountryRecord> countryData = List.of(
                country("A", 10, 4),                     // density 2.5
                country("B", 11, 4)                      // density 2.75 (integer division would give 2 for both)
        );

        // Test
        String country = CountryAnalysis.getCountryWithHighestPopulationDensity(countryData);

        // Assert
        assertEquals("B", country);
    }

    @Test
    @DisplayName("Returns the only country when there is a single record")
    void getCountryWithHighestPopulationDensity_whenSingleRecord_returnsThatCountry() {
        // Arrange
        List<CountryRecord> countryData = List.of(country("Austria", 8926000, 83855));

        // Test
        String country = CountryAnalysis.getCountryWithHighestPopulationDensity(countryData);

        // Assert
        assertEquals("Austria", country);
    }

    @Test
    @DisplayName("Throws IllegalArgumentException when country data is empty")
    void getCountryWithHighestPopulationDensity_whenEmptyList_throwsIllegalArgumentException() {
        // Arrange
        List<CountryRecord> countryData = Collections.emptyList();

        // Test
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> CountryAnalysis.getCountryWithHighestPopulationDensity(countryData)
        );

        // Assert
        assertEquals("Country data can't be empty.", thrown.getMessage());
    }

    @Test
    @DisplayName("Throws NullPointerException when country data is null")
    void getCountryWithHighestPopulationDensity_whenNull_throwsNullPointerException() {
        // Test & Assert
        assertThrows(
                NullPointerException.class,
                () -> CountryAnalysis.getCountryWithHighestPopulationDensity(null)
        );
    }
}
