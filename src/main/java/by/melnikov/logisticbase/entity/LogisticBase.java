package by.melnikov.logisticbase.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LogisticBase {
    private static final Logger logger = LogManager.getLogger();
    public static final int MAX_CAPACITY = 2000;
    private static final Lock lock = new ReentrantLock(true);
    public static LogisticBase instance;
    private final Semaphore semaphore;
    private final Deque<Terminal> freeTerminals = new ArrayDeque<>();
    private final Deque<Truck> trucksQueue = new ArrayDeque<>();
    private final AtomicInteger currentWarehouseLoad;


    private LogisticBase() {
        freeTerminals.add(new Terminal(1));
        freeTerminals.add(new Terminal(2));
        freeTerminals.add(new Terminal(3));
        semaphore = new Semaphore(freeTerminals.size());
        currentWarehouseLoad = new AtomicInteger(MAX_CAPACITY / 2);
        logger.info("Logistic base is open. Number of terminals: " + freeTerminals.size() + ". Current warehouse load: " + currentWarehouseLoad.get());
    }

    public static LogisticBase getInstance() {
        if (instance == null) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new LogisticBase();
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public void addTruckInQueue(Truck truck) {
        try {
            lock.lock();
            if (truck.isPriority()) {
                trucksQueue.addFirst(truck);
            } else {
                trucksQueue.addLast(truck);
            }
        } finally {
            lock.unlock();
        }
    }

    public Deque<Truck> getTrucksQueue() {
        return trucksQueue;
    }

    public void loadTruck(int cargo) {
        currentWarehouseLoad.addAndGet(-cargo);
    }

    public void unloadTruck(int cargo) {
        currentWarehouseLoad.addAndGet(cargo);
    }

    public int getCurrentWarehouseLoad() {
        return currentWarehouseLoad.get();
    }

    public boolean isFull() {
        return currentWarehouseLoad.get() == MAX_CAPACITY;
    }

    public boolean isEmpty() {
        return currentWarehouseLoad.get() == 0;
    }

    public Terminal getFreeTerminal() {
        Terminal freeTerminal = null;
        try {
            semaphore.acquire();
            lock.lock();
            freeTerminal = freeTerminals.pop();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
        return freeTerminal;
    }

    public void releaseTerminal(Terminal terminal) {
        try {
            lock.lock();
            freeTerminals.addLast(terminal);
            semaphore.release();
        } finally {
            lock.unlock();
        }
    }
}
