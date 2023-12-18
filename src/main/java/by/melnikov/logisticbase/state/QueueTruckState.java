package by.melnikov.logisticbase.state;

import by.melnikov.logisticbase.entity.Terminal;
import by.melnikov.logisticbase.entity.Truck;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.melnikov.logisticbase.main.Main.LOGISTIC_BASE;

public final class QueueTruckState implements TruckState {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doOperation(Truck truck) {
        logger.info(truck.getName() + " stand in queue and waiting his turn....");
        Terminal freeTerminal = LOGISTIC_BASE.getFreeTerminal();
        truck.setTerminal(freeTerminal);
        logger.info("Terminal " + truck.getTerminal().getId() + " is free. " +  truck.getName() + " is going to there!");
        if ("LOAD" == truck.getOperation().name()) {
            truck.changeState(new LoadTruckState());
        } else if ("UNLOAD" == truck.getOperation().name()) {
            truck.changeState(new UnloadTruckState());
        }
    }
}
