package seedu.address.cashier.logic;

import static seedu.address.inventory.model.Item.DECIMAL_FORMAT;

import java.util.ArrayList;
import java.util.logging.Logger;

import seedu.address.cashier.logic.commands.CheckoutCommand;
import seedu.address.cashier.logic.commands.Command;
import seedu.address.cashier.logic.commands.CommandResult;
import seedu.address.cashier.logic.commands.exception.NoCashierFoundException;
import seedu.address.cashier.logic.parser.CashierTabParser;
import seedu.address.cashier.model.Model;
import seedu.address.cashier.storage.Storage;
import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.model.Item;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.transaction.model.transaction.Transaction;

/**
 * The main LogicManager of the cashier tab.
 */
public class LogicManager implements Logic {

    private final Model model;
    private final Storage storage;
    private CashierTabParser parser;
    private final seedu.address.person.model.Model personModel;
    private final seedu.address.transaction.model.Model transactionModel;
    private final seedu.address.inventory.model.Model inventoryModel;
    private final Logger logger = LogsCenter.getLogger(getClass());

    public LogicManager(Model cashierManager,
                        Storage cashierStorage,
                        seedu.address.person.model.Model personModel,
                        seedu.address.transaction.model.Model transactionModel,
                        seedu.address.inventory.model.Model inventoryModel) {

        this.model = cashierManager;
        this.personModel = personModel;
        this.transactionModel = transactionModel;
        this.inventoryModel = inventoryModel;

        this.storage = cashierStorage;
        parser = new CashierTabParser();
    }

    @Override
    public CommandResult execute(String commandText) throws Exception {
        readInUpdatedList();
        Command command = parser.parseCommand(commandText, model, personModel);
        CommandResult commandResult = command.execute(model, personModel);
        if (command instanceof CheckoutCommand) {
            writeInInventoryFile();
            Transaction transaction = model.getCheckoutTransaction();
            logger.info("Description of transaction added is: " + transaction.getDescription());
            storage.appendToTransaction(transaction);
            transactionModel.addTransaction(transaction);
            inventoryModel.readInUpdatedList();
        }
        return commandResult;
    }

    /**
     * Updates the inventory and transaction list from the data file.
     */
    @Override
    public void readInUpdatedList() throws Exception {
        model.getUpdatedLists(storage.getInventoryList(), storage.getTransactionList());
    }

    @Override
    public void writeInInventoryFile() throws Exception {
        storage.writeToInventoryFile(model.getInventoryList());
    }

    public InventoryList getInventoryList() {
        return model.getInventoryList();
    }

    public ArrayList<Item> getSalesList() {
        return model.getSalesList();
    }

    @Override
    public String getAmount() {
        return DECIMAL_FORMAT.format(model.getTotalAmount());
    }

    @Override
    public String getCashier() throws NoCashierFoundException {
        try {
            model.getCashier();
        } catch (NoCashierFoundException e) {
            logger.info("No cashier set.");
            return "";
        }
        return String.valueOf(model.getCashier().getName());
    }


}



