package de.bcxp.challenge.mapping;

import de.bcxp.challenge.model.CountryRecord;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class CountryRecordMapperTest {

    private static final String HEADER = "Name;Capital;Accession;Population;Area (km²);GDP (US$ M);HDI;MEPs\n";

    private final CountryRecordMapper mapper = new CountryRecordMapper();

    /**
     * CSVRecord has no public constructor, so test records are created by parsing a csv string
     */
    private static CSVRecord csvRecord(String csv) {
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setDelimiter(";")
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
    @DisplayName("Valid row is mapped to CountryRecord")
    void map_whenValidRow_returnsCountryRecord() {
        // Arrange
        CSVRecord csvRecord = csvRecord(HEADER + "Austria;Vienna;1995;8926000;83855;447718;0.922;19");

        // Test
        CountryRecord countryRecord = mapper.map(csvRecord);

        // Assert
        assertEquals(new CountryRecord("Austria", "Vienna", "1995", 8926000, 83855, 447718, 0.922, 19), countryRecord);
    }

    @Test
    @DisplayName("Non-numeric accession like 'Founder' is kept as text")
    void map_whenAccessionIsFounder_keepsText() {
        // Arrange
        CSVRecord csvRecord = csvRecord(HEADER + "Belgium;Brussels;Founder;11566041;30528;517609;0.931;21");

        // Test
        CountryRecord countryRecord = mapper.map(csvRecord);

        // Assert
        assertEquals("Founder", countryRecord.accession());
    }

    @Test
    @DisplayName("Population in German number format is parsed")
    void map_whenPopulationInGermanNumberFormat_parsesValue() {
        // Arrange
        CSVRecord csvRecord = csvRecord(HEADER + "Croatia;Zagreb;2013;4.036.355,00;56594;60702;0.851;12");

        // Test
        CountryRecord countryRecord = mapper.map(csvRecord);

        // Assert
        assertEquals(4036355, countryRecord.population());
    }

    @Test
    @DisplayName("Columns are mapped by header name, not by position")
    void map_whenColumnsInDifferentOrder_mapsByHeaderName() {
        // Arrange
        CSVRecord csvRecord = csvRecord(
                "MEPs;HDI;GDP (US$ M);Area (km²);Population;Accession;Capital;Name\n"
                        + "19;0.922;447718;83855;8926000;1995;Vienna;Austria");

        // Test
        CountryRecord countryRecord = mapper.map(csvRecord);

        // Assert
        assertEquals(new CountryRecord("Austria", "Vienna", "1995", 8926000, 83855, 447718, 0.922, 19), countryRecord);
    }

    @Test
    @DisplayName("Non-numeric population throws IllegalArgumentException")
    void map_whenPopulationNotNumeric_throwsIllegalArgumentException() {
        // Arrange
        CSVRecord csvRecord = csvRecord(HEADER + "Austria;Vienna;1995;abc;83855;447718;0.922;19");

        // Test
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> mapper.map(csvRecord));

        // Assert
        assertEquals("Invalid number format: abc", thrown.getMessage());
        assertInstanceOf(ParseException.class, thrown.getCause());
    }

    @Test
    @DisplayName("Empty area throws IllegalArgumentException")
    void map_whenAreaEmpty_throwsIllegalArgumentException() {
        // Arrange
        CSVRecord csvRecord = csvRecord(HEADER + "Austria;Vienna;1995;8926000;;447718;0.922;19");

        // Test
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> mapper.map(csvRecord));

        // Assert
        assertEquals("Invalid number format: ", thrown.getMessage());
    }

    @Test
    @DisplayName("Non-numeric HDI throws NumberFormatException")
    void map_whenHdiNotNumeric_throwsNumberFormatException() {
        // Arrange
        CSVRecord csvRecord = csvRecord(HEADER + "Austria;Vienna;1995;8926000;83855;447718;abc;19");

        // Test & Assert
        assertThrows(NumberFormatException.class, () -> mapper.map(csvRecord));
    }

    @Test
    @DisplayName("Non-numeric MEPs throws NumberFormatException")
    void map_whenMepsNotNumeric_throwsNumberFormatException() {
        // Arrange
        CSVRecord csvRecord = csvRecord(HEADER + "Austria;Vienna;1995;8926000;83855;447718;0.922;abc");

        // Test & Assert
        assertThrows(NumberFormatException.class, () -> mapper.map(csvRecord));
    }

    @Test
    @DisplayName("Missing column throws IllegalArgumentException")
    void map_whenColumnMissing_throwsIllegalArgumentException() {
        // Arrange
        CSVRecord csvRecord = csvRecord("Name;Capital;Accession;Population\nAustria;Vienna;1995;8926000");

        // Test & Assert
        assertThrows(IllegalArgumentException.class, () -> mapper.map(csvRecord));
    }
}
