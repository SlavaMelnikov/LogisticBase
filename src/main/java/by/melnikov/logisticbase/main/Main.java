package by.melnikov.logisticbase.main;

import by.melnikov.logisticbase.entity.LogisticBase;
import by.melnikov.logisticbase.entity.Truck;
import by.melnikov.logisticbase.exception.CustomException;
import by.melnikov.logisticbase.parser.TrucksParser;
import by.melnikov.logisticbase.parser.impl.TrucksFromCsvFileParser;
import by.melnikov.logisticbase.reader.TrucksReader;
import by.melnikov.logisticbase.reader.impl.TrucksFromCsvFileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final Logger logger = LogManager.getLogger();
    public static final String TRUCKS_DATABASE_PATH = "src//main//resources//database.csv"; //FIXME
    public static final LogisticBase LOGISTIC_BASE = LogisticBase.getInstance();

    public static void main(String[] args) throws CustomException {
        TrucksReader reader = new TrucksFromCsvFileReader();
        List<String[]> csvFileLines = reader.read(TRUCKS_DATABASE_PATH);
        TrucksParser parser = new TrucksFromCsvFileParser();
        List<Truck> trucks = parser.parse(csvFileLines);
        logger.info("Trucks were successfully read and created from csv file");

        logger.info("Another working day is starting... :(");
        ExecutorService executor = Executors.newFixedThreadPool(trucks.size());
        for (Truck truck : trucks) {
            executor.execute(truck);
        }
        executor.shutdown();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (LOGISTIC_BASE.getTrucksQueue().isEmpty()) {
            logger.info("End of the busy day, all trucks were served!");
        } else {
            logger.info("Something wrong... " + LOGISTIC_BASE.getTrucksQueue().size() + " trucks weren't served.");
        }
    }
}
