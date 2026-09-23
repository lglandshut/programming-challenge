package de.bcxp.challenge.mapping;

import de.bcxp.challenge.model.CountryRecord;
import org.apache.commons.csv.CSVRecord;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Maps a csv row to a CountryRecord
 */
public class CountryRecordMapper implements RecordMapper<CountryRecord, CSVRecord> {

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(Locale.GERMANY);

    @Override
    public CountryRecord map(CSVRecord csvRecord) {
        return new CountryRecord(
                csvRecord.get("Name"),
                csvRecord.get("Capital"),
                csvRecord.get("Accession"),
                parseLong(csvRecord.get("Population")),
                parseLong(csvRecord.get("Area (km²)")),
                parseLong(csvRecord.get("GDP (US$ M)")),
                Double.parseDouble(csvRecord.get("HDI")),
                Integer.parseInt(csvRecord.get("MEPs"))
                );
    }

    /**
     * Helper-Method for robust parsing of long values
     * @param rawValue raw csv value
     * @return the parsed long value
     */
    private long parseLong(String rawValue) {
        try {
            return NUMBER_FORMAT.parse(rawValue).longValue();
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid number format: " + rawValue, e);
        }
    }
}