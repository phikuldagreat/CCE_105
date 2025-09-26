package Courier;

import models.Truck;
import persistence.CourierPersistence;

import java.util.List;

public class CourierManager implements ICourierManager {
    private List<Truck> trucks;
    private CourierPersistence persistence = new CourierPersistence();

    public CourierManager() {
        trucks = persistence.loadTrucks();
    }

    public void acceptTruck(Truck truck) {
        if (trucks.size() < 20) {
            trucks.add(truck);
            persistence.saveTrucks(trucks);
        } else {
            System.out.println("Max 20 trucks allowed.");
        }
    }

    public void declineTruck(String code, String plate) {
        trucks.removeIf(t -> t.getPlate().equals(plate));
        persistence.saveTrucks(trucks);
    }

    public List<Truck> getAllTrucks() {
        return trucks;
    }
}
