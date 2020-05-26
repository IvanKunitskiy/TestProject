package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C22573_EditCheckingAccountTest extends BaseTest {

    private IndividualClient client;
    private Account checkingAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up account
        checkingAccount = new Account().setCHKAccountData();

        // Login to the system and create a client with checking account
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22573, Edit checking account")
    @Severity(SeverityLevel.CRITICAL)
    public void editCheckingAccount() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the CHK account from the precondition and open it on Details");
//        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(checkingAccount);
        Pages.clientsSearchPage().typeToClientsSearchInputField(checkingAccount.getAccountNumber());
        Assert.assertTrue(Pages.clientsSearchPage().getAllLookupResults().size() == 1, "There is more than one client found");
        Assert.assertTrue(Pages.clientsSearchPage().isSearchResultsRelative(Pages.clientsSearchPage().getAllLookupResults(), checkingAccount.getAccountNumber()));
        Pages.clientsSearchPage().clickOnSearchButton();
        Pages.clientsSearchResultsPage().clickTheExactlyMatchedClientInSearchResults();
        Pages.clientDetailsPage().waitForPageLoaded();
        Pages.clientDetailsPage().clickAccountsTab();
        Pages.clientDetailsPage().openAccountByNumber(checkingAccount.getAccountNumber());

        logInfo("Step 3: Click [Edit] button");
        Pages.accountDetailsPage().clickEditButton();

        // TODO: Accounts page is under reconstruction. Check elements presence for commented out lines and delete or uncomment respectively.
        logInfo("Step 4: Look at the fields and verify that such fields are disabled for editing");
        Assert.assertTrue(Pages.editAccountPage().isProductTypeFieldDisabledInEditMode(), "'Product Type' field is not disabled");
//        Assert.assertTrue(Pages.editAccountPage().isProductFieldDisabledInEditMode(), "'Product' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccountNumberFieldDisabledInEditMode(), "'Account Number' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccountTypeFieldDisabledInEditMode(), "'Account Type' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isOriginatingOfficerFieldDisabledInEditMode(), "'Originating Officer' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccountStatusFieldDisabledInEditMode(), "'Account Status' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateOpenedFieldDisabledInEditMode(), "'Date Opened' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateClosedFieldDisabledInEditMode(), "'Date Closed field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAnnualPercentageYieldFieldDisabledInEditMode(), "'Annual Percentage Yield' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDaysOverdraftFieldDisabledInEditMode(), "'Times Overdrawn-6 Months' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDaysOverdraftAboveLimitFieldDisabledInEditMode(), "'Times $5000 Overdrawn-6 Months' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isLastDebitAmountFieldDisabledInEditMode(), "'Last Debit Amount' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAutomaticOverdraftLimitFieldDisabledInEditMode(), "'Automatic Overdraft Limit Field' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isTotalEarningsFieldDisabledInEditMode(), "'Total Earnings' field is not disabled");

        logInfo("Step 5: Select data in such dropdown fields that were not available in Add New mode");
        AccountActions.editAccount().selectValuesInDropdownFieldsThatWereNotAvailableDuringCheckingAccountCreation(checkingAccount);

        logInfo("Step 6: Fill in such text fields that were not displayed in Add new mode");
        AccountActions.editAccount().fillInInputFieldsThatWereNotAvailableDuringCheckingAccountCreation(checkingAccount);

        logInfo("Step 7: Select any other value in such fields");
        AccountActions.createAccount().setCurrentOfficer(checkingAccount);
        AccountActions.createAccount().setBankBranch(checkingAccount);
        Pages.editAccountPage().setInterestRate(checkingAccount.getInterestRate());
        AccountActions.createAccount().setCallClassCode(checkingAccount);
        AccountActions.createAccount().setChargeOrAnalyze(checkingAccount);

        logInfo("Step 8: Submit the account editing by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 9: Pay attention to CHK account fields");
        if (Pages.accountDetailsPage().isMoreButtonVisible()) {
            Pages.accountDetailsPage().clickMoreButton();
        }
        Assert.assertEquals(Pages.accountDetailsPage().getFederalWHReason(), checkingAccount.getFederalWHReason(), "'Federal WH Reason' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getReasonATMChargeWaived(), checkingAccount.getReasonATMChargeWaived(), "'Reason ATM Charge Waived' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getOdProtectionAcct(), checkingAccount.getOdProtectionAcct(), "'Od Protection Acct' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getReasonAutoNSFChgWaived(), checkingAccount.getReasonAutoNSFChgWaived(), "'Reason Auto NSF Chg Waived' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getReasonDebitCardChargeWaived(), checkingAccount.getReasonDebitCardChargeWaived(), "'Reason Debit Card Charge Waived' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getAutomaticOverdraftStatus(), checkingAccount.getAutomaticOverdraftStatus(), "'Automatic Overdraft Status' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getReasonAutoOdChgWaived(), checkingAccount.getReasonAutoOdChgWaived(), "'Reason Auto Od Chg Waived' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getWhenSurchargesRefunded(), checkingAccount.getWhenSurchargesRefunded(), "'When Surcharges Refunded' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getFederalWHPercent(), checkingAccount.getFederalWHPercent(), "'Federal WH Percent' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getNumberOfATMCardsIssued(), checkingAccount.getNumberOfATMCardsIssued(), "'Number Of ATM Cards Issued' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getEarningCreditRate(), checkingAccount.getEarningCreditRate(), "'Earning Credit Rate' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_1(), checkingAccount.getUserDefinedField_1(), "'User Defined Field 1' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_2(), checkingAccount.getUserDefinedField_2(), "'User Defined Field 2' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_3(), checkingAccount.getUserDefinedField_3(), "'User Defined Field 3' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_4(), checkingAccount.getUserDefinedField_4(), "'User Defined Field 4' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getNumberOfDebitCardsIssued(), checkingAccount.getNumberOfDebitCardsIssued(), "'Number Of Debit Cards Issued' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getAutomaticOverdraftLimit(), checkingAccount.getAutomaticOverdraftLimit(), "'Automatic Overdraft Limit' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCashCollDaysBeforeChg(), checkingAccount.getCashCollDaysBeforeChg(), "'Cash Coll Days Before Chg' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCashCollInterestChg(), checkingAccount.getCashCollInterestChg(), "'Cash Coll Interest Chg' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCashCollFloat(), checkingAccount.getCashCollFloat(), "'Cash Coll Float' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getPositivePay(), checkingAccount.getPositivePay(), "'Positive Pay' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCurrentOfficerValue(), checkingAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestRateValue(), checkingAccount.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankBranchValue(), checkingAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCallClassCode(), checkingAccount.getCallClassCode(), "'Call Class' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getChargeOrAnalyze(), checkingAccount.getChargeOrAnalyze(), "'Charge or Analyze' value does not match");

        logInfo("Step 10: Click [Edit] button and pay attention to the fields");
        Pages.accountDetailsPage().clickEditButton();
        Assert.assertEquals(Pages.editAccountPage().getFederalWHReasonInEditMode(), checkingAccount.getFederalWHReason(), "'Federal WH Reason' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getReasonATMChargeWaived(), checkingAccount.getReasonATMChargeWaived(), "'Reason ATM Charge Waived' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getOdProtectionAcct(), checkingAccount.getOdProtectionAcct(), "'Od Protection Acct' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getReasonAutoNSFChgWaived(), checkingAccount.getReasonAutoNSFChgWaived(), "'Reason Auto NSF Chg Waived' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getReasonDebitCardChargeWaived(), checkingAccount.getReasonDebitCardChargeWaived(), "'Reason Debit Card Charge Waived' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getAutomaticOverdraftStatus(), checkingAccount.getAutomaticOverdraftStatus(), "'Automatic Overdraft Status' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getReasonAutoOdChgWaived(), checkingAccount.getReasonAutoOdChgWaived(), "'Reason Auto Od Chg Waived' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getWhenSurchargesRefunded(), checkingAccount.getWhenSurchargesRefunded(), "'When Surcharges Refunded' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getFederalWHPercent(), checkingAccount.getFederalWHPercent(), "'Federal WH Percent' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getNumberOfATMCardsIssued(), checkingAccount.getNumberOfATMCardsIssued(), "'Number Of ATM Cards Issued' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getEarningCreditRate(), checkingAccount.getEarningCreditRate(), "'Earning Credit Rate' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField1(), checkingAccount.getUserDefinedField_1(), "'User Defined Field 1' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField2(), checkingAccount.getUserDefinedField_2(), "'User Defined Field 2' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField3(), checkingAccount.getUserDefinedField_3(), "'User Defined Field 3' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField4(), checkingAccount.getUserDefinedField_4(), "'User Defined Field 4' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getNumberOfDebitCardsIssued(), checkingAccount.getNumberOfDebitCardsIssued(), "'Number Of Debit Cards Issued' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getAutomaticOverdraftLimit(), checkingAccount.getAutomaticOverdraftLimit(), "'Automatic Overdraft Limit' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCashCollDaysBeforeChg(), checkingAccount.getCashCollDaysBeforeChg(), "'Cash Coll Days Before Chg' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCashCollInterestChg(), checkingAccount.getCashCollInterestChg(), "'Cash Coll Interest Chg' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCashCollFloat(), checkingAccount.getCashCollFloat(), "'Cash Coll Float' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getPositivePay(), checkingAccount.getPositivePay(), "'Positive Pay' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCurrentOfficerValueInEditMode(), checkingAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getInterestRateValueInEditMode(), checkingAccount.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getBankBranchValueInEditMode(), checkingAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCallClassCodeValueInEditMode(), checkingAccount.getCallClassCode(), "'Call Class' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getChargeOrAnalyzeInEditMode(), checkingAccount.getChargeOrAnalyze(), "'Charge or Analyze' value does not match");

        logInfo("Step 11: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 12: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        AccountActions.accountMaintenanceActions().expandAllRows();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Product") == 1,
                "'Product' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("accounttype") == 1,
                "'accounttype' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Account Title") == 1,
                "'Account Title' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Current Officer") == 1,
                "'Current Officer' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Bank Branch") == 1,
                "'Bank Branch' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Date Opened") == 1,
                "'Date Opened' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Rate") == 1,
                "'Interest Rate' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Statement Cycle") == 1,
                "'Statement Cycle' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Charge or analyze") == 1,
                "'Charge or analyze' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Account analysis") == 1,
                "'Account analysis' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("User Defined Field 1") == 1,
                "'User Defined Field 1' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("User Defined Field 2") == 1,
                "'User Defined Field 2' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("User Defined Field 3") == 1,
                "'User Defined Field 3' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("User Defined Field 4") == 1,
                "'User Defined Field 4' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Federal W/H reason") == 1,
                "'Federal W/H reason' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Federal W/H percent") == 1,
                "'Federal W/H percent' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Reason Debit Card Charge Waived") == 1,
                "'Reason Debit Card Charge Waived' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Number of Debit Cards issued") == 1,
                "'Number of Debit Cards issued' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Automatic Overdraft Limit") == 1,
                "'Automatic Overdraft Limit' row count is incorrect!");
    }

}