package by.melnikov.logisticbase.state;

import by.melnikov.logisticbase.entity.Truck;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class FinishTruckState implements TruckState {
    private static final Logger logger = LogManager.getLogger();
    @Override
    public void doOperation(Truck truck) {
        logger.info("YAHOOO, " + truck.getName() + " going home!");
    }
}
