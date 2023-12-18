package by.melnikov.logisticbase.parser;

import by.melnikov.logisticbase.entity.Truck;

import java.util.List;
import java.util.PriorityQueue;

public interface TrucksParser {
    List<Truck> parse(List<String[]> lines);
}
