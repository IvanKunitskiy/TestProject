package com.nymbus.actions.account;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Generator;
import com.nymbus.newmodels.accountinstructions.ActivityHoldInstruction;
import com.nymbus.pages.Pages;

public class EditInstructionActions {

    public void addTextToNotesField() {
        Pages.accountInstructionsPage().typeNotesValueWithoutWipe(Generator.genString(10));
    }

    public void changeExpirationDate() {
        Pages.accountInstructionsPage().typeExpirationDateValue(DateTime.getDateTodayPlusDaysWithFormat(2, "MM/dd/yyyy"));
    }

    public void editActivityHoldInstruction(ActivityHoldInstruction instruction) {
        addTextToNotesField();
        changeExpirationDate();
        Pages.accountInstructionsPage().clickSaveButton();
        fillInSupervisorModal();
    }

    public void deleteActivityHoldInstruction(ActivityHoldInstruction instruction) {
        Pages.accountInstructionsPage().clickDeleteButton();
        fillInSupervisorModal();
    }

    private void fillInSupervisorModal() {
        Pages.supervisorModalPage().inputLogin(Constants.USERNAME);
        Pages.supervisorModalPage().inputPassword(Constants.PASSWORD);
        Pages.supervisorModalPage().clickEnter();
        Pages.supervisorModalPage().waitForModalWindowInvisibility();
    }
}
