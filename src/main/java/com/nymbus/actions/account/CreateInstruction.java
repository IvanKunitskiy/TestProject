package com.nymbus.actions.account;

import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.accountinstructions.*;
import com.nymbus.newmodels.accountinstructions.ActivityHoldInstruction;
import com.nymbus.newmodels.accountinstructions.CreditHoldInstruction;
import com.nymbus.newmodels.accountinstructions.DebitHoldInstruction;
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

    public void createDebitHoldInstruction(DebitHoldInstruction instruction) {
        Pages.accountInstructionsPage().clickNewInstructionButton();
        setInstructionType(instruction.getType());
        setDate(instruction.getExpirationDate());
        setNotes(instruction.getNotes());
        Pages.accountInstructionsPage().clickSaveButton();
    }

    public void createCreditHoldInstruction(CreditHoldInstruction instruction) {
        Pages.accountInstructionsPage().clickNewInstructionButton();
        setInstructionType(instruction.getType());
        setDate(instruction.getExpirationDate());
        setNotes(instruction.getNotes());
        Pages.accountInstructionsPage().clickSaveButton();
    }

    public void createStopPaymentInstruction(StopPaymentInstruction instruction) {
        Pages.accountInstructionsPage().clickNewInstructionButton();
        setInstructionType(instruction.getType());
        setStopType(instruction.getStopType().getStopType());
        setDate(instruction.getExpirationDate());
        setBeginCheck(instruction.getBeginCheck());
        setEndCheck(instruction.getEndCheck());
        setLowDollar(String.valueOf(instruction.getLowAmount()));
        setHighDollar(String.valueOf(instruction.getHighAmount()));
        setNotes(instruction.getNotes());
        Pages.accountInstructionsPage().clickSaveButton();
    }

    public void createDebitHoldInstruction(DebitHoldInstruction instruction) {
        Pages.accountInstructionsPage().clickNewInstructionButton();
        setInstructionType(instruction.getType());
        setDate(instruction.getExpirationDate());
        setNotes(instruction.getNotes());
        Pages.accountInstructionsPage().clickSaveButton();
    }

    public void createCreditHoldInstruction(CreditHoldInstruction instruction) {
        Pages.accountInstructionsPage().clickNewInstructionButton();
        setInstructionType(instruction.getType());
        setDate(instruction.getExpirationDate());
        setNotes(instruction.getNotes());
        Pages.accountInstructionsPage().clickSaveButton();
    }

    public void createActivityHoldInstruction(ActivityHoldInstruction instruction) {
        Pages.accountInstructionsPage().clickNewInstructionButton();
        setInstructionType(instruction.getType());
        setDate(instruction.getExpirationDate());
        setNotes(instruction.getNotes());
        Pages.accountInstructionsPage().clickSaveButton();
        Pages.accountInstructionsPage().waitForLoadingSpinnerInvisibility();
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
        Pages.accountInstructionsPage().waitForDeletedInstructionInvisibility(index);
    }

    public void deleteInstructionByReasonText(String text) {
        int instructionsCount = Pages.accountInstructionsPage().getCreatedInstructionsCount();

        int instructionToDeleteIndex = getInstructionIndexByReasonText(text, instructionsCount);

        if (instructionToDeleteIndex > 0) {
            deleteInstruction(instructionToDeleteIndex);
        }
    }

    private int getInstructionIndexByReasonText(String text, int count) {
        for (int i = 1; i <= count; i++) {
            Pages.accountInstructionsPage().clickInstructionInListByIndex(i);
            if (Pages.accountInstructionsPage().getReasonText().equals(text)) {
                return i;
            }
        }
        return -1;
    }

    public void fillingSupervisorModal() {
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

    private void setBeginCheck(String beginCheck) {
        Pages.accountInstructionsPage().typeBeginCheckValue(beginCheck);
    }

    private void setEndCheck(String endCheck) {
        Pages.accountInstructionsPage().typeEndCheckValue(endCheck);
    }

    private void setLowDollar(String lowDollar) {
        Pages.accountInstructionsPage().typeLowDollarValue(lowDollar);
    }

    private void setHighDollar(String highDollar) {
        Pages.accountInstructionsPage().typeHighDollarValue(highDollar);
    }

    private void setInstructionType(String type) {
        Pages.accountInstructionsPage().clickInstructionDropdownSelectButton();

        Pages.accountInstructionsPage().clickDropDownItem(type);
    }

    private void setStopType(String type) {
        Pages.accountInstructionsPage().clickStopTypeDropdownSelectButton();

        Pages.accountInstructionsPage().clickDropDownItem(type);
    }

    public int getInstructionCount() {
        return Pages.accountInstructionsPage().isInstructionsListVisible() ?
                Pages.accountInstructionsPage().getCreatedInstructionsCount() : 0;
    }
}
