package gui;

import storage.StorageManager;
import courier.CourierManager;
import models.Item;
import models.Truck;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainGUI extends JFrame {

    //UI PANELS
    private JPanel storagePanel, courierPanel, deliveryPanel;
    private JPanel storageButtonsPanel, courierButtonsPanel, deliveryButtonsPanel;

    //TABLES + MODELS
    private JTable storageTable, courierTable, deliveryTable;
    private DefaultTableModel storageModel, courierModel, deliveryModel;

    //BACKEND MANAGERS
    private StorageManager storageManager;
    private CourierManager courierManager;
    private rollback.RollbackManager rollbackManager;

    public MainGUI() {
        setTitle("Warehouse System");
        setBounds(300, 0, 900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        storageManager = new StorageManager();
        courierManager = new CourierManager();
        rollbackManager = new rollback.RollbackManager(storageManager, courierManager);

        JPanel backgroundPanel = new JPanel(null);
        backgroundPanel.setBounds(0, 0, 900, 600);
        backgroundPanel.setBackground(Color.black);
        add(backgroundPanel);

        JPanel sidePanel = new JPanel(null);
        sidePanel.setBounds(10, 10, 180, 540);
        sidePanel.setBackground(new Color(200, 200, 255));
        backgroundPanel.add(sidePanel);

        int spacing = 60;
        int startY = (540 - (3 * spacing)) / 2;

        JButton storageBtn = new JButton("ITEM MANAGER");
        storageBtn.setBounds(10, startY, 160, 40);
        sidePanel.add(storageBtn);

        JButton courierBtn = new JButton("DRIVER MANAGER");
        courierBtn.setBounds(10, startY + spacing, 160, 40);
        sidePanel.add(courierBtn);

        JButton deliveryBtn = new JButton("DELIVERY MANAGER");
        deliveryBtn.setBounds(10, startY + spacing * 2, 160, 40);
        sidePanel.add(deliveryBtn);

        //STORAGE PANEL
        storagePanel = new JPanel(null);
        storagePanel.setBounds(200, 10, 680, 500);
        storagePanel.setBackground(new Color(200, 200, 255));
        backgroundPanel.add(storagePanel);

        JLabel storageLabel = new JLabel("ITEM MANAGER", SwingConstants.CENTER);
        storageLabel.setBounds(0, 0, 680, 30);
        storageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        storagePanel.add(storageLabel);

        String[] cols1 = {"Code", "Name", "Quantity"};
        storageModel = new DefaultTableModel(cols1, 0);
        storageTable = new JTable(storageModel);
        JScrollPane scroll1 = new JScrollPane(storageTable);
        scroll1.setBounds(20, 40, 640, 430);
        storagePanel.add(scroll1);

        storageButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        storageButtonsPanel.setBounds(200, 510, 680, 40);
        storageButtonsPanel.setBackground(Color.black);
        backgroundPanel.add(storageButtonsPanel);

        JButton addItemBtn = new JButton("Add Item");
        JButton removeItemBtn = new JButton("Remove Item");
        storageButtonsPanel.add(addItemBtn);
        storageButtonsPanel.add(removeItemBtn);

        //COURIER PANEL
        courierPanel = new JPanel(null);
        courierPanel.setBounds(200, 10, 680, 500);
        courierPanel.setBackground(new Color(200, 200, 255));
        backgroundPanel.add(courierPanel);

        JLabel courierLabel = new JLabel("DRIVER MANAGER", SwingConstants.CENTER);
        courierLabel.setBounds(0, 0, 680, 30);
        courierLabel.setFont(new Font("Arial", Font.BOLD, 14));
        courierPanel.add(courierLabel);

        String[] cols2 = {"Code", "Driver", "Plate"};
        courierModel = new DefaultTableModel(cols2, 0);
        courierTable = new JTable(courierModel);
        JScrollPane scroll2 = new JScrollPane(courierTable);
        scroll2.setBounds(20, 40, 640, 430);
        courierPanel.add(scroll2);

        courierButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        courierButtonsPanel.setBounds(200, 510, 680, 40);
        courierButtonsPanel.setBackground(Color.black);
        backgroundPanel.add(courierButtonsPanel);

        JButton acceptTruckBtn = new JButton("Accept Truck");
        JButton declineTruckBtn = new JButton("Remove Truck");
        courierButtonsPanel.add(acceptTruckBtn);
        courierButtonsPanel.add(declineTruckBtn);

        //DELIVERY PANEL
        deliveryPanel = new JPanel(null);
        deliveryPanel.setBounds(200, 10, 680, 500);
        deliveryPanel.setBackground(new Color(200, 200, 255));
        backgroundPanel.add(deliveryPanel);

        JLabel deliveryLabel = new JLabel("DELIVERY MANAGER", SwingConstants.CENTER);
        deliveryLabel.setBounds(0, 0, 680, 30);
        deliveryLabel.setFont(new Font("Arial", Font.BOLD, 14));
        deliveryPanel.add(deliveryLabel);

        String[] cols3 = {"Item Code", "Item Name", "Truck Code", "Driver Name", "Plate Number", "Quantity"};
        deliveryModel = new DefaultTableModel(cols3, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        deliveryTable = new JTable(deliveryModel);
        JScrollPane scroll3 = new JScrollPane(deliveryTable);
        scroll3.setBounds(20, 40, 640, 430);
        deliveryPanel.add(scroll3);

        deliveryButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        deliveryButtonsPanel.setBounds(200, 510, 680, 40);
        deliveryButtonsPanel.setBackground(Color.black);
        backgroundPanel.add(deliveryButtonsPanel);

        JButton assignCourierBtn = new JButton("Assign Courier");
        JButton cancelCourierBtn = new JButton("Cancel Courier");
        JButton completeOrderBtn = new JButton("Complete Order");
        deliveryButtonsPanel.add(assignCourierBtn);
        deliveryButtonsPanel.add(cancelCourierBtn);
        deliveryButtonsPanel.add(completeOrderBtn);

        completeOrderBtn.addActionListener(e -> {
            int r = deliveryTable.getSelectedRow();
            if (r >= 0) {
                String itemCode = (String) deliveryTable.getValueAt(r, 0);
                deliveryModel.removeRow(r);
                JOptionPane.showMessageDialog(this, "Order for item code " + itemCode + " completed!");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a delivery to complete.");
            }
        });

        storagePanel.setVisible(true);
        storageButtonsPanel.setVisible(true);
        courierPanel.setVisible(false);
        courierButtonsPanel.setVisible(false);
        deliveryPanel.setVisible(false);
        deliveryButtonsPanel.setVisible(false);

        storageBtn.addActionListener(e -> switchPanel(true, false, false));
        courierBtn.addActionListener(e -> switchPanel(false, true, false));
        deliveryBtn.addActionListener(e -> switchPanel(false, false, true));

        addItemBtn.addActionListener(e -> addItemDialog());
        removeItemBtn.addActionListener(e -> removeSelectedItem());

        acceptTruckBtn.addActionListener(e -> addTruckDialog());
        declineTruckBtn.addActionListener(e -> removeSelectedTruck());

        assignCourierBtn.addActionListener(e -> {
            deliverDialog();
            refreshStorageTable();
            refreshCourierTable();
            refreshDeliveryTable();
        });

        cancelCourierBtn.addActionListener(e -> {
        	int r = deliveryTable.getSelectedRow();
        	if (r >= 0) {
        	    String itemCode = (String) deliveryTable.getValueAt(r, 0);
        	    String itemName = (String) deliveryTable.getValueAt(r, 1);
        	    String code = (String) deliveryTable.getValueAt(r, 2);
        	    String driverName = (String) deliveryTable.getValueAt(r, 3);
        	    String plate = (String) deliveryTable.getValueAt(r, 4);
        	    Object qtyObj = deliveryTable.getValueAt(r, 5);
        	    int qty = (qtyObj instanceof Integer) ? (Integer) qtyObj : Integer.parseInt(qtyObj.toString());

        	    // restore truck
        	    Truck cancelledTruck = new Truck(code, driverName, plate);
        	    courierManager.acceptTruck(cancelledTruck);

        	    // restore item
        	    List<Item> items = storageManager.getAllItems();
        	    Item existing = null;
        	    for (Item i : items) {
        	        if (i.getCode().equals(itemCode)) {
        	            existing = i;
        	            break;
        	        }
        	    }

        	    if (existing != null) {
        	        existing.setQuantity(existing.getQuantity() + qty);
        	    } else {
        	        storageManager.addItem(new Item(itemCode, itemName, qty));
        	    }

        	    deliveryModel.removeRow(r);
        	    refreshStorageTable();
        	    refreshCourierTable();
        	    JOptionPane.showMessageDialog(this, "Courier cancelled and restored successfully.");
        	} else {
        	    JOptionPane.showMessageDialog(this, "Please select a delivery to cancel.");
        	}
        });

        refreshStorageTable();
        refreshCourierTable();
    }

    private void switchPanel(boolean showStorage, boolean showCourier, boolean showDelivery) {
        storagePanel.setVisible(showStorage);
        storageButtonsPanel.setVisible(showStorage);
        courierPanel.setVisible(showCourier);
        courierButtonsPanel.setVisible(showCourier);
        deliveryPanel.setVisible(showDelivery);
        deliveryButtonsPanel.setVisible(showDelivery);
    }

    private void refreshStorageTable() {
        DefaultTableModel model = storageModel;
        model.setRowCount(0);
        List<Item> items = storageManager.getAllItems();
        for (Item i : items) {
            model.addRow(new Object[]{i.getCode(), i.getName(), i.getQuantity()});
        }
    }

    private void refreshCourierTable() {
        DefaultTableModel model = courierModel;
        model.setRowCount(0);
        List<Truck> trucks = courierManager.getAllTrucks();
        for (Truck t : trucks) {
            model.addRow(new Object[]{t.getCode(), t.getDriver(), t.getPlate()});
        }
    }

    private void refreshDeliveryTable() {
    }

    //STORAGE ACTIONS
    private void addItemDialog() {
        JTextField codeField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField qtyField = new JTextField();

        Object[] message = {"Code:", codeField, "Name:", nameField, "Quantity:", qtyField};
        int option = JOptionPane.showConfirmDialog(this, message, "Add Item", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String code = codeField.getText().trim();
                String name = nameField.getText().trim();
                int qty = Integer.parseInt(qtyField.getText().trim());
                Item newItem = new Item(code, name, qty);
                storageManager.addItem(newItem);
                rollbackManager.recordStorageAction(newItem);

                int totalQty = 0;
                List<Item> items = storageManager.getAllItems();
                for (Item i : items) {
                    if (i.getCode().equals(code)) totalQty += i.getQuantity();
                }

                if (totalQty > 100) {
                    rollbackManager.rollbackStorage();
                    JOptionPane.showMessageDialog(this, "Quantity for item '" + name + "' exceeds 100. Action rolled back.");
                } else refreshStorageTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantity must be a number.");
            }
        }
    }

    private void removeSelectedItem() {
        int row = storageTable.getSelectedRow();
        if (row >= 0) {
            String code = (String) storageTable.getValueAt(row, 0);
            List<Item> items = storageManager.getAllItems();
            Item toRemove = null;
            for (Item i : items) {
                if (i.getCode().equals(code)) { toRemove = i; break; }
            }
            if (toRemove != null) rollbackManager.recordStorageAction(toRemove);
            storageManager.removeItem(code);
            refreshStorageTable();
        } else {
            JOptionPane.showMessageDialog(this, "Select an item to remove.");
        }
    }

    //COURIER ACTIONS
    private void addTruckDialog() {
        JTextField codeField = new JTextField();
        JTextField driverField = new JTextField();
        JTextField plateField = new JTextField();

        Object[] message = {"Code:", codeField, "Driver:", driverField, "Plate:", plateField};
        int option = JOptionPane.showConfirmDialog(this, message, "Accept Truck", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String code = codeField.getText().trim();
            String driver = driverField.getText().trim();
            String plate = plateField.getText().trim();
            courierManager.acceptTruck(new Truck(code, driver, plate));
            refreshCourierTable();
        }
    }

    private void removeSelectedTruck() {
        int row = courierTable.getSelectedRow();
        if (row >= 0) {
            String code = (String) courierTable.getValueAt(row, 0);
            String plate = (String) courierTable.getValueAt(row, 2);
            courierManager.declineTruck(code, plate);
            refreshCourierTable();
        } else {
            JOptionPane.showMessageDialog(this, "Select a truck to remove.");
        }
    }

    //DELIVERY ACTIONS
    private void deliverDialog() {
        List<Truck> trucks = courierManager.getAllTrucks();
        if (trucks.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No trucks available for delivery.");
            return;
        }

        String[] truckOptions = trucks.stream()
                .map(t -> t.getPlate() + " (" + t.getDriver() + ")")
                .toArray(String[]::new);

        String truckChoice = (String) JOptionPane.showInputDialog(
                this,
                "Select a truck for delivery:",
                "Deliver Items",
                JOptionPane.PLAIN_MESSAGE,
                null,
                truckOptions,
                truckOptions[0]
        );

        if (truckChoice == null) return;

        Truck selectedTruck = trucks.get(java.util.Arrays.asList(truckOptions).indexOf(truckChoice));

        List<Item> items = storageManager.getAllItems();
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No items in storage to deliver.");
            return;
        }

        String[] itemOptions = items.stream()
                .map(i -> i.getCode() + " - " + i.getName() + " (" + i.getQuantity() + ")")
                .toArray(String[]::new);

        JList<String> itemList = new JList<>(itemOptions);
        itemList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        int option = JOptionPane.showConfirmDialog(
                this,
                new JScrollPane(itemList),
                "Select items to load into truck",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (option != JOptionPane.OK_OPTION) return;

        for (int index : itemList.getSelectedIndices()) {
            Item i = items.get(index);
            int qtyToLoad = 0;
            boolean valid = false;

            while (!valid) {
                String qtyStr = JOptionPane.showInputDialog(
                        this,
                        "Enter quantity to load for " + i.getName() + " (Available: " + i.getQuantity() + "):",
                        i.getQuantity()
                );

                if (qtyStr == null) break;

                try {
                    qtyToLoad = Integer.parseInt(qtyStr);
                    if (qtyToLoad <= 0) {
                        JOptionPane.showMessageDialog(this, "Quantity must be greater than 0.");
                    } else if (qtyToLoad > i.getQuantity()) {
                        JOptionPane.showMessageDialog(this, "Cannot exceed available quantity (" + i.getQuantity() + ").");
                    } else {
                        valid = true;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter a number.");
                }
            }

            if (valid) {
                selectedTruck.addItem(new Item(i.getCode(), i.getName(), qtyToLoad));
                int remaining = i.getQuantity() - qtyToLoad;
                if (remaining > 0) i.setQuantity(remaining);
                else storageManager.removeItem(i.getCode());
            }
        }

        refreshStorageTable();
        courierManager.declineTruck(selectedTruck.getCode(), selectedTruck.getPlate());
        refreshCourierTable();

        StringBuilder invoice = new StringBuilder("Delivery Invoice:\n");
        invoice.append("Truck: ").append(selectedTruck.getPlate())
                .append(" (").append(selectedTruck.getDriver()).append(")\nItems:\n");

        for (Item i : selectedTruck.getItems()) {
            invoice.append(i.getCode()).append(" - ").append(i.getName())
                    .append(" x").append(i.getQuantity()).append("\n");
            deliveryModel.addRow(new Object[]{
            	    i.getCode(),
            	    i.getName(),
            	    selectedTruck.getCode(),
            	    selectedTruck.getDriver(),
            	    selectedTruck.getPlate(),
            	    i.getQuantity()
            	});
        }


        JOptionPane.showMessageDialog(this, invoice.toString(), "Invoice", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI().setVisible(true));
    }
}
