package Storage;

import models.Item;
import java.util.List;

public interface IStorageManager {
    void addItem(Item item);
    void removeItem(String code);
    List<Item> getAllItems();
}
