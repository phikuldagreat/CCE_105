package models;

import java.util.ArrayList;
import java.util.List;

public class Truck {
    private String code;   //truck code
    private String driver;
    private String plate;

    //Truck carries multiple items
    private List<Item> items = new ArrayList<>();

    public Truck(String code, String driver, String plate) {
        this.code = code;
        this.driver = driver;
        this.plate = plate;
    }

    //Getters
    public String getCode() { return code; }
    public String getDriver() { return driver; }
    public String getPlate() { return plate; }

    //Items management
    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }
}
