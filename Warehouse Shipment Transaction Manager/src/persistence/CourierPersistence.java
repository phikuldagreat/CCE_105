package persistence;

import models.Truck;
import models.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles reading/writing courier trucks and their items to file.
 */
public class CourierPersistence {
    private static final String FILE_NAME = "trucks.txt";

    public void saveTrucks(List<Truck> trucks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Truck truck : trucks) {
                // Save truck info: code,driver,plate
                writer.write(truck.getCode() + "," + truck.getDriver() + "," + truck.getPlate());
                
                // Save truck items (optional, pipe-separated: code-name-qty|code-name-qty...)
                List<Item> items = truck.getItems();
                if (!items.isEmpty()) {
                    writer.write(",");
                    for (int i = 0; i < items.size(); i++) {
                        Item item = items.get(i);
                        writer.write(item.getCode() + "-" + item.getName() + "-" + item.getQuantity());
                        if (i < items.size() - 1) writer.write("|");
                    }
                }

                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Truck> loadTrucks() {
        List<Truck> trucks = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return trucks;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 4); // split truck info + items
                if (parts.length >= 3) {
                    String code = parts[0];
                    String driver = parts[1];
                    String plate = parts[2];
                    Truck truck = new Truck(code, driver, plate);

                    // Load items if available
                    if (parts.length == 4) {
                        String[] itemParts = parts[3].split("\\|");
                        for (String itemStr : itemParts) {
                            String[] itemInfo = itemStr.split("-");
                            if (itemInfo.length == 3) {
                                String itemCode = itemInfo[0];
                                String itemName = itemInfo[1];
                                int qty = Integer.parseInt(itemInfo[2]);
                                truck.addItem(new Item(itemCode, itemName, qty));
                            }
                        }
                    }

                    trucks.add(truck);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trucks;
    }
}
