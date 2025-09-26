package Storage;

import models.Item;
import persistence.StoragePersistence;

import java.util.List;

/**
 * Handles adding, removing, and listing items in storage.
 */
public class StorageManager implements IStorageManager {
    private List<Item> items;
    private StoragePersistence persistence = new StoragePersistence();

    public StorageManager() {
        items = persistence.loadItems();
    }

    @Override
    public void addItem(Item item) {
        boolean found = false;
        for (Item i : items) {
            if (i.getName().equalsIgnoreCase(item.getName())) {
                i.setQuantity(i.getQuantity() + item.getQuantity());
                found = true;
                break;
            }
        }
        if (!found) items.add(item);
        persistence.saveItems(items);
    }

    @Override
    public void removeItem(String code) {
        items.removeIf(i -> i.getCode().equals(code));
        persistence.saveItems(items);
    }

    @Override
    public List<Item> getAllItems() {
        return items;
    }
}
