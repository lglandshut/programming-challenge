package de.bcxp.challenge.mapping;

import de.bcxp.challenge.model.WeatherRecord;
import org.apache.commons.csv.CSVRecord;

/**
 * Maps a csv row to a WeatherRecord
 */
public class WeatherRecordMapper implements RecordMapper<WeatherRecord, CSVRecord> {

    @Override
    public WeatherRecord map(CSVRecord csvRecord) {
        return new WeatherRecord(
                Integer.parseInt(csvRecord.get("Day")),
                Integer.parseInt(csvRecord.get("MxT")),
                Integer.parseInt(csvRecord.get("MnT"))
                );
    }
}
