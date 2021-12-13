package com.nymbus.actions.loans;

import com.nymbus.pages.Pages;

public class LoanPaymentInfoActions {
    public void setOtherPaymentsFrequency(String frequencyType) {
        Pages.accountPaymentInfoPage().clickOtherPaymentsFrequency();
        Pages.accountPaymentInfoPage().pickOtherPaymentsFrequencyDropdownItem(frequencyType);
    }

    public void setOtherPaymentsPaymentType(String paymentType) {
        Pages.accountPaymentInfoPage().clickOtherPaymentsPaymentType();
        Pages.accountPaymentInfoPage().pickOtherPaymentsPaymentTypeDropdownItem(paymentType);
    }
}
