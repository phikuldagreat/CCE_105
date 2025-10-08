package courier;

import models.Truck;
import persistence.CourierPersistence;
import queue.QueueImp;  // your custom queue

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CourierManager implements ICourierManager {
    private QueueImp<Truck> trucks = new QueueImp<>();   // FIFO queue
    private CourierPersistence persistence = new CourierPersistence();

    public CourierManager() {
        // Load existing trucks from persistence
        List<Truck> loaded = persistence.loadTrucks();
        for (Truck t : loaded) {
            trucks.enqueue(t);
        }
    }

    @Override
    public void acceptTruck(Truck truck) {
        if (trucks.size() < 20) {
            trucks.enqueue(truck);
            persistence.saveTrucks(trucksToList());
        } else {
            JOptionPane.showMessageDialog(null, "Max 20 trucks allowed.");
        }
    }

    @Override
    public void declineTruck(String code, String plate) {
        trucks.removeIf(t -> t.getPlate().equals(plate));
        persistence.saveTrucks(trucksToList());
    }

    @Override
    public List<Truck> getAllTrucks() {
        return trucksToList();
    }

    // Convert queue to list for saving/persistence
    private List<Truck> trucksToList() {
        List<Truck> list = new ArrayList<>(trucks.toList());
        return list;
    }
}
