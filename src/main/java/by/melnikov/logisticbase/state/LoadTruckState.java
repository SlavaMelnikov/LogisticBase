package by.melnikov.logisticbase.state;

import by.melnikov.logisticbase.entity.Truck;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static by.melnikov.logisticbase.main.Main.LOGISTIC_BASE;

public final class LoadTruckState implements TruckState {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doOperation(Truck truck) {
        int loadingTime = 0;
        switch (truck.getSize()) {
            case SMALL  -> loadingTime = new Random().nextInt(50, 100);
            case MEDIUM -> loadingTime = new Random().nextInt(150, 250);
            case LARGE  -> loadingTime = new Random().nextInt(300, 500);
        }
        LOGISTIC_BASE.loadTruck(truck.getSize().getCapacity());
        try {
            TimeUnit.MILLISECONDS.sleep(loadingTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logger.info(truck.getName() + " complete loading. It took " + loadingTime + " minutes. Current load of the base: " + LOGISTIC_BASE.getCurrentWarehouseLoad());
        LOGISTIC_BASE.releaseTerminal(truck.getTerminal());
        truck.changeState(new FinishTruckState());
    }
}
