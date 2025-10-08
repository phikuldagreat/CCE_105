package models;

//Represents an item stored in the warehouse.
public class Item {
    private String code;
    private String name;
    private String status; //incoming, outgoing, in-storage
    private int quantity;

    public Item(String code, String name, int quantity) {
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }

    //Getters and setters
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getStatus() { return status; }
    public int getQuantity() { return quantity; }

    public void setStatus(String status) { this.status = status; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
