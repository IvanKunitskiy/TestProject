package com.nymbus.actions.loans;

import com.nymbus.pages.Pages;

public class AddNewLoanActions {

    public void disableReadonlyByFieldName(String fieldName) {
        if (Pages.addNewLoanProductPage().isFieldReadonlyByName(fieldName)) {
            Pages.addNewLoanProductPage().clickReadonlyToggleByFieldName(fieldName);
        }
    }

}
