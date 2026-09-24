package de.bcxp.challenge.io;

import de.bcxp.challenge.mapping.RecordMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DataReader implementation for csv data
 * @param <T> record type
 */
public class CsvDataReader<T> implements DataReader<T> {

    private static final Logger LOGGER = Logger.getLogger(CsvDataReader.class.getName());

    private final String filePath;
    private final String separator;
    private final RecordMapper<T, CSVRecord> mapper;

    /**
     * Creates a reader for the given csv file
     * @param filePath path to the csv file
     * @param separator column delimiter
     * @param mapper maps each csv row to a record of type T
     * @throws IllegalArgumentException if filePath is null
     */
    public CsvDataReader(String filePath, String separator, RecordMapper<T, CSVRecord> mapper) {
        if (filePath == null) {
            throw new IllegalArgumentException("File Path can't be null.");
        }

        this.filePath = filePath;
        this.separator = separator;
        this.mapper = mapper;
    }

    /**
     * Reads all data rows (header row excluded) and maps each row with the given mapper.
     * Rows that cannot be mapped are skipped and logged as warning.
     * @return valid records in file order; empty list if the file contains no valid data rows
     * @throws IllegalArgumentException if the file cannot be read
     */
    @Override
    public List<T> readData() {
        List<T> result = new ArrayList<>();
        int skippedRows = 0;

        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setDelimiter(separator)
                .setHeader()
                .setSkipHeaderRecord(true)
                .build();

        try (Reader reader = Files.newBufferedReader(Path.of(filePath));
             CSVParser parser = format.parse(reader)) {

            for (CSVRecord csvRecord : parser) {
                try {
                    result.add(mapper.map(csvRecord));
                } catch (IllegalArgumentException e) {
                    skippedRows++;
                    LOGGER.log(Level.WARNING, "Skipping invalid row {0} in {1}: {2}",
                            new Object[]{csvRecord.getRecordNumber(), filePath, e.getMessage()});
                }
            }

        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading file " + filePath, e);
        }

        LOGGER.log(Level.INFO, "Read {0} records from {1}, skipped {2} invalid rows",
                new Object[]{result.size(), filePath, skippedRows});
        return result;
    }
}
