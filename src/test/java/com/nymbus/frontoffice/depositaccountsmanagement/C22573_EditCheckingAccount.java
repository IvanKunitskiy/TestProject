package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.base.BaseTest;
import com.nymbus.models.account.Account;
import com.nymbus.models.client.Client;
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
        // Set up Client and Account
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");
        checkingAccount = new Account().setCHKAccountData();

        // Login to the system and create a client with checking account
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
        Assert.assertTrue(Pages.editAccountPage().isProductTypeFieldDisabledInEditMode(), "'Product Type' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isProductFieldDisabledInEditMode(), "'Product' field is not disabled");
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

        LOG.info("Step 5: Select data in such dropdown fields that were not available in Add New mode");
        AccountActions.editAccount().setFederalWHReason(checkingAccount);
        AccountActions.editAccount().setReasonATMChargeWaived(checkingAccount);
        AccountActions.editAccount().setOdProtectionAcct(checkingAccount);
        AccountActions.editAccount().setReasonAutoNSFChgWaived(checkingAccount);
        AccountActions.editAccount().setReasonDebitCardChargeWaived(checkingAccount);
        AccountActions.editAccount().setAutomaticOverdraftStatus(checkingAccount);
        AccountActions.editAccount().setReasonAutoOdChgWaived(checkingAccount);
        AccountActions.editAccount().setWhenSurchargesRefunded(checkingAccount);

        LOG.info("Step 6: Fill in such text fields that were not displayed in Add new mode");
        Pages.editAccountPage().setFederalWHPercent(checkingAccount.getFederalWHPercent());
        Pages.editAccountPage().setNumberOfATMCardsIssued(checkingAccount.getNumberOfATMCardsIssued());
        Pages.editAccountPage().setEarningCreditRate(checkingAccount.getEarningCreditRate());
        Pages.editAccountPage().setUserDefinedField_1(checkingAccount.getUserDefinedField_1());
        Pages.editAccountPage().setUserDefinedField_2(checkingAccount.getUserDefinedField_2());
        Pages.editAccountPage().setUserDefinedField_3(checkingAccount.getUserDefinedField_3());
        Pages.editAccountPage().setUserDefinedField_4(checkingAccount.getUserDefinedField_4());
        Pages.editAccountPage().setImageStatementCode(checkingAccount.getImageStatementCode());
        Pages.editAccountPage().setNumberOfDebitCardsIssued(checkingAccount.getNumberOfDebitCardsIssued());
        Pages.editAccountPage().setAutomaticOverdraftLimit(checkingAccount.getAutomaticOverdraftLimit());
        Pages.editAccountPage().setCashCollDaysBeforeChg(checkingAccount.getCashCollDaysBeforeChg());
        Pages.editAccountPage().setCashCollInterestRate(checkingAccount.getCashCollInterestRate());
        Pages.editAccountPage().setCashCollInterestChg(checkingAccount.getCashCollInterestChg());
        Pages.editAccountPage().setCashCollFloat(checkingAccount.getCashCollFloat());
        Pages.editAccountPage().setPositivePay(checkingAccount.getPositivePay());

        LOG.info("Step 7: Select any other value in such fields");
        AccountActions.createAccount().setCurrentOfficer(checkingAccount);
        AccountActions.createAccount().setBankBranch(checkingAccount);
        Pages.editAccountPage().setInterestRate(checkingAccount.getInterestRate());
        AccountActions.createAccount().setCallClassCode(checkingAccount);
        AccountActions.createAccount().setChargeOrAnalyze(checkingAccount);

        LOG.info("Step 8: Submit the account editing by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        LOG.info("Step 9: Pay attention to CHK account fields");
        Pages.accountDetailsPage().clickMoreButton();

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
        Assert.assertEquals(Pages.accountDetailsPage().getImageStatementCode(), checkingAccount.getImageStatementCode(), "'Image Statement Code' value does not match");
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

        LOG.info("Step 10: Click [Edit] button and pay attention to the fields");
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
        Assert.assertEquals(Pages.editAccountPage().getImageStatementCode(), checkingAccount.getImageStatementCode(), "'Image Statement Code' value does not match");
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

        LOG.info("Step 11: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        LOG.info("Step 12: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        // TODO: Implement verification at Maintenance History page
    }

}
