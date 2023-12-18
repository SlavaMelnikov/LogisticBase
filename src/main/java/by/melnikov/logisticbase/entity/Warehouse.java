package by.melnikov.logisticbase.entity;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Warehouse {
    public static final int MAX_CAPACITY = 500;
    private static final Warehouse instance = new Warehouse();
    private final AtomicInteger currentLoad;

    private Warehouse() {
        currentLoad = new AtomicInteger(new Random().nextInt(MAX_CAPACITY));
    }

    public static Warehouse getInstance() {
        return instance;
    }

    public int getCurrentLoad() {
        return currentLoad.get();
    }

    public boolean isFull() {
        return currentLoad.get() == MAX_CAPACITY;
    }
}
