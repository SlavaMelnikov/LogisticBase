package by.melnikov.logisticbase.state;

import by.melnikov.logisticbase.entity.Truck;

sealed public interface TruckState permits StartTruckState, QueueTruckState, LoadTruckState, UnloadTruckState, FinishTruckState {
    void doOperation(Truck truck);
}
