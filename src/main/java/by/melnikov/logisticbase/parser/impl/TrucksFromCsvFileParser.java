package by.melnikov.logisticbase.parser.impl;

import by.melnikov.logisticbase.entity.Truck;
import by.melnikov.logisticbase.parser.TrucksParser;

import java.util.ArrayList;
import java.util.List;

public class TrucksFromCsvFileParser implements TrucksParser {
    @Override
    public List<Truck> parse(List<String[]> lines) {
        lines.removeFirst();
        List<Truck> trucks = new ArrayList<>();
        for (String[] line : lines) {
            Truck truck = new Truck(Long.parseLong(line[0]),
                                    line[1],
                                    Truck.Size.valueOf(line[2]),
                                    Truck.Operation.valueOf(line[3]),
                                    line[4].equalsIgnoreCase("YES") ? true : false);
            trucks.add(truck);
        }
        return trucks;
    }
}
