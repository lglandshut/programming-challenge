package de.bcxp.challenge.io;

import de.bcxp.challenge.mapping.RecordMapper;

import java.util.List;

public class CsvDataReader<T> implements DataReader<T> {

    private final String filePath;
    private final String separator;
    private final RecordMapper<T> mapper;

    public CsvDataReader(String filePath, String separator, RecordMapper<T> mapper) {
        if (filePath == null) {
            throw new IllegalArgumentException("File Path can't be null.");
        }

        this.filePath = filePath;
        this.separator = separator;
        this.mapper = mapper;
    }

    @Override
    public List<T> readData() {
        return null;
    }
}
