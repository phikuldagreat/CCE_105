package persistence;

import java.io.*;

//Handles saving and loading rollback states.
public class RollbackPersistence {
    private static final String FILE_NAME = "rollback.txt";

    public void saveRollbackState(String state) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String loadRollbackState() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return "";
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
