package seedu.address.cashier.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.cashier.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.cashier.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalTransactions;

public class DeleteCommandTest {

    private ModelManager model = new ModelManager(TypicalItem.getTypicalInventoryList(),
            TypicalTransactions.getTypicalTransactionList());

    private seedu.address.person.model.Model personModel =
            new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_successful() throws NoSuchIndexException {
        DeleteCommand deleteCommand = new DeleteCommand(1);
        String message = String.format(CashierMessages.MESSAGE_DELETED_ITEM, TypicalItem.FISH_BURGER.getDescription());

        ModelManager expectedModel = new ModelManager(TypicalItem.getTypicalInventoryList(),
                TypicalTransactions.getTypicalTransactionList());
        expectedModel.addItem(TypicalItem.FISH_BURGER);
        expectedModel.deleteItem(1);

        model.addItem(TypicalItem.FISH_BURGER);

        assertCommandSuccess(deleteCommand, model, message, expectedModel, personModel);
    }

    @Test
    public void execute_outOfBoundIndex_failure() {
        DeleteCommand deleteCommand = new DeleteCommand(30);
        String message = CashierMessages.NO_SUCH_INDEX_CASHIER;
        assertCommandFailure(deleteCommand, model, message, personModel);
    }

    @Test
    public void constructor_negativeIndex_throwsAssertionException() {
        assertThrows(AssertionError.class, () -> new DeleteCommand(-5));
    }

}
