package seedu.address.reimbursement.commands;

import java.util.logging.Logger;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.reimbursement.model.Model;
import seedu.address.reimbursement.ui.ReimbursementMessages;

/**
 * Represents a command to list all reimbursements.
 */
public class ListCommand extends Command {
    public static final String COMMAND_WORD = "back";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) {
        ReimbursementMessages reimbursementMessages = new ReimbursementMessages();
        model.listReimbursement();
        return new CommandResult(reimbursementMessages.LIST_COMMAND);
    }
}
