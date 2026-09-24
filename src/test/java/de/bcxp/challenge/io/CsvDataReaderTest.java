package de.bcxp.challenge.io;

import de.bcxp.challenge.mapping.WeatherRecordMapper;
import de.bcxp.challenge.model.WeatherRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvDataReaderTest {

    @Test
    @DisplayName("Null file path in constructor")
    void constructor_whenNullPathTest() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new CsvDataReader<>(null, ",", new WeatherRecordMapper()),
                "Constructor should throw IllegalArgumentException because of null filePath"
        );
        assertEquals("File Path can't be null.", thrown.getMessage());
    }

    @Test
    @DisplayName("Read data when Input file does not exist")
    void readData_whenFileMissingTest() {
        // Arrange
        DataReader<WeatherRecord> reader = new CsvDataReader<>("does/not/exist.csv", ",", new WeatherRecordMapper());

        // Test
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, reader::readData);

        // Assert
        assertEquals("Error reading file does/not/exist.csv", thrown.getMessage());
    }

    @Test
    @DisplayName("Read data when wrong Separator")
    void readData_whenWrongSeparatorTest() {
        // Arrange
        DataReader<WeatherRecord> weatherReader = new CsvDataReader<>("src/main/resources/de/bcxp/challenge/weather.csv", ";", new WeatherRecordMapper());

        // Test
        List<WeatherRecord> weatherData = weatherReader.readData();

        // Assert
        assertTrue(weatherData.isEmpty());
    }

    @Test
    @DisplayName("Read data validation")
    void readData() {
        // Arrange
        DataReader<WeatherRecord> weatherReader = new CsvDataReader<>("src/main/resources/de/bcxp/challenge/weather.csv", ",", new WeatherRecordMapper());

        // Test
        List<WeatherRecord> weatherData = weatherReader.readData();

        // Assert
        assertEquals(30, weatherData.size());
        assertEquals(new WeatherRecord(1, 88, 59), weatherData.get(0));
        assertEquals(new WeatherRecord(30, 90, 45), weatherData.get(29));
    }
}