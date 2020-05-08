package com.nymbus.actions.account;

import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Generator;
import com.nymbus.pages.Pages;

public class EditInstructionActions {

    public void addTextToNotesField() {
        Pages.accountInstructionsPage().typeNotesValueWithoutWipe(Generator.genString(10));
    }

    public void changeExpirationDate() {
        Pages.accountInstructionsPage().typeExpirationDateValue(DateTime.getDateTodayPlusDaysWithFormat(2, "MM/dd/yyyy"));
    }
}
