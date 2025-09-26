package persistence;

import models.Truck;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles reading/writing courier trucks to file.
 */
public class CourierPersistence {
    private static final String FILE_NAME = "trucks.txt";

    public void saveTrucks(List<Truck> trucks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Truck truck : trucks) {
                // Save in order: code,name,quantity,driver,plate
                writer.write(truck.getCode() + "," +
                             truck.getName() + "," +
                             truck.getQuantity() + "," +
                             truck.getDriver() + "," +
                             truck.getPlate());
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
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String code = parts[0];
                    String name = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    String driver = parts[3];
                    String plate = parts[4];
                    trucks.add(new Truck(code, name, quantity, driver, plate));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trucks;
    }
}
