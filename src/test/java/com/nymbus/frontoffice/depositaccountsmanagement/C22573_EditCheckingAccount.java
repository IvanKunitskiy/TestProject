package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.base.BaseTest;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.pages.Pages;
import com.nymbus.util.Constants;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C22573_EditCheckingAccount extends BaseTest {

    Client client;
    Account checkingAccount;

    @BeforeMethod
    public void preCondition() {
        // set up client and account
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");
        checkingAccount = new Account().setCHKAccountData();
        checkingAccount.setAutomaticOverdraftStatus("Active");

        // login to the system and create a client with checking account
        navigateToUrl(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22573, Edit checking account")
    @Severity(SeverityLevel.CRITICAL)
    public void editCheckingAccount() {

        LOG.info("Step 1: Log in to the system");
        navigateToUrl(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        LOG.info("Step 2: Search for the CHK account from the precondition and open it on Details");
        Pages.clientsPage().typeToClientsSearchInputField(checkingAccount.getAccountNumber());
        Assert.assertTrue(Pages.clientsPage().getAllLookupResults().size() == 1, "There is more than one client found");
        Assert.assertTrue(Pages.clientsPage().isSearchResultsRelative(Pages.clientsPage().getAllLookupResults(), checkingAccount.getAccountNumber()));
        Pages.clientsPage().clickOnSearchButton();
        Pages.clientsSearchResultsPage().clickTheExactlyMatchedClientInSearchResults();
        Pages.clientDetailsPage().waitForPageLoaded();
        Pages.clientDetailsPage().clickAccountsTab();
        Pages.clientDetailsPage().openAccountByNumber(checkingAccount.getAccountNumber());

        LOG.info("Step 3: Click [Edit] button");
        Pages.accountDetailsPage().clickEditButton();

        LOG.info("Step 4: Look at the fields and verify that such fields are disabled for editing");
        Assert.assertTrue(Pages.accountDetailsPage().isProductTypeFieldDisabledInEditMode(), "'Product Type' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isProductFieldDisabledInEditMode(), "'Product' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isAccountNumberFieldDisabledInEditMode(), "'Account Number' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isAccountTypeFieldDisabledInEditMode(), "'Account Type' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isOriginatingOfficerFieldDisabledInEditMode(), "'Originating Officer' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isAccountStatusFieldDisabledInEditMode(), "'Account Status' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isDateOpenedFieldDisabledInEditMode(), "'Date Opened' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isDateClosedFieldDisabledInEditMode(), "'Date Closed field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isAnnualPercentageYieldFieldDisabledInEditMode(), "'Annual Percentage Yield' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isDaysOverdraftFieldDisabledInEditMode(), "'Times Overdrawn-6 Months' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isDaysOverdraftAboveLimitFieldDisabledInEditMode(), "'Times $5000 Overdrawn-6 Months' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isLastDebitAmountFieldDisabledInEditMode(), "'Last Debit Amount' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isAutomaticOverdraftLimitFieldDisabledInEditMode(), "'Automatic Overdraft Limit Field' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isTotalEarningsFieldDisabledInEditMode(), "'Total Earnings' field is not disabled");

        LOG.info("Step 5: Select data in such dropdown fields that were not available in Add New mode");
        AccountActions.editAccount().setFederalWHReason(checkingAccount);
        AccountActions.editAccount().setReasonATMChargeWaived(checkingAccount);
//        AccountActions.editAccount().setOdProtectionAcct(checkingAccount);
        AccountActions.editAccount().setReasonAutoNSFChgWaived(checkingAccount);
        AccountActions.editAccount().setReasonDebitCardChargeWaived(checkingAccount);
        AccountActions.editAccount().setAutomaticOverdraftStatus(checkingAccount);
        AccountActions.editAccount().setReasonAutoOdChgWaived(checkingAccount);
        AccountActions.editAccount().setWhenSurchargesRefunded(checkingAccount);

        LOG.info("Step 6: Fill in such text fields that were not displayed in Add new mode");
        Pages.accountDetailsPage().setFederalWHPercent(checkingAccount.getFederalWHPercent()); // pass from account object
        Pages.accountDetailsPage().setNumberOfATMCardsIssued(checkingAccount.getNumberOfATMCardsIssued());
        Pages.accountDetailsPage().setEarningCreditRate(checkingAccount.getEarningCreditRate()); // pass from account object
        Pages.accountDetailsPage().setUserDefinedField_1(checkingAccount.getUserDefinedField_1());
        Pages.accountDetailsPage().setUserDefinedField_2(checkingAccount.getUserDefinedField_1());
        Pages.accountDetailsPage().setUserDefinedField_3(checkingAccount.getUserDefinedField_1());
        Pages.accountDetailsPage().setUserDefinedField_4(checkingAccount.getUserDefinedField_1());
        Pages.accountDetailsPage().setImageStatementCode(checkingAccount.getImageStatementCode());
        Pages.accountDetailsPage().setNumberOfDebitCardsIssued(checkingAccount.getNumberOfDebitCardsIssued());
//        - Automatic Overdraft Limit - numeric value (12 digits value)
        Pages.accountDetailsPage().setCashCollDaysBeforeChg(checkingAccount.getCashCollDaysBeforeChg());
        Pages.accountDetailsPage().setCashCollInterestRate(checkingAccount.getCashCollInterestRate());
        Pages.accountDetailsPage().setCashCollInterestChg(checkingAccount.getCashCollInterestChg());
        Pages.accountDetailsPage().setCashCollFloat(checkingAccount.getCashCollFloat());
        Pages.accountDetailsPage().setPositivePay(checkingAccount.getPositivePay());

        LOG.info("Step 7: Select any other value in such fields");
        AccountActions.createAccount().setCurrentOfficer(checkingAccount);
        AccountActions.createAccount().setBankBranch(checkingAccount);
        Pages.accountDetailsPage().setInterestRate(checkingAccount.getInterestRate());
        AccountActions.createAccount().setCallClassCode(checkingAccount);
        AccountActions.createAccount().setChargeOrAnalyze(checkingAccount);

        LOG.info("Step 8: Submit the account editing by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        LOG.info("Step 9: Pay attention to CHK account fields");
        Pages.accountDetailsPage().clickMoreButton();

        Assert.assertEquals(Pages.accountDetailsPage().getFederalWHReason(), checkingAccount.getFederalWHReason());
        Assert.assertEquals(Pages.accountDetailsPage().getReasonATMChargeWaived(), checkingAccount.getReasonATMChargeWaived());
//        Assert.assertEquals(Pages.accountDetailsPage().getOdProtectionAcct(), checkingAccount.getOdProtectionAcct());
        Assert.assertEquals(Pages.accountDetailsPage().getReasonAutoNSFChgWaived(), checkingAccount.getReasonAutoNSFChgWaived());
        Assert.assertEquals(Pages.accountDetailsPage().getReasonDebitCardChargeWaived(), checkingAccount.getReasonDebitCardChargeWaived());
        Assert.assertEquals(Pages.accountDetailsPage().getAutomaticOverdraftStatus(), checkingAccount.getAutomaticOverdraftStatus());
        Assert.assertEquals(Pages.accountDetailsPage().getReasonAutoOdChgWaived(), checkingAccount.getReasonAutoOdChgWaived());
//        - When surcharges refunded
//        - Federal W/H percent - min - 4 digits pass the decimal, max - 100%
//        - Number of ATM cards issue- any numeric value
//        - Earning Credit Rate min - 4 digits pass the decimal, max - 100%
//        - User Defined Field 1
//        - User Defined Field 2
//        - User Defined Field 3
//        - User Defined Field 4
//        - Image statement code - numeric value
//        - Number of Debit Cards issued
//        - Automatic Overdraft Limit - numeric value (12 digits value)
//        - Cash coll # days before chg - numeric value
//        - Cash coll interest chg - numeric value
//        - Cash coll float - numeric value
//        - Positive pay - alphanumeric
        Assert.assertEquals(Pages.accountDetailsPage().getCurrentOfficerValue(), checkingAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestRateValue(), checkingAccount.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankBranchValue(), checkingAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCallClassCode(), checkingAccount.getCallClassCode(), "'Call Class' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getChargeOrAnalyze(), checkingAccount.getChargeOrAnalyze(), "'Charge or Analyze' value does not match");

        LOG.info("Step 10: Click [Edit] button and pay attention to the fields");
        Pages.accountDetailsPage().clickEditButton();

//        - Federal W/H reason
//        - Reason ATM charge waived
//        - OD protection acct #
//        - Reason auto NSF chg waived
//        - Reason Debit Card Charge Waived
//        - Automatic Overdraft Status = Active
//        - Reason auto OD chg waived
//        - When surcharges refunded
//        - Federal W/H percent - min - 4 digits pass the decimal, max - 100%
//        - Number of ATM cards issue- any numeric value
//        - Earning Credit Rate min - 4 digits pass the decimal, max - 100%
//        - User Defined Field 1
//        - User Defined Field 2
//        - User Defined Field 3
//        - User Defined Field 4
//        - Image statement code - numeric value
//        - Number of Debit Cards issued
//        - Automatic Overdraft Limit - numeric value (12 digits value)
//        - Cash coll # days before chg - numeric value
//        - Cash coll interest chg - numeric value
//        - Cash coll float - numeric value
//        - Positive pay - alphanumeric
        Assert.assertEquals(Pages.accountDetailsPage().getCurrentOfficerValueInEditMode(), checkingAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestRateValueInEditMode(), checkingAccount.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankBranchValueInEditMode(), checkingAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCallClassCodeValueInEditMode(), checkingAccount.getCallClassCode(), "'Call Class' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getChargeOrAnalyzeInEditMode(), checkingAccount.getChargeOrAnalyze(), "'Charge or Analyze' value does not match");

        LOG.info("Step 11: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountDetailsPage().clickMaintenanceTab();
        Pages.accountDetailsPage().clickViewAllMaintenanceHistoryLink();

        LOG.info("Step 12: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        // TODO: Implement verification at Maintenance History page
    }

}
