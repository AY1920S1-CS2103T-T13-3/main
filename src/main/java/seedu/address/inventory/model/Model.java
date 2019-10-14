package seedu.address.inventory.model;

import seedu.address.inventory.model.exception.NoSuchIndexException;

/**
 * The API of the Model component.
 */
public interface Model {

    void addItem(Item item);

    Item findItemByIndex(int index) throws NoSuchIndexException;

    void deleteItem(int index);

    void writeInInventoryFile() throws Exception;

    //InventoryList getInventoryList();

    void setItem(int i, Item editedItem) throws Exception;

    boolean hasItemInInventory(Item item);

    //boolean hasSufficientQuantity(String description, int quantity) throws NoSuchItemException;

    void readInUpdatedList();

}
