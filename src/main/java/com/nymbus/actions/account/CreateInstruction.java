package com.nymbus.actions.account;

import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.accountinstructions.HoldInstruction;
import com.nymbus.pages.Pages;

public class CreateInstruction {

    public void createHoldInstruction(HoldInstruction instruction) {
        Pages.accountInstructionsPage().clickNewInstructionButton();
        setInstructionType(instruction.getType());
        setAmount(String.format("%.2f", instruction.getAmount()));
        setDate(instruction.getExpirationDate());
        setNotes(instruction.getNotes());
        Pages.accountInstructionsPage().clickSaveButton();
    }

    public void createActivityHoldInstruction(HoldInstruction instruction) {
        Pages.accountInstructionsPage().clickNewInstructionButton();
        setInstructionType(instruction.getType());
        setDate(instruction.getExpirationDate());
        setNotes(instruction.getNotes());
        Pages.accountInstructionsPage().clickSaveButton();
    }

    public void editHoldInstruction(HoldInstruction instruction) {
        Pages.accountInstructionsPage().clickInstructionInListByIndex(1);
        Pages.accountInstructionsPage().clickEditButton();
        setAmount(String.format("%.2f", instruction.getAmount()));
        setDate(instruction.getExpirationDate());
        setNotes(instruction.getNotes());
        Pages.accountInstructionsPage().clickSaveButton();
        fillingSupervisorModal();
        Pages.accountInstructionsPage().waitForSaveButtonInvisibility();
    }

    public void deleteInstruction(int index) {
        Pages.accountInstructionsPage().clickInstructionInListByIndex(index);

        Pages.accountInstructionsPage().clickDeleteButton();

        fillingSupervisorModal();

        Pages.accountInstructionsPage().waitForDeleteButtonInvisibility();
    }

    private void fillingSupervisorModal() {
        Pages.supervisorModalPage().inputLogin(Constants.USERNAME);

        Pages.supervisorModalPage().inputPassword(Constants.PASSWORD);

        Pages.supervisorModalPage().clickEnter();

        Pages.supervisorModalPage().waitForModalWindowInvisibility();
    }

    private void setNotes(String notes) {
        Pages.accountInstructionsPage().typeNotesValue(notes);
    }

    private void setDate(String expirationDate) {
        Pages.accountInstructionsPage().typeExpirationDateValue(expirationDate);
    }

    private void setAmount(String amount) {
        Pages.accountInstructionsPage().typeAmountValue(amount);
    }

    private void setInstructionType(String type) {
        Pages.accountInstructionsPage().clickInstructionDropdownSelectButton();

        Pages.accountInstructionsPage().clickDropDownItem(type);
    }

    public int getInstructionCount() {
        return Pages.accountInstructionsPage().isInstructionsListVisible() ?
                Pages.accountInstructionsPage().getCreatedInstructionsCount() : 0;
    }
}
