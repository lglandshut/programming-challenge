package de.bcxp.challenge.mapping;

import de.bcxp.challenge.model.WeatherRecord;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class WeatherRecordMapperTest {

    private final WeatherRecordMapper mapper = new WeatherRecordMapper();

    private static CSVRecord csvRecord(String csv) {
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build();

        try (CSVParser parser = format.parse(new StringReader(csv))) {
            return parser.getRecords().get(0);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    @DisplayName("Valid row is mapped to WeatherRecord")
    void map_whenValidRow_returnsWeatherRecord() {
        // Arrange
        CSVRecord csvRecord = csvRecord("Day,MxT,MnT\n1,88,59");

        // Test
        WeatherRecord weatherRecord = mapper.map(csvRecord);

        // Assert
        assertEquals(new WeatherRecord(1, 88, 59), weatherRecord);
    }

    @Test
    @DisplayName("Columns are mapped by header name, not by position")
    void map_whenColumnsInDifferentOrder_mapsByHeaderName() {
        // Arrange
        CSVRecord csvRecord = csvRecord("MnT,Day,MxT\n59,1,88");

        // Test
        WeatherRecord weatherRecord = mapper.map(csvRecord);

        // Assert
        assertEquals(new WeatherRecord(1, 88, 59), weatherRecord);
    }

    @Test
    @DisplayName("Additional columns are ignored")
    void map_whenAdditionalColumns_ignoresThem() {
        // Arrange
        CSVRecord csvRecord = csvRecord("Day,MxT,MnT,AvT,AvDP\n1,88,59,74,53.8");

        // Test
        WeatherRecord weatherRecord = mapper.map(csvRecord);

        // Assert
        assertEquals(new WeatherRecord(1, 88, 59), weatherRecord);
    }

    @Test
    @DisplayName("Negative temperatures are mapped")
    void map_whenNegativeTemperatures_returnsWeatherRecord() {
        // Arrange
        CSVRecord csvRecord = csvRecord("Day,MxT,MnT\n5,-2,-15");

        // Test
        WeatherRecord weatherRecord = mapper.map(csvRecord);

        // Assert
        assertEquals(new WeatherRecord(5, -2, -15), weatherRecord);
    }

    @Test
    @DisplayName("Non-numeric value throws NumberFormatException")
    void map_whenNonNumericValue_throwsNumberFormatException() {
        // Arrange
        CSVRecord csvRecord = csvRecord("Day,MxT,MnT\n1,abc,59");

        // Test & Assert
        assertThrows(NumberFormatException.class, () -> mapper.map(csvRecord));
    }

    @Test
    @DisplayName("Decimal value throws NumberFormatException")
    void map_whenDecimalValue_throwsNumberFormatException() {
        // Arrange
        CSVRecord csvRecord = csvRecord("Day,MxT,MnT\n1,88.5,59");

        // Test & Assert
        assertThrows(NumberFormatException.class, () -> mapper.map(csvRecord));
    }

}
