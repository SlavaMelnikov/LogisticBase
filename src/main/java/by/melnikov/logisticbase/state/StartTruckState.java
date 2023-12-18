package by.melnikov.logisticbase.state;

import by.melnikov.logisticbase.entity.Truck;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static by.melnikov.logisticbase.main.Main.LOGISTIC_BASE;

public final class StartTruckState implements TruckState {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doOperation(Truck truck) {
        logger.info(truck.getName() + " driving to the logistic base.");
        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        LOGISTIC_BASE.addTruckInQueue(truck);
        truck.changeState(new QueueTruckState());
    }
}
