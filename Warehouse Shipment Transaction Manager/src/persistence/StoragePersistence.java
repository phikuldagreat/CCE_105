package persistence;

import models.Item;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles reading/writing storage items to file.
 */
public class StoragePersistence {
    private static final String FILE_NAME = "items.txt";

    public void saveItems(List<Item> items) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Item item : items) {
                writer.write(item.getCode() + "," + item.getName() + "," + item.getStatus() + "," + item.getQuantity());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Item> loadItems() {
        List<Item> items = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return items;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    items.add(new Item(parts[0], parts[1], parts[2], Integer.parseInt(parts[3])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }
}
