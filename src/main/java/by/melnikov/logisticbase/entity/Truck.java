package by.melnikov.logisticbase.entity;

import by.melnikov.logisticbase.state.*;

import java.util.Objects;
import java.util.StringJoiner;

public class Truck implements Runnable {
    public enum Size {
        SMALL(50), MEDIUM(100), LARGE(200);

        private final int capacity;

        Size(int capacity) {
            this.capacity = capacity;
        }

        public int getCapacity() {
            return capacity;
        }
    }

    public enum Operation {
        LOAD, UNLOAD
    }

    private final long id;
    private final Size size;
    private final Operation operation;
    private String name;
    private boolean priority;
    private TruckState currentTruckState;
    private Terminal terminal;

    public Truck(long id, String name, Size size, Operation operation, boolean priority) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.operation = operation;
        this.priority = priority;
        currentTruckState = new StartTruckState();
    }

    public long getId() {
        return id;
    }

    public Size getSize() {
        return size;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public TruckState getCurrentTruckState() {
        return currentTruckState;
    }

    public void changeState(TruckState newTruckState) {
        this.currentTruckState = newTruckState;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal freeTerminal) {
        this.terminal = freeTerminal;
    }

    @Override
    public void run() {
        while (true) {
            currentTruckState.doOperation(this);            // StartTruckState -> QueueTruckState -> LoadTruckState -> FinishTruckState
            if (currentTruckState instanceof FinishTruckState) {  //                                     ↘                 ↗
                currentTruckState.doOperation(this);        //                                       UnloadTruckState
                break;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Truck truck = (Truck) o;

        if (id != truck.id) return false;
        if (priority != truck.priority) return false;
        if (size != truck.size) return false;
        if (operation != truck.operation) return false;
        if (!Objects.equals(name, truck.name)) return false;
        return Objects.equals(currentTruckState, truck.currentTruckState);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (priority ? 1 : 0);
        result = 31 * result + (currentTruckState != null ? currentTruckState.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Truck.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("size=" + size)
                .add("operation=" + operation)
                .add("priority=" + priority)
                .toString();
    }
}
