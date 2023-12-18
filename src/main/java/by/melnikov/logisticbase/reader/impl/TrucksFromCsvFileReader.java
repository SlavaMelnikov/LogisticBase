package by.melnikov.logisticbase.reader.impl;

import by.melnikov.logisticbase.exception.CustomException;
import by.melnikov.logisticbase.reader.TrucksReader;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class TrucksFromCsvFileReader implements TrucksReader {
    @Override
    public List<String[]> read(String path) throws CustomException {
        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            return reader.readAll();
        } catch (IOException | CsvException e) {
            throw new CustomException(e);
        }
    }
}
