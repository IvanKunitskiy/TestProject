package com.nymbus.actions.loans;

import com.nymbus.pages.Pages;

public class InsuranceCompaniesActions {

    public void createInsuranceCompanyIfNotExists(String code, String glAccount){
        Pages.aSideMenuPage().clickLoansMenuItem();
        Pages.loansPage().clickViewAllInsuranceCompanies();
        Pages.insuranceCompaniesPage().searchCompany(code);
        boolean isExist = !Pages.insuranceCompaniesPage().checkExistCompany();
        if(isExist) {
            Pages.insuranceCompaniesPage().clickAddNewButton();
            Pages.addNewInsuranceCompanyPage().inputCode(code);
            Pages.addNewInsuranceCompanyPage().inputName(code);
            Pages.addNewInsuranceCompanyPage().inputGlAccount(glAccount);
            Pages.addNewInsuranceCompanyPage().clickSaveChangesButton();
        }
    }
}
