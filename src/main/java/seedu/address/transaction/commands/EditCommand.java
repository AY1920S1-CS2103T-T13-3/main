package seedu.address.transaction.commands;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import seedu.address.person.commons.util.CollectionUtil;
import seedu.address.person.logic.commands.exceptions.CommandException;
import seedu.address.transaction.commands.exception.NoSuchPersonException;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.ui.MyUi;

public class EditCommand extends Command {
    private int index;
    private EditTransactionDescriptor editTransactionDescriptor;
    public static final String COMMAND_WORD = "edit";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person is already recorded.";

    public EditCommand(int index, EditTransactionDescriptor editTransactionDescriptor) {
        this.index = index;
        this.editTransactionDescriptor = new EditTransactionDescriptor(editTransactionDescriptor);
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) throws Exception {
        MyUi myUi = new MyUi();
        Transaction transactionToEdit = model.findTransactionByIndex(index);

        Transaction editedTransaction = createdEditedTransaction(transactionToEdit, editTransactionDescriptor);

        if (!transactionToEdit.isSameTransaction(editedTransaction) && model.hasTransaction(editedTransaction)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setTransaction(transactionToEdit, editedTransaction);
        if (!personModel.hasPerson(editedTransaction.getPerson())) {
            //personModel.addPerson(editedTransaction.getPerson());
            throw new NoSuchPersonException();
        }
        return new CommandResult(MyUi.editedTransaction(editedTransaction));
    }

    private static Transaction createdEditedTransaction(Transaction transactionToEdit, EditTransactionDescriptor editTransactionDescriptor) {

        String updatedDate = editTransactionDescriptor.getDate().orElse(transactionToEdit.getDate());
        String updatedDescription = editTransactionDescriptor.getDescription().orElse(transactionToEdit.getDescription());
        String updatedCategory = editTransactionDescriptor.getCategory().orElse(transactionToEdit.getCategory());
        double updatedAmount = editTransactionDescriptor.getAmount().orElse(transactionToEdit.getAmount());
        String updatedName = editTransactionDescriptor.getName().orElse(transactionToEdit.getName());

        return new Transaction(updatedDate, updatedDescription, updatedCategory, updatedAmount, updatedName);
    }

    public static class EditTransactionDescriptor {
        private String date;
        private String description;
        private String category;
        private double amount;
        private String name;
        private final DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy",
                Locale.ENGLISH);


        public EditTransactionDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTransactionDescriptor(EditTransactionDescriptor toCopy) {
            setDate(toCopy.date);
            setDescription(toCopy.description);
            setCategory(toCopy.category);
            setAmount(toCopy.amount);
            setName(toCopy.name);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(date, description, category, amount, name);
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Optional<String> getDate() {
            return Optional.ofNullable(date);
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Optional<String> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Optional<String> getCategory() {
            return Optional.ofNullable(category);
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public Optional<Double> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setName(String name) {
            this.name = name;
        }

        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTransactionDescriptor)) {
                return false;
            }

            // state check
            EditTransactionDescriptor e = (EditTransactionDescriptor) other;

            return getDate().equals(e.getDate())
                    && getDescription().equals(e.getDescription())
                    && getCategory().equals(e.getCategory())
                    && getAmount().equals(e.getAmount())
                    && getName().equals(e.getName());
        }
    }

}
