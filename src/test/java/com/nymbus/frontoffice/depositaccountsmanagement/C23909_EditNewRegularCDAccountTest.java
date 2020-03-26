package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C23909_EditNewRegularCDAccountTest extends BaseTest {

    Client client;
    Account cdAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");
        client.setSelectOfficer("autotest autotest"); // Controlling officer to validate 'Bank Branch' value

        // Set up IRA account
        cdAccount = new Account().setCDAccountData();
        cdAccount.setBankBranch("Inspire - Langhorne"); // Branch of the 'autotest autotest' user
        cdAccount.setApplyInterestTo("Remain in Account");
        cdAccount.setTermType("3");
        cdAccount.setMaturityDate(DateTime.getDateWithNMonthAdded(cdAccount.getDateOpened(), "MM/dd/yyyy", Integer.parseInt(cdAccount.getTermType())));
        cdAccount.setDateNextInterest(DateTime.getDateWithNMonthAdded(cdAccount.getDateOpened(), "MM/dd/yyyy", 3)); // 3 month added as 'Interest Frequency' is set to 'Quarterly'

        // Login to the system and create a client with IRA account
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        AccountActions.createAccount().createCDAccount(cdAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "Edit New Regular CD Account")
    @Severity(SeverityLevel.CRITICAL)
    public void editNewRegularCDAccount() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the CD account from the precondition and open it in Edit mode");
        Pages.clientsSearchPage().typeToClientsSearchInputField(cdAccount.getAccountNumber());
        Assert.assertTrue(Pages.clientsSearchPage().getAllLookupResults().size() == 1, "There is more than one client found");
        Assert.assertTrue(Pages.clientsSearchPage().isSearchResultsRelative(Pages.clientsSearchPage().getAllLookupResults(), cdAccount.getAccountNumber()));
        Pages.clientsSearchPage().clickOnSearchButton();
        Pages.clientsSearchResultsPage().clickTheExactlyMatchedClientInSearchResults();
        Pages.clientDetailsPage().waitForPageLoaded();
        Pages.clientDetailsPage().clickAccountsTab();
        Pages.clientDetailsPage().openAccountByNumber(cdAccount.getAccountNumber());
        Pages.accountDetailsPage().clickEditButton();

        logInfo("Step 3: Look at the fields and verify that such fields are disabled for editing");
        Assert.assertTrue(Pages.editAccountPage().isProductTypeFieldDisabledInEditMode(), "'Product Type' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isProductFieldDisabledInEditMode(), "'Product' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccountNumberFieldDisabledInEditMode(), "'Account Number' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccountTypeFieldDisabledInEditMode(), "'Account Type' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isOriginatingOfficerFieldDisabledInEditMode(), "'Originating Officer' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccountStatusFieldDisabledInEditMode(), "'Account Status' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateOpenedFieldDisabledInEditMode(), "'Date Opened' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateClosedFieldDisabledInEditMode(), "'Date Closed field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isCurrentBalanceDisabledInEditMode(), "'Current Balance' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isOriginalBalanceDisabledInEditMode(), "'Original Balance' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAmountLastInterestPaidDisabledInEditMode(), "'Amount Last Interest Paid' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateLastInterestPaidDisabledInEditMode(), "'Date Last Interest Paid' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isTermTypeMonthsDisabledInEditMode(), "'Term Type Months' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAnnualPercentageYieldFieldDisabledInEditMode(), "'Annual Percentage Yield' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateNextInterestDisabledInEditMode(), "'Date next interest' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isNextInterestPaymentAmountDisabledInEditMode(), "' Next Interest Payment Amount' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isMaturityDateDisabledInEditMode(), "'Maturity Date' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccruedInterestDisabledInEditMode(), "'Accrued Interest' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isPenaltyAmountYTDDisabledInEditMode(), "'Penalty Amount Year-to-date' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDailyInterestAccrualDisabledInEditMode(), "'Daily Interest Accrual' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isBalanceAtRenewalDisabledInEditMode(), "'Balance At Renewal' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateOfRenewalDisabledInEditMode(), "'Date of renewal' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isInterestRateAtRenewalDisabledInEditMode(), "'Interest rate at renewal' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isRenewalAmountDisabledInEditMode(), "'Renewal amount' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isSpecialMailingInstructionsDisabledInEditMode(), "'Special Mailing Instructions' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isTaxesWithheldYTDDisabledInEditMode(), "'Taxes Withheld YTD' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isBalanceAtEndOfYearDisabledInEditMode(), "'Balance at end of year' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccruedInterestAtEndOfYearDisabledInEditMode(), "'Accrued interest at end of year' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isIraPlanNumberDisabledInEditMode(), "'IRA plan number' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isInterestPaidLastYearDisabledInEditMode(), "'Interest Paid Last Year' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isPrintInterestNoticeOverrideDisabledInEditMode(), "'Print interest notice override' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isTotalEarningsDisabledInEditMode(), "'Total Earnings for Life of Account' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isTotalContributionsDisabledInEditMode(), "'Total Contributions for Life of Account' field is not disabled");

        logInfo("Step 4: Select data in such dropdown fields that were not available in Add New mode");
        AccountActions.editAccount().setFederalWHReason(cdAccount);

        logInfo("Step 5: Fill in such text fields that were not displayed in Add new mode");
        AccountActions.editAccount().fillInInputFieldsThatWereNotAvailableDuringCDAccountCreation(cdAccount);

        logInfo("Step 6: Select any other value in such fields");
        AccountActions.editAccount().setCurrentOfficer(cdAccount);
        Pages.editAccountPage().setInterestRate(cdAccount.getInterestRate());
        AccountActions.editAccount().setBankBranch(cdAccount);
        AccountActions.editAccount().setCallClassCode(cdAccount);

        logInfo("Step 7: Set switcher Transactional Account = NO");
        if (!Pages.editAccountPage().getTransactionalAccountInEditMode().equals("NO")) {
            Pages.editAccountPage().clickTransactionalAccountSwitch();
        }

        logInfo("Step 8: Submit the account editing by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 9: Pay attention to CD account fields");
        Pages.accountDetailsPage().clickMoreButton();
        Assert.assertEquals(Pages.accountDetailsPage().getFederalWHReason(), cdAccount.getFederalWHReason(), "'Federal WH Reason' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getFederalWHPercent(), cdAccount.getFederalWHPercent(), "'Federal WH Percent' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_1(), cdAccount.getUserDefinedField_1(), "'User Defined Field 1' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_2(), cdAccount.getUserDefinedField_2(), "'User Defined Field 2' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_3(), cdAccount.getUserDefinedField_3(), "'User Defined Field 3' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_4(), cdAccount.getUserDefinedField_4(), "'User Defined Field 4' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCurrentOfficerValue(), cdAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestRateValue(), cdAccount.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankBranchValue(), cdAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCallClassCode(), cdAccount.getCallClassCode(), "'Call Class' value does not match");

        logInfo("Step 10: Click [Edit] button and pay attention to the fields");
        Pages.accountDetailsPage().clickEditButton();
        Assert.assertEquals(Pages.editAccountPage().getFederalWHReasonInEditMode(), cdAccount.getFederalWHReason(), "'Federal WH Reason' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getFederalWHPercent(), cdAccount.getFederalWHPercent(), "'Federal WH Percent' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField1(), cdAccount.getUserDefinedField_1(), "'User Defined Field 1' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField2(), cdAccount.getUserDefinedField_2(), "'User Defined Field 2' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField3(), cdAccount.getUserDefinedField_3(), "'User Defined Field 3' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField4(), cdAccount.getUserDefinedField_4(), "'User Defined Field 4' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCurrentOfficerValueInEditMode(), cdAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getInterestRateValueInEditMode(), cdAccount.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getBankBranchValueInEditMode(), cdAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCallClassCodeValueInEditMode(), cdAccount.getCallClassCode(), "'Call Class' value does not match");

        logInfo("Step 11: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 12: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        // TODO: Implement verification at Maintenance History page
    }
}
