package Storage;

import models.Item;
import java.util.List;

/**
 * Interface for managing storage operations (items).
 */
public interface IStorageManager {
    void addItem(Item item);
    void removeItem(String code);
    void removeQuantity(String code, int qty);
    void reduceItemQuantity(String code, int qty);
    List<Item> getAllItems();
}
