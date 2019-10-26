package seedu.address.cashier.logic;

import static seedu.address.cashier.commands.CommandTestUtil.DESC_BUILDER_QUANTITY;
import static seedu.address.cashier.commands.CommandTestUtil.DESC_CATEGORY_1;
import static seedu.address.cashier.commands.CommandTestUtil.DESC_DESCRIPTION_FISH_BURGER;
import static seedu.address.cashier.commands.CommandTestUtil.DESC_DESCRIPTION_STORYBOOK;
import static seedu.address.cashier.commands.CommandTestUtil.DESC_QUANTITY_1;
import static seedu.address.cashier.commands.CommandTestUtil.DESC_QUANTITY_2;
import static seedu.address.cashier.commands.CommandTestUtil.INVALID_DESCRIPTION_1;
import static seedu.address.cashier.commands.CommandTestUtil.INVALID_DESCRIPTION_2;
import static seedu.address.cashier.commands.CommandTestUtil.INVALID_QUANTITY_1;
import static seedu.address.cashier.commands.CommandTestUtil.INVALID_QUANTITY_2;
import static seedu.address.cashier.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.cashier.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.cashier.commands.CommandTestUtil.VALID_CATEGORY_1;
import static seedu.address.cashier.commands.CommandTestUtil.VALID_DESCRIPTION_FISH_BURGER;
import static seedu.address.cashier.commands.CommandTestUtil.VALID_DESCRIPTION_STORYBOOK;
import static seedu.address.cashier.commands.CommandTestUtil.VALID_QUANTITY_1;
import static seedu.address.cashier.commands.CommandTestUtil.VALID_QUANTITY_2;
import static seedu.address.cashier.logic.CommandParserTestUtil.assertCommandParserFailure;
import static seedu.address.cashier.logic.CommandParserTestUtil.assertCommandParserSuccess;
import static seedu.address.cashier.ui.CashierMessages.NO_SUCH_ITEM_FOR_SALE_CASHIER;
import static seedu.address.cashier.ui.CashierMessages.QUANTITY_NOT_A_NUMBER;
import static seedu.address.cashier.ui.CashierMessages.QUANTITY_NOT_POSITIVE;
import static seedu.address.cashier.ui.CashierMessages.itemsByCategory;
import static seedu.address.cashier.ui.CashierMessages.noSuchItemRecommendation;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.logic.commands.AddCommand;
import seedu.address.cashier.logic.parser.AddCommandParser;
import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalTransactions;

public class AddCommandParserTest {

    private AddCommandParser parser = new AddCommandParser();
    private ModelManager model = new ModelManager(TypicalItem.getTypicalInventoryList(),
            TypicalTransactions.getTypicalTransactionList());
    private seedu.address.person.model.Model personModel =
            new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parse_allFieldsPresent_success() throws NoSuchIndexException {

        // whitespace only preamble
        assertCommandParserSuccess(parser, PREAMBLE_WHITESPACE + DESC_DESCRIPTION_FISH_BURGER
                        + DESC_QUANTITY_1,
                new AddCommand(VALID_DESCRIPTION_FISH_BURGER, VALID_QUANTITY_1), model, personModel);

        //no whitespace preamble
        assertCommandParserSuccess(parser, DESC_DESCRIPTION_STORYBOOK + DESC_QUANTITY_2,
                new AddCommand(VALID_DESCRIPTION_STORYBOOK, VALID_QUANTITY_2), model, personModel);

        // multiple description - last description accepted
        assertCommandParserSuccess(parser, DESC_DESCRIPTION_FISH_BURGER + DESC_DESCRIPTION_STORYBOOK
                        + DESC_QUANTITY_1,
                new AddCommand(VALID_DESCRIPTION_STORYBOOK, VALID_QUANTITY_1), model, personModel);

        // multiple quantity - last quantity accepted
        assertCommandParserSuccess(parser, DESC_DESCRIPTION_FISH_BURGER + DESC_QUANTITY_1
                        + DESC_QUANTITY_2,
                new AddCommand(VALID_DESCRIPTION_FISH_BURGER, VALID_QUANTITY_2), model, personModel);

        // optional category included
        assertCommandParserSuccess(parser, DESC_CATEGORY_1 + DESC_DESCRIPTION_FISH_BURGER + DESC_QUANTITY_1
                        + DESC_QUANTITY_2,
                new AddCommand(VALID_DESCRIPTION_FISH_BURGER, VALID_QUANTITY_2), model, personModel);

    }

    @Test
    public void parse_itemNotInInventory_failure() throws NoSuchIndexException {
        // with item not found in inventory
        assertCommandParserFailure(parser, INVALID_DESCRIPTION_1
                        + DESC_BUILDER_QUANTITY,
                noSuchItemRecommendation(model.getRecommendedItems(INVALID_DESCRIPTION_1)), model, personModel);
    }

    @Test
    public void parse_itemNotForSale_failure() {
        // with description of item that is not available for sale
        assertCommandParserFailure(parser, INVALID_DESCRIPTION_2 + DESC_BUILDER_QUANTITY,
                NO_SUCH_ITEM_FOR_SALE_CASHIER, model, personModel);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() throws NoSuchIndexException {

        String expectedMessage = CashierMessages.MESSAGE_INVALID_ADDCOMMAND_FORMAT;

        // missing description prefix
        assertCommandParserFailure(parser, VALID_DESCRIPTION_FISH_BURGER + DESC_QUANTITY_1,
                expectedMessage, model, personModel);

        // missing quantity prefix
        assertCommandParserFailure(parser, DESC_DESCRIPTION_FISH_BURGER + VALID_QUANTITY_1,
                expectedMessage, model, personModel);

        // all prefixes missing
        assertCommandParserFailure(parser, VALID_DESCRIPTION_FISH_BURGER + VALID_QUANTITY_1,
                expectedMessage, model, personModel);

        //with optional category field, but missing description
        ArrayList<String> listItems = model.getDescriptionByCategory(VALID_CATEGORY_1);
        assertCommandParserFailure(parser, DESC_CATEGORY_1,
                itemsByCategory(listItems), model, personModel);

    }

    @Test
    public void parse_invalidValue_failure() throws NoSuchIndexException {

        // invalid description
        ArrayList<String> recommendedItems = model.getRecommendedItems(INVALID_DESCRIPTION_1);
        assertCommandParserFailure(parser, INVALID_DESCRIPTION_1 + DESC_QUANTITY_1,
                noSuchItemRecommendation(recommendedItems), model, personModel);

        // invalid non-integer quantity
        assertCommandParserFailure(parser, DESC_DESCRIPTION_FISH_BURGER + INVALID_QUANTITY_1,
                QUANTITY_NOT_A_NUMBER, model, personModel);

        // invalid negative quantity
        assertCommandParserFailure(parser, DESC_DESCRIPTION_FISH_BURGER + INVALID_QUANTITY_2,
                QUANTITY_NOT_POSITIVE, model, personModel);

        // two invalid values, only first invalid value reported
        assertCommandParserFailure(parser, INVALID_DESCRIPTION_1 + INVALID_QUANTITY_1,
                QUANTITY_NOT_A_NUMBER, model, personModel);

        // non-empty preamble
        assertCommandParserFailure(parser, PREAMBLE_NON_EMPTY + DESC_DESCRIPTION_STORYBOOK + DESC_QUANTITY_1,
                CashierMessages.MESSAGE_INVALID_ADDCOMMAND_FORMAT, model, personModel);

    }

}
