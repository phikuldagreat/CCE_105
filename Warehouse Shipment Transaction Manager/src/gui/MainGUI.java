package gui;

import Storage.StorageManager;
import Courier.CourierManager;
import rollback.RollbackManager;
import models.Item;
import models.Truck;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainGUI extends JFrame {

    //Panels
    private JPanel menuPanel;
    private JPanel contentPanel;
    private JPanel buttonPanel;

    //Tables
    private JTable storageTable;
    private JTable courierTable;

    //Layout
    private CardLayout cardLayout;

    //Backend Managers
    private StorageManager storageManager;
    private CourierManager courierManager;

    //Track active card
    private String activeCard = "Storage"; // default active card

    public MainGUI() {
        // === Window setup ===
        setTitle("Shipment Transaction Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // === Initialize backend managers ===
        storageManager = new StorageManager();
        courierManager = new CourierManager();

        // === LEFT MENU PANEL ===
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0, 1, 10, 10));

        JButton storageButton = new JButton("Item Storage");
        
        JButton courierButton = new JButton("Driver List");
        
        JButton deliverButton = new JButton("Deliver");

        menuPanel.add(storageButton);
        menuPanel.add(courierButton);
        menuPanel.add(deliverButton);
        
        add(menuPanel, BorderLayout.WEST);

        //CENTER CONTENT
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // STORAGE MANAGER TABLE
        String[] storageCols = {"Code", "Name", "Quantity"};
        storageTable = new JTable(new DefaultTableModel(storageCols, 0));
        JScrollPane storageScroll = new JScrollPane(storageTable);
        contentPanel.add(storageScroll, "Storage");

        // COURIER MANAGER TABLE
        String[] courierCols = {"Code", "Driver", "Plate"};
        courierTable = new JTable(new DefaultTableModel(courierCols, 0));
        JScrollPane courierScroll = new JScrollPane(courierTable);
        contentPanel.add(courierScroll, "Courier");

        add(contentPanel, BorderLayout.CENTER);


        // === BOTTOM BUTTON PANEL ===
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // === MENU SWITCHING ===
        storageButton.addActionListener(e -> {
            cardLayout.show(contentPanel, "Storage");
            activeCard = "Storage";
            setButtonLabels("Add Item", "Remove Item");
            refreshStorageTable();
        });

        courierButton.addActionListener(e -> {
            cardLayout.show(contentPanel, "Courier");
            activeCard = "Courier";
            setButtonLabels("Add Truck", "Remove Truck");
            refreshCourierTable();
        });

        // === BUTTON ACTIONS ===
        addButton.addActionListener(e -> {
            if (isStorageActive()) addItemDialog();
            else addTruckDialog();
        });

        removeButton.addActionListener(e -> {
            if (isStorageActive()) removeSelectedItem();
            else removeSelectedTruck();
        });

        // Initial table load
        refreshStorageTable();
        refreshCourierTable();
    }

    // === HELPER METHODS ===

    private boolean isStorageActive() {
        return activeCard.equals("Storage");
    }

    private void setButtonLabels(String add, String remove) {
        ((JButton) buttonPanel.getComponent(0)).setText(add);
        ((JButton) buttonPanel.getComponent(1)).setText(remove);
    }

    private void refreshStorageTable() {
        DefaultTableModel model = (DefaultTableModel) storageTable.getModel();
        model.setRowCount(0); // clear
        List<Item> items = storageManager.getAllItems();
        for (Item i : items) {
            model.addRow(new Object[]{i.getCode(), i.getName(), i.getQuantity()});
        }
    }

    private void refreshCourierTable() {
        DefaultTableModel model = (DefaultTableModel) courierTable.getModel();
        model.setRowCount(0);
        List<Truck> trucks = courierManager.getAllTrucks();
        for (Truck t : trucks) {
            model.addRow(new Object[]{t.getCode(), t.getDriver(), t.getPlate()});
        }
    }

    // === STORAGE ACTIONS ===
    private void addItemDialog() {
        JTextField codeField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField qtyField = new JTextField();

        Object[] message = {
                "Code:", codeField,
                "Name:", nameField,
                "Quantity:", qtyField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Item", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String code = codeField.getText();
            String name = nameField.getText();
            int qty = Integer.parseInt(qtyField.getText());
            storageManager.addItem(new Item(code, name, qty));
            refreshStorageTable();
        }
    }

    private void removeSelectedItem() {
        int row = storageTable.getSelectedRow();
        if (row >= 0) {
            String code = (String) storageTable.getValueAt(row, 0);
            storageManager.removeItem(code);
            refreshStorageTable();
        }
    }


    // === COURIER ACTIONS ===
    private void addTruckDialog() {
        JTextField codeField = new JTextField();
        JTextField driverField = new JTextField();
        JTextField plateField = new JTextField();

        Object[] message = {
                "Code:", codeField,
                "Driver:", driverField,
                "Plate:", plateField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Accept Truck", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String code = codeField.getText();
            String driver = driverField.getText();
            String plate = plateField.getText();

            courierManager.acceptTruck(new Truck(code, driver, plate));
            refreshCourierTable();
        }
    }

    private void removeSelectedTruck() {
        int row = courierTable.getSelectedRow();
        if (row >= 0) {
            String code = (String) courierTable.getValueAt(row, 0);  // code column
            String plate = (String) courierTable.getValueAt(row, 2); // plate column
            courierManager.declineTruck(code, plate); // update manager method to accept both
            refreshCourierTable();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI().setVisible(true));
    }
}
