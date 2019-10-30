package seedu.address.cashier.storage;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.model.Model;
import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.util.InventoryList;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalTransactions;

public class StorageManagerTest {
    //private File iFile;
    //private File tFile;
    //private StorageManager storageManager;
    //private Model model;
    /*private File iFile;
    private File tFile;
    private File rFile;*/
    private Storage storage;
    /*private seedu.address.person.model.Model personModel;
    private seedu.address.transaction.model.Model transactionModel = null;
    private seedu.address.inventory.model.Model inventoryModel;
    private seedu.address.transaction.logic.Logic transactionLogic;
    private seedu.address.inventory.logic.Logic inventoryLogic;
    private seedu.address.transaction.storage.Storage transactionStorage;
    private seedu.address.inventory.storage.Storage inventoryStorage;*/
    //private seedu.address.reimbursement.logic.Logic reimbursementLogic = null;
    //private seedu.address.reimbursement.storage.Storage reimbursementStorage = null;
    //private Model model;

    public StorageManagerTest() throws Exception {
        try {
            Model model;
            File iFile;
            File tFile;
            File rFile;
            //Storage storage;
            seedu.address.person.model.Model personModel1;
            seedu.address.person.model.GetPersonByNameOnlyModel personModel;
            seedu.address.transaction.model.Model transactionModel = null;
            seedu.address.inventory.model.Model inventoryModel;
            seedu.address.transaction.logic.Logic transactionLogic;
            seedu.address.inventory.logic.Logic inventoryLogic;
            seedu.address.transaction.storage.Storage transactionStorage;
            seedu.address.inventory.storage.Storage inventoryStorage;
            model = new ModelManager(TypicalItem.getTypicalInventoryList(),
                    TypicalTransactions.getTypicalTransactionList());
            personModel = new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());
            personModel1 = new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());
            iFile = File.createTempFile("testing", "tempInventory.txt");
            tFile = File.createTempFile("testing", "tempTransaction.txt");
            rFile = File.createTempFile("testing", "tempReimbursement.txt");

            seedu.address.reimbursement.storage.StorageManager reimbursementManager =
                    new seedu.address.reimbursement.storage.StorageManager(rFile);

            seedu.address.reimbursement.model.Model reimbursementModel =
                    new seedu.address.reimbursement.model.ModelManager(
                                    reimbursementManager.getReimbursementFromFile(model.getTransactionList()));


            transactionStorage =
                    new seedu.address.transaction.storage.StorageManager(tFile, personModel);


            transactionModel = new seedu.address.transaction.model.ModelManager(
                    new seedu.address.transaction.storage.StorageManager(tFile, personModel).readTransactionList());

            //model.getTransactionList();
            transactionLogic = new seedu.address.transaction.logic.LogicManager(transactionModel,
                    transactionStorage, personModel);

            inventoryModel =
                    new seedu.address.inventory.model.ModelManager(
                            new seedu.address.inventory.storage.StorageManager(iFile).getInventoryList());
            inventoryStorage =
                    new seedu.address.inventory.storage.StorageManager(iFile);

            inventoryLogic = new seedu.address.inventory.logic.LogicManager(
                    (seedu.address.inventory.model.ModelManager) inventoryModel,
                    (seedu.address.inventory.storage.StorageManager) inventoryStorage);
            storage = new StorageManager(inventoryLogic, transactionLogic);

        /*Model model;
        File iFile;
        File tFile;
        iFile = File.createTempFile("testing", "tempInventory.txt");
        tFile = File.createTempFile("testing", "tempTransaction.txt");
        iFile.deleteOnExit();
        tFile.deleteOnExit();
        model = new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());
        storageManager = new StorageManager(iFile, tFile, model);*/
        } catch (IOException e) {
            throw new AssertionError("This constructor should not throw an exception.");
        }
    }

    @Test
    public void fileReadSaveForInventory() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly writing and reading to the
         * text file.
         */
        InventoryList original = TypicalItem.getTypicalInventoryList();
        storage.writeToInventoryFile(original);
        InventoryList retrieved = storage.getInventoryList();
        assertEquals(original, retrieved);
    }

    //@Test
    //public void fileReadSaveForTransaction() throws Exception {
    /*
     * Note: This is an integration test that verifies the StorageManager is properly writing and reading to the
     * text file.
     */
    //resetTransactionsForReimbursement();
    /*TransactionList transactionList = TypicalTransactions.getTypicalTransactionList();
    transactionStorage.writeFile(transactionList);
    Transaction original = TypicalTransactions.GEORGE_TRANSACTION_7;
    storage.appendToTransaction(original);
    System.out.println(storage.getTransactionList().size());
    TransactionList retrieved = storage.getTransactionList();
    assertEquals(original, retrieved.get(0));
    }*/
}


