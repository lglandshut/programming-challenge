package de.bcxp.challenge.mapping;

/**
 * Converts raw data row into a record
 * @param <T> type of the resulting record
 * @param <S> type of the source row, e.g. CSVRecord
 */
public interface RecordMapper<T, S> {
    T map(S source);
}
