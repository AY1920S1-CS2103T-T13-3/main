package seedu.address.inventory.model;

import java.util.ArrayList;

import seedu.address.inventory.model.exception.NoSuchIndexException;
import seedu.address.inventory.util.InventoryList;

/**
 * The API of the Model component.
 */
public interface Model {

    void addItem(Item item);

    Item findItemByIndex(int index) throws NoSuchIndexException;

    void deleteItem(int index);

    void writeInInventoryFile() throws Exception;

    void setItem(int i, Item editedItem) throws Exception;

    boolean hasItemInInventory(Item item);

    void readInUpdatedList();

    ArrayList<Item> getInventoryListInArrayList();

    InventoryList getInventoryList();

    void resetAndWriteIntoInventoryFile(InventoryList inventoryList) throws Exception;

}
