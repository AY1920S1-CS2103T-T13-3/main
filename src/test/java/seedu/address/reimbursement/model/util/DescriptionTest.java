package seedu.address.reimbursement.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TransactionBuilder;
import seedu.address.testutil.TypicalPersons;
import seedu.address.transaction.model.Transaction;

public class DescriptionTest {
    public static final String CORRECT_STRING = "1. poster printing" + System.lineSeparator() + "2. poster printing";

    @Test
    public void valid_default_constructor() {
        assertEquals("", new Description().toString());
    }

    @Test
    public void valid_constructor_from_transaction() {
        Transaction transAlice = new TransactionBuilder(TypicalPersons.ALICE).build();
        Transaction transBob = new TransactionBuilder(TypicalPersons.BOB).build();
        ArrayList<Transaction> transList = new ArrayList<>();
        transList.add(transAlice);
        transList.add(transBob);
        Description testedDescription = new Description(transList);
        String testedString = testedDescription.toString();
        System.out.println(testedString);
        assertEquals(CORRECT_STRING, testedString);
    }

}
