package com.nymbus.actions.account;

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
}
