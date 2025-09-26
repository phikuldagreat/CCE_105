package rollback;
import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import java.io.Serializable;
import persistence.RollbackPersistence;
import Storage.StorageManager;
import Courier.CourierManager;

/**
 * Handles rollback functionality for storage and courier.
 */
public class RollbackManager implements IRollbackManager {
    private RollbackPersistence persistence = new RollbackPersistence();
    private StorageManager storageManager;
    private CourierManager courierManager;

    public RollbackManager(StorageManager sm, CourierManager cm) {
        this.storageManager = sm;
        this.courierManager = cm;
    }

    @Override
    public void rollbackStorage() {
        String state = persistence.loadRollbackState();
        // For simplicity, just reload storage from file
        storageManager = new StorageManager();
        JOptionPane.showMessageDialog(null, "Storage rolled back.", "Notification: " + state, JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void rollbackCourier() {
        String state = persistence.loadRollbackState();
        // For simplicity, just reload trucks from file
        courierManager = new CourierManager();
        JOptionPane.showMessageDialog(null, "Courier rolled back.", "Notification: " + state, JOptionPane.INFORMATION_MESSAGE);
    }
}
