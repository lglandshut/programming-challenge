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

/**
 * DataReader implementation for csv data
 * @param <T> record type
 */
public class CsvDataReader<T> implements DataReader<T> {

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
     * Reads all data rows (header row excluded) and maps each row with the given mapper
     * @return records in file order; empty list if the file contains only a header
     * @throws IllegalArgumentException if the file cannot be read or a row cannot be mapped
     */
    @Override
    public List<T> readData() {
        List<T> result = new ArrayList<>();

        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setDelimiter(separator)
                .setHeader()
                .setSkipHeaderRecord(true)
                .build();

        try (Reader reader = Files.newBufferedReader(Path.of(filePath));
             CSVParser parser = format.parse(reader)) {

            for (CSVRecord csvRecord : parser) {
                result.add(mapper.map(csvRecord));
            }

        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading file " + filePath, e);
        }

        return result;
    }
}
