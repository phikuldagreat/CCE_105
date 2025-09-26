package Courier;

import models.Truck;
import persistence.CourierPersistence;

import java.util.List;

/**
 * Handles accepting, declining, and listing trucks.
 */
public class CourierManager implements ICourierManager {
    private List<Truck> trucks;
    private CourierPersistence persistence = new CourierPersistence();

    public CourierManager() {
        trucks = persistence.loadTrucks();
    }

    @Override
    public void acceptTruck(Truck truck) {
        if (trucks.size() < 20) {
            trucks.add(truck);
            persistence.saveTrucks(trucks);
        } else {
            System.out.println("Max 20 trucks allowed.");
        }
    }

    @Override
    public void declineTruck(String plate) {
        trucks.removeIf(t -> t.getPlate().equals(plate));
        persistence.saveTrucks(trucks);
    }

    @Override
    public List<Truck> getAllTrucks() {
        return trucks;
    }
}
