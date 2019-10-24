package seedu.address.cashier.logic.commands;

import static seedu.address.cashier.ui.CashierMessages.MESSAGE_CHECKOUT_SUCCESS;

import java.util.logging.Logger;

import seedu.address.cashier.logic.commands.exception.NoCashierFoundException;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.inventory.model.Item;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.model.person.Person;

/**®
 * Checkout a list of item to be sold.
 */
public class CheckoutCommand extends Command {

    public static final String COMMAND_WORD = "checkout";
    private final double totalAmount;
    private final double change;
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Creates a CheckoutCommand to update the inventory and the transaction.
     * @param totalAmount of the sales list
     * @param change to be returned to the customer
     */
    public CheckoutCommand(double totalAmount, double change) {
        assert totalAmount >= 0 : "Total amount cannot be negative.";
        assert change >= 0 : "Change cannot be negative.";

        logger.info("Total Amount: " + totalAmount);
        logger.info("Change: " + change);

        this.totalAmount = totalAmount;
        this.change = change;
    }

    @Override
    public CommandResult execute(seedu.address.cashier.model.Model modelManager,
                                 seedu.address.person.model.Model personModel)
            throws Exception {
        Person p;
        try {
            p = modelManager.getCashier();
            logger.info("Cashier already set to: " + p.toString());
        } catch (NoCashierFoundException e) {
            throw new NoCashierFoundException(CashierMessages.NO_CASHIER);
        }
        modelManager.checkoutAsTransaction(totalAmount, p);
        modelManager.updateInventoryList();
        ClearCommand clearCommand = new ClearCommand();
        clearCommand.execute(modelManager, personModel);
        return new CommandResult(String.format(MESSAGE_CHECKOUT_SUCCESS, Item.DECIMAL_FORMAT.format(totalAmount),
                Item.DECIMAL_FORMAT.format(change)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CheckoutCommand // instanceof handles nulls
                && totalAmount == (((CheckoutCommand) other).totalAmount)
                && change == ((CheckoutCommand) other).change);
    }
}
