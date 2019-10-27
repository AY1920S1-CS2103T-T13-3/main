package seedu.address.cashier.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.ItemBuilder.DEFAULT_CATEGORY;
import static seedu.address.testutil.ItemBuilder.DEFAULT_COST;
import static seedu.address.testutil.ItemBuilder.DEFAULT_DESCRIPTION;
import static seedu.address.testutil.ItemBuilder.DEFAULT_PRICE;
import static seedu.address.testutil.ItemBuilder.DEFAULT_QUANTITY;
import static seedu.address.util.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.util.CliSyntax.PREFIX_COST;
import static seedu.address.util.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.util.CliSyntax.PREFIX_INDEX;
import static seedu.address.util.CliSyntax.PREFIX_PRICE;
import static seedu.address.util.CliSyntax.PREFIX_QUANTITY;

import java.util.ArrayList;

import seedu.address.cashier.logic.commands.Command;
import seedu.address.cashier.logic.commands.CommandResult;
import seedu.address.cashier.model.Model;
import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.model.Item;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String VALID_DESCRIPTION_FISH_BURGER = "Burger";
    public static final String VALID_DESCRIPTION_STORYBOOK = "The tale";
    public static final String VALID_DESCRIPTION_BLACK_SHIRT = "CCA shirt";
    public static final String VALID_CATEGORY_1 = "food";
    public static final String VALID_CATEGORY_2 = "Book";
    public static final int VALID_QUANTITY_1 = 12;
    public static final int VALID_QUANTITY_2 = 4;
    public static final int VALID_INDEX_1 = 1;
    public static final int VALID_INDEX_2 = 2;
    public static final double VALID_TOTAL_AMOUNT = 843.23;
    public static final double VALID_CHANGE = 94.23;
    public static final double VALID_PRICE_PAID = 905.23;


    public static final String DESC_DESCRIPTION_FISH_BURGER = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_FISH_BURGER;
    public static final String DESC_DESCRIPTION_STORYBOOK = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_STORYBOOK;
    public static final String DESC_DESCRIPTION_BLACK_SHIRT = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_BLACK_SHIRT;
    public static final String DESC_CATEGORY_1 = " " + PREFIX_CATEGORY + VALID_CATEGORY_1;
    public static final String DESC_CATEGORY_2 = " " + PREFIX_CATEGORY + VALID_CATEGORY_2;
    public static final String DESC_QUANTITY_1 = " " + PREFIX_QUANTITY + VALID_QUANTITY_1;
    public static final String DESC_QUANTITY_2 = " " + PREFIX_QUANTITY + VALID_QUANTITY_2;
    public static final String DESC_INDEX_1 = " " + PREFIX_INDEX + VALID_INDEX_1;
    public static final String DESC_INDEX_2 = " " + PREFIX_INDEX + VALID_INDEX_2;
    public static final String DESC_TOTAL_AMOUNT = " " + PREFIX_COST + VALID_TOTAL_AMOUNT;
    public static final String DESC_CHANGE = " " + PREFIX_COST + VALID_CHANGE;
    public static final String DESC_PRICE_PAID = " " + VALID_PRICE_PAID;

    public static final String DESC_BUILDER_QUANTITY = " " + PREFIX_QUANTITY + DEFAULT_QUANTITY;
    public static final String DESC_BUILDER_COST = " " + PREFIX_COST + DEFAULT_COST;
    public static final String DESC_BUILDER_PRICE = " " + PREFIX_PRICE + DEFAULT_PRICE;
    public static final String DESC_BUILDER_DESC = " " + PREFIX_DESCRIPTION + DEFAULT_DESCRIPTION;
    public static final String DESC_BUILDER_CATEGORY = " " + PREFIX_CATEGORY + DEFAULT_CATEGORY;

    public static final String INVALID_DESCRIPTION_1 = " " + PREFIX_DESCRIPTION + "black shirt";
    public static final String INVALID_DESCRIPTION_2 = " " + PREFIX_DESCRIPTION + "black case";
    public static final String INVALID_CATEGORY_1 = " " + PREFIX_CATEGORY + "accessory";
    public static final String INVALID_CATEGORY_2 = " " + PREFIX_CATEGORY + "paper";
    public static final String INVALID_QUANTITY_1 = " " + PREFIX_QUANTITY + "hi";
    public static final String INVALID_QUANTITY_2 = " " + PREFIX_QUANTITY + "-4";
    public static final String INVALID_INDEX_1 = " " + PREFIX_INDEX + "-5";
    public static final String INVALID_INDEX_2 = " " + PREFIX_INDEX + "900";
    public static final String INVALID_INDEX_3 = " " + PREFIX_INDEX + "hey";
    public static final String INVALID_INDEX_4 = " " + PREFIX_INDEX + 0;
    public static final String INVALID_PRICE_PAID_1 = " " + "-400";
    public static final String INVALID_PRICE_PAID_2 = " " + "3";

    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link seedu.address.cashier.logic.commands.CommandResult}
     * matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model cashierModel,
                                            CommandResult expectedCommandResult,
                                            Model expectedModel, seedu.address.person.model.Model personModel) {
        try {
            System.out.println("beforee");
            CommandResult result = command.execute(cashierModel, personModel);
            System.out.println("inside test util:" + expectedCommandResult.getFeedbackToUser());
            System.out.println(result.getFeedbackToUser());
            assertEquals(expectedCommandResult, result);
            System.out.println("did first assert equals");
            assertEquals(expectedModel, cashierModel);
            assertEquals(expectedModel.getSalesList(), cashierModel.getSalesList());
        } catch (Exception ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to
     * {@link #assertCommandSuccess(Command, Model, CommandResult, Model, seedu.address.person.model.Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model cashierModel,
                                            String expectedMessage,
                                            Model expectedModel, seedu.address.person.model.Model personModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, cashierModel, expectedCommandResult, expectedModel,
                personModel);
    }


    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage,
                                            seedu.address.person.model.Model personModel) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ArrayList<Item> salesList = actualModel.getSalesList();
        InventoryList inventoryList = actualModel.getInventoryList();

        assertThrows(Exception.class, expectedMessage, () -> command.execute(actualModel, personModel));
        assertEquals(salesList, actualModel.getSalesList());
        assertEquals(inventoryList, actualModel.getInventoryList());
    }

}
