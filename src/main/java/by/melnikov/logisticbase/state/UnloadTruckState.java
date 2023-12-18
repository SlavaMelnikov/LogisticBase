package by.melnikov.logisticbase.state;

import static by.melnikov.logisticbase.main.Main.LOGISTIC_BASE;
import by.melnikov.logisticbase.entity.Truck;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public final class UnloadTruckState implements TruckState {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doOperation(Truck truck) {
        int unloadingTime = 0;
        switch (truck.getSize()) {
            case SMALL  -> unloadingTime = new Random().nextInt(25, 50);
            case MEDIUM -> unloadingTime = new Random().nextInt(100, 150);
            case LARGE  -> unloadingTime = new Random().nextInt(200, 300);
        }
        LOGISTIC_BASE.unloadTruck(truck.getSize().getCapacity());
        try {
            TimeUnit.MILLISECONDS.sleep(unloadingTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logger.info(truck.getName() + " complete unloading. It took " + unloadingTime + " minutes. Current load of the base: " + LOGISTIC_BASE.getCurrentWarehouseLoad());
        LOGISTIC_BASE.releaseTerminal(truck.getTerminal());
        truck.changeState(new FinishTruckState());
    }
}
