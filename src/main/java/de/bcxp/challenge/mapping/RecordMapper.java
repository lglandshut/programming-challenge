package de.bcxp.challenge.mapping;

import org.apache.commons.csv.CSVRecord;

public interface RecordMapper<T> {
    T map(CSVRecord csvRecord);
}
