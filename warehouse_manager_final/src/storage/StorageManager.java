package storage;

import models.Item;
import persistence.StoragePersistence;

import java.util.List;

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
            if (i.getCode().equals(item.getCode()) && i.getName().equalsIgnoreCase(item.getName())) {
                i.setQuantity(i.getQuantity() + item.getQuantity());
                found = true;
                break;
            }
        }
        if (!found) {
            items.add(item);
        }
        persistence.saveItems(items);
    }

    @Override
    public void removeItem(String code) {
        items.removeIf(i -> i.getCode().equals(code));
        persistence.saveItems(items);
    }
    
    @Override
    public void removeQuantity(String code, int qtyToRemove) {
        for (Item i : items) {
            if (i.getCode().equals(code)) {
                int remaining = i.getQuantity() - qtyToRemove;
                if (remaining > 0) {
                    i.setQuantity(remaining); 
                } else {
                    items.remove(i); 
                }
                break;
            }
        }
        persistence.saveItems(items);
    }
    
    public void reduceItemQuantity(String code, int qty) {
        for (Item i : items) {
            if (i.getCode().equals(code)) {
                int remaining = i.getQuantity() - qty;
                if (remaining > 0) {
                    i.setQuantity(remaining);
                } else {
                    items.remove(i); 
                }
                break;
            }
        }
        persistence.saveItems(items);
    }


    @Override
    public List<Item> getAllItems() {
        return items;
    }
}
