package com.nymbus.actions.loans;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.pages.Pages;

public class LoanInsurancePlanSetupActions {

    public void createLoanInsurancePlanSetupIfNotExists(String code, String company, String date){
        Pages.aSideMenuPage().clickLoansMenuItem();
        Pages.loansPage().clickViewAllLoanInsurancePlanSetup();
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        Pages.loanInsurancePlanSetupPage().searchPlan(code);
        boolean isExist = !Pages.loanInsurancePlanSetupPage().checkExistPlan();
        if(isExist) {
            Pages.loanInsurancePlanSetupPage().clickAddNewButton();
            Pages.addNewLoanInsurancePlanSetupPage().inputCode(code);
            Pages.addNewLoanInsurancePlanSetupPage().inputDescription(code);
            Pages.addNewLoanInsurancePlanSetupPage().inputCompany(code);
            Pages.addNewLoanInsurancePlanSetupPage().inputInsuranceType(company);
            Pages.addNewLoanInsurancePlanSetupPage().inputFormula("pro rata");
            Pages.addNewLoanInsurancePlanSetupPage().inputMinimumEarnings("1100");
            Pages.addNewLoanInsurancePlanSetupPage().inputMaximumRebate("100000000");
            Pages.addNewLoanInsurancePlanSetupPage().inputDate(date);
            Pages.addNewInsuranceCompanyPage().clickSaveChangesButton();
        }
    }
}
