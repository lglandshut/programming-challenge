package de.bcxp.challenge.io;

import java.util.List;

/**
 * Reads data from a source and returns it as a list of records
 * @param <T> Record type
 */
public interface DataReader<T> {
    List<T> readData();
}
