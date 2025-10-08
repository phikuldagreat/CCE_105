package courier;

import models.Truck;
import java.util.List;

public interface ICourierManager {
    void acceptTruck(Truck truck);
	void declineTruck(String code, String plate);
	List<Truck> getAllTrucks();
}
