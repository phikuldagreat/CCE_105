package rollback;

import javax.swing.JOptionPane;

import stack.StackImp;
import Storage.StorageManager;
import Courier.CourierManager;
import models.Item;
import models.Truck;

//Handles rollback functionality for storage and courier using a stack.
public class RollbackManager implements IRollbackManager {
    private StorageManager storageManager;
    private CourierManager courierManager;

    // stacks for history tracking
    private StackImp<Item> storageHistory = new StackImp<>();
    private StackImp<Truck> courierHistory = new StackImp<>();

    public RollbackManager(StorageManager sm, CourierManager cm) {
        this.storageManager = sm;
        this.courierManager = cm;
    }

    //Call this when adding an item
    public void recordStorageAction(Item item) {
        storageHistory.push(item);
    }

    //Call this when accepting/declining a truck
    public void recordCourierAction(Truck truck) {
        courierHistory.push(truck);
    }

    @Override
    public void rollbackStorage() {
        if (storageHistory.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No storage actions to rollback.");
            return;
        }
        Item last = storageHistory.pop();
        storageManager.removeItem(last.getCode()); // undo last add
        JOptionPane.showMessageDialog(null, "Rolled back storage: " + last.getName());
    }

    @Override
    public void rollbackCourier() {
        if (courierHistory.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No courier actions to rollback.");
            return;
        }
        Truck last = courierHistory.pop();
        courierManager.acceptTruck(last); // restore the last removed/declined truck
        JOptionPane.showMessageDialog(null, "Rolled back courier: " + last.getPlate());
    }
}
