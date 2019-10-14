package seedu.address.inventory.model;

import seedu.address.inventory.model.exception.NoSuchIndexException;
import seedu.address.inventory.model.exception.NoSuchItemException;
import seedu.address.inventory.storage.StorageManager;
import seedu.address.inventory.util.InventoryList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private InventoryList inventoryList;
    private StorageManager storage;

    public ModelManager(InventoryList inventoryList) {
        this.inventoryList = inventoryList;
    }

    public ModelManager(StorageManager storage) {
        this.storage = storage;
        try {
            this.inventoryList = storage.getInventoryList();
        } catch (Exception e) {
            this.inventoryList = new InventoryList();
        }
    }

    //@Override
    public InventoryList getInventoryList() {
        return this.inventoryList;
    }

    @Override
    public void setItem(int i, Item editedItem) throws Exception {
        inventoryList.set(i - 1, editedItem);
    }

    @Override
    public boolean hasItemInInventory(Item item) {
        for (int i = 0; i < inventoryList.size(); i++) {
            try {
                if (inventoryList.getItemByIndex(i).isSameItem(item)) {
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public void addItem(Item item) {
        inventoryList.add(item);
    }

    @Override
    public Item findItemByIndex(int index) throws NoSuchIndexException {
        Item item = inventoryList.getItemByIndex(index - 1);
        return item;
    }

    @Override
    public void deleteItem(int index) {
        inventoryList.delete(index - 1);
    }

    @Override
    public void writeInInventoryFile() throws Exception {
        storage.writeFile(inventoryList);
    }

    //@Override
    public boolean hasSufficientQuantity(String description, int quantity) throws NoSuchItemException {
        if (inventoryList.getOriginalItem(description).getQuantity() > quantity) {
            return false;
        } else {
            return true;
        }
    }

    public void updateIndexes() throws NoSuchIndexException {
        for (int i = 0; i < inventoryList.size(); i++) {
            Item item = inventoryList.get(i);
            item.setId(i + 1);
        }
    }

    public void sortByDescription() {
        inventoryList.sortByDescription();
    }

    public void sortByCategory() {
        inventoryList.sortByCategory();
    }

    public void sortByQuantity() {
        inventoryList.sortByQuantity();
    }

    @Override
    public void readInUpdatedList() {
        try {
            this.inventoryList = storage.getInventoryList();
        } catch (Exception e) {
            this.inventoryList = new InventoryList();
        }
    }
}
