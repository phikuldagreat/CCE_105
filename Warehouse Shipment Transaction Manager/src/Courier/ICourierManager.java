package Courier;

import models.Truck;
import java.util.List;

/**
 * Interface for managing courier operations (trucks).
 */
public interface ICourierManager {
    void acceptTruck(Truck truck);
    void declineTruck(String plate);
    List<Truck> getAllTrucks();
}
