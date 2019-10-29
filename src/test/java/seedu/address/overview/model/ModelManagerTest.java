package seedu.address.overview.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.testutil.TypicalOverview.BLANK_OVERVIEW_MODEL;
import static seedu.address.testutil.TypicalOverview.OVERVIEW_MODEL_WITH_DATA;


import org.junit.jupiter.api.Test;

public class ModelManagerTest {

    @Test
    public void newModelManager_blankData() {
        Model model = new ModelManager(BLANK_OVERVIEW_MODEL);

        assertEquals(0.0, model.getBudgetTarget());
        assertEquals(0.0, model.getExpenseTarget());
        assertEquals(0.0, model.getSalesTarget());

        assertEquals(0.0, model.getBudgetThreshold());
        assertEquals(0.0, model.getExpenseThreshold());
        assertEquals(0.0, model.getSalesThreshold());

    }

    @Test
    public void newModelManager_withData() {
        Model model = new ModelManager(OVERVIEW_MODEL_WITH_DATA);

        assertEquals(1500.0, model.getBudgetTarget());
        assertEquals(500.0, model.getExpenseTarget());
        assertEquals(200.0, model.getSalesTarget());

        assertEquals(80.0, model.getBudgetThreshold());
        assertEquals(90.0, model.getExpenseThreshold());
        assertEquals(100.0, model.getSalesThreshold());

    }

    @Test
    public void setModelManagerFromBlank_correctInput_success() {
        Model model = new ModelManager(BLANK_OVERVIEW_MODEL);

        model.setBudgetTarget(500);
        assertEquals(500.0, model.getBudgetTarget());

        model.setExpenseTarget(50);
        assertEquals(50.0, model.getExpenseTarget());

        model.setSalesTarget(5);
        assertEquals(5.0, model.getSalesTarget());

    }

    @Test
    public void setModelManagerFromExisting_correctInput_success() {

        Model model = new ModelManager(OVERVIEW_MODEL_WITH_DATA);

        model.setBudgetTarget(500);
        assertEquals(500.0, model.getBudgetTarget());

        model.setExpenseTarget(50);
        assertEquals(50.0, model.getExpenseTarget());

        model.setSalesTarget(5);
        assertEquals(5.0, model.getSalesTarget());

    }

}
