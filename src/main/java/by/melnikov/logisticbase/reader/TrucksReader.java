package by.melnikov.logisticbase.reader;

import by.melnikov.logisticbase.exception.CustomException;

import java.util.List;

public interface TrucksReader {
    List<String[]> read(String path) throws CustomException;
}
