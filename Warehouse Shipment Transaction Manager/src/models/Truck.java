package models;

/**
 * Represents a truck delivering or picking up items.
 */
public class Truck {
    private String code;   // item code
    private String name;   // item name
    private int quantity;  // number of items in the truck
    private String driver;
    private String plate;

    // Corrected constructor order: code, name, quantity, driver, plate
    public Truck(String code, String driver, String plate) {
        this.code = code;
        this.driver = driver;
        this.plate = plate;
    }

    // Getters
    public String getCode() { return code; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public String getDriver() { return driver; }
    public String getPlate() { return plate; }

    // Setter
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
