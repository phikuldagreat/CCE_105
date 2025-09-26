package Courier;

import models.Truck;
import java.util.List;

public interface ICourierManager {
    void acceptTruck(Truck truck);
    List<Truck> getAllTrucks();
	void declineTruck(String code, String plate);
}
