package com.nymbus.actions.modalwindow;

import com.nymbus.pages.Pages;

public class LoanSkipPaymentModalActions {

    public void setFeeAddOnPaymentToggleToNo() {
        if (Pages.loanSkipPaymentModalPage().getFeeAddOnPaymentToggleValue().equalsIgnoreCase("yes")) {
            Pages.loanSkipPaymentModalPage().clickFeeAddOnPaymentToggle();
        }
    }

    public void setFeeAddOnPaymentToggleToYes() {
        if (Pages.loanSkipPaymentModalPage().getFeeAddOnPaymentToggleValue().equalsIgnoreCase("no")) {
            Pages.loanSkipPaymentModalPage().clickFeeAddOnPaymentToggle();
        }
    }

    public void setExtendMaturityToggleToNo() {
        if (Pages.loanSkipPaymentModalPage().getExtendMaturityToggleValue().equalsIgnoreCase("yes")) {
            Pages.loanSkipPaymentModalPage().clickExtendMaturityToggle();
        }
    }

    public void setExtendMaturityToggleToYes() {
        if (Pages.loanSkipPaymentModalPage().getExtendMaturityToggleValue().equalsIgnoreCase("no")) {
            Pages.loanSkipPaymentModalPage().clickExtendMaturityToggle();
        }
    }
}