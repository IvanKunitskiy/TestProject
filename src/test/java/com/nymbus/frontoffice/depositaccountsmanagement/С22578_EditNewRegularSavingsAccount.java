package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.models.account.Account;
import com.nymbus.models.client.Client;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class С22578_EditNewRegularSavingsAccount extends BaseTest {

    Client client;
    Account regularSavingsAccount;

    @BeforeMethod
    public void preCondition() {
        // Set up Client
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");
        client.setSelectOfficer("autotest autotest"); // Controlling officer to validate 'Bank Branch' value

        // Set up Savings account
        regularSavingsAccount = new Account().setSavingsAccountData();
        regularSavingsAccount.setBankBranch("Inspire - Langhorne"); // Branch of the 'autotest autotest' user

        // Login to the system and create a client
//        navigateToUrl(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        client.setClientID(Pages.clientDetailsPage().getClientID());
        AccountActions.createAccount().createSavingsAccount(regularSavingsAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "С22578, Edit New Regular Savings Account")
    @Severity(SeverityLevel.CRITICAL)
    public void editNewRegularSavingsAccount() {

//        LOG.info("Step 1: Log in to the system as the user from the precondition");
//        navigateToUrl(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

//        LOG.info("Step 2: Search for the client from the preconditions and open his profile on Accounts tab");
        Pages.clientsPage().typeToClientsSearchInputField(regularSavingsAccount.getAccountNumber());
        Assert.assertTrue(Pages.clientsPage().getAllLookupResults().size() == 1, "There is more than one client found");
        Assert.assertTrue(Pages.clientsPage().isSearchResultsRelative(Pages.clientsPage().getAllLookupResults(), regularSavingsAccount.getAccountNumber()), "Search results are not relevant");
        Pages.clientsPage().clickOnSearchButton();
        Pages.clientsSearchResultsPage().clickTheExactlyMatchedClientInSearchResults();
        Pages.clientDetailsPage().waitForPageLoaded();
        Pages.clientDetailsPage().clickAccountsTab();
        Pages.clientDetailsPage().openAccountByNumber(regularSavingsAccount.getAccountNumber());

//        LOG.info("Step 3: Click [Edit] button");
        Pages.accountDetailsPage().clickEditButton();

//        LOG.info("Step 4: Look at the fields and verify that such fields are disabled for editing");
        Assert.assertTrue(Pages.editAccountPage().isProductTypeFieldDisabledInEditMode(), "'Product Type' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isProductFieldDisabledInEditMode(), "'Product' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccountNumberFieldDisabledInEditMode(), "'Account Number' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccountTypeFieldDisabledInEditMode(), "'Account Type' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isOriginatingOfficerFieldDisabledInEditMode(), "'Originating Officer' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccountStatusFieldDisabledInEditMode(), "'Account Status' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateOpenedFieldDisabledInEditMode(), "'Date Opened' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateClosedFieldDisabledInEditMode(), "'Date Closed field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isCurrentBalanceDisabledInEditMode(), "'Current Balance' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAvailableBalanceDisabledInEditMode(), "'Available Balance' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAverageBalanceDisabledInEditMode(), "'Average Balance' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isLowBalanceThisStatementCycleDisabledInEditMode(), "'Low Balance This Statement Cycle' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isBalanceLastStatementDisabledInEditMode(), "'Balance Last Statement' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateLastWithdrawalDisabledInEditMode(), "'Date Last Withdrawal' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateLastDepositDisabledInEditMode(), "'Date Last Deposit' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateLastStatementDisabledInEditMode(), "'Date Last Statement' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isNumberOfWithdrawalsThisStatementCycleDisabledInEditMode(), "'# Of Withdrawals This Statement Cycle' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isNumberOfDepositsThisStatementCycleDisabledInEditMode(), "'Number Of Deposits This Statement Cycle' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccruedInterestThisStatementCycleDisabledInEditMode(), "'Accrued Interest this statement cycle' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAmountInterestLastPaidDisabledInEditMode(), "'Interest Last paid' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isLastWithdrawalAmountDisabledInEditMode(), "'Last withdrawal amount' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isLastDepositAmountDisabledInEditMode(), "'Last Deposit Amount' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isPreviousStatementBalanceDisabledInEditMode(), "'Previous Statement Balance' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isPreviousStatementDateDisabledInEditMode(), "'Previous Statement Date' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isServiceChargesYTDDisabledInEditMode(), "'Service charges YTD' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAggregateBalanceYTDDisabledInEditMode(), "'Aggregate Balance Year to date' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isSpecialMailingInstructionsDisabledInEditMode(), "'Special Mailing Instructions' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isTaxesWithheldYTDDisabledInEditMode(), "'Taxes Withheld YTD' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isChargesWaivedYTDDisabledInEditMode(), "'YTD charges waived' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isNumberRegDItemsDisabledInEditMode(), "'Number Reg D items (6)' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isMonthlyLowBalanceDisabledInEditMode(), "'Monthly low balance' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isMonthlyNumberOfWithdrawalsDisabledInEditMode(), "'Monthly number of withdrawals' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isInterestPaidLastYearDisabledInEditMode(), "'Interest Paid Last Year' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isOneDayFloatDisabledInEditMode(), "'1 day float' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isTwoDayFloatDisabledInEditMode(), "'2 day float' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isThreeDayFloatDisabledInEditMode(), "'3 day float' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isFourDayFloatDisabledInEditMode(), "'4 day float' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isFiveDayFloatDisabledInEditMode(), "'5 day float' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAggregateColBalDisabledInEditMode(), "'Aggregate col bal' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAggrColLstStmtDisabledInEditMode(), "'Aggr col lst stmt' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isYtdAggrColBalDisabledInEditMode(), "'YTD aggr col bal' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAggrOdBalanceDisabledInEditMode(), "'Aggr OD balance' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAggrOdLstStmtDisabledInEditMode(), "'Aggr OD lst stmt' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAggrColOdBalDisabledInEditMode(), "'Aggr col OD bal' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAggrColOdLstStmtDisabledInEditMode(), "'Aggr col OD lst stmt' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isOnlineBankingLoginDisabledInEditMode(), "'Online Banking login' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isTotalEarningsForLifeOfAccountDisabledInEditMode(), "'Total Earnings for Life of Account' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isTotalContributionsDisabledInEditMode(), "'Total Contributions for Life of Account' field is not disabled");

//        LOG.info("Step 5: Select data in such drop-down fields that were not available in Add New mode:");
        AccountActions.editAccount().setFederalWHReason(regularSavingsAccount);
        AccountActions.editAccount().setReasonATMChargeWaived(regularSavingsAccount);
        AccountActions.editAccount().setReasonDebitCardChargeWaived(regularSavingsAccount);

//        LOG.info("Step 6: Fill in such text fields that were not displayed in Add new mode:");
        Pages.editAccountPage().setPrintStatementNextUpdate(regularSavingsAccount.getPrintStatementNextUpdate());
        Pages.editAccountPage().setInterestPaidYTD(regularSavingsAccount.getInterestPaidYTD());
        Pages.editAccountPage().setFederalWHPercent(regularSavingsAccount.getFederalWHPercent());
        Pages.editAccountPage().setNumberOfATMCardsIssued(regularSavingsAccount.getNumberOfATMCardsIssued());
        Pages.editAccountPage().setUserDefinedField_1(regularSavingsAccount.getUserDefinedField_1());
        Pages.editAccountPage().setUserDefinedField_2(regularSavingsAccount.getUserDefinedField_2());
        Pages.editAccountPage().setUserDefinedField_3(regularSavingsAccount.getUserDefinedField_3());
        Pages.editAccountPage().setUserDefinedField_4(regularSavingsAccount.getUserDefinedField_4());
        Pages.editAccountPage().setNumberOfDebitCardsIssued(regularSavingsAccount.getNumberOfDebitCardsIssued());

//        LOG.info("Step 7: Select any other value in such fields");
        AccountActions.createAccount().setCurrentOfficer(regularSavingsAccount);
        Pages.editAccountPage().setInterestRate(regularSavingsAccount.getInterestRate());
        AccountActions.createAccount().setBankBranch(regularSavingsAccount);
        AccountActions.createAccount().setCallClassCode(regularSavingsAccount);
        AccountActions.createAccount().setStatementCycle(regularSavingsAccount);

//        LOG.info("Step 8: Set such switchers");
        Pages.editAccountPage().clickNewAccountSwitch();
        Pages.editAccountPage().clickTransactionalAccountSwitch();

//        LOG.info("Step 9: Submit the account editing by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

//        LOG.info("Step 10: Pay attention to Savings account fields");
        Pages.accountDetailsPage().clickMoreButton();
        Assert.assertEquals(Pages.accountDetailsPage().getFederalWHReason(), regularSavingsAccount.getFederalWHReason(), "'Federal WH Reason' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getReasonATMChargeWaived(), regularSavingsAccount.getReasonATMChargeWaived(), "'Reason ATM Charge Waived' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getReasonDebitCardChargeWaived(), regularSavingsAccount.getReasonDebitCardChargeWaived(), "'Reason Debit Card Charge Waived' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getPrintStatementNextUpdate(), regularSavingsAccount.getPrintStatementNextUpdate(), "'Print Statement Next Update' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestPaidYTD(), regularSavingsAccount.getInterestPaidYTD(), "'Interest Paid Year to date' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getFederalWHPercent(), regularSavingsAccount.getFederalWHPercent(), "'Federal WH Percent' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getNumberOfATMCardsIssued(), regularSavingsAccount.getNumberOfATMCardsIssued(), "'Number Of ATM Cards Issued' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_1(), regularSavingsAccount.getUserDefinedField_1(), "'User Defined Field 1' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_2(), regularSavingsAccount.getUserDefinedField_2(), "'User Defined Field 2' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_3(), regularSavingsAccount.getUserDefinedField_3(), "'User Defined Field 3' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_4(), regularSavingsAccount.getUserDefinedField_4(), "'User Defined Field 4' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getNumberOfDebitCardsIssued(), regularSavingsAccount.getNumberOfDebitCardsIssued(), "'Number Of Debit Cards Issued' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCurrentOfficerValue(), regularSavingsAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestRateValue(), regularSavingsAccount.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankBranchValue(), regularSavingsAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCallClassCode(), regularSavingsAccount.getCallClassCode(), "'Call Class' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getStatementCycle(), regularSavingsAccount.getStatementCycle(), "'Statement Cycle' value does not match");

//        LOG.info("Step 11: Click [Edit] button and pay attention to the fields");
        Pages.accountDetailsPage().clickEditButton();
        Assert.assertEquals(Pages.editAccountPage().getFederalWHReasonInEditMode(), regularSavingsAccount.getFederalWHReason(), "'Federal WH Reason' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getReasonATMChargeWaived(), regularSavingsAccount.getReasonATMChargeWaived(), "'Reason ATM Charge Waived' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getReasonDebitCardChargeWaived(), regularSavingsAccount.getReasonDebitCardChargeWaived(), "'Reason Debit Card Charge Waived' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getPrintStatementNextUpdate(), regularSavingsAccount.getPrintStatementNextUpdate(), "'Print Statement Next Update' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getInterestPaidYTD(), regularSavingsAccount.getInterestPaidYTD(), "'Interest Paid Year to date' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getFederalWHPercent(), regularSavingsAccount.getFederalWHPercent(), "'Federal WH Percent' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getNumberOfATMCardsIssued(), regularSavingsAccount.getNumberOfATMCardsIssued(), "'Number Of ATM Cards Issued' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField1(), regularSavingsAccount.getUserDefinedField_1(), "'User Defined Field 1' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField2(), regularSavingsAccount.getUserDefinedField_2(), "'User Defined Field 2' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField3(), regularSavingsAccount.getUserDefinedField_3(), "'User Defined Field 3' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField4(), regularSavingsAccount.getUserDefinedField_4(), "'User Defined Field 4' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getNumberOfDebitCardsIssued(), regularSavingsAccount.getNumberOfDebitCardsIssued(), "'Number Of Debit Cards Issued' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCurrentOfficerValueInEditMode(), regularSavingsAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getInterestRateValueInEditMode(), regularSavingsAccount.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getBankBranchValueInEditMode(), regularSavingsAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCallClassCodeValueInEditMode(), regularSavingsAccount.getCallClassCode(), "'Call Class' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getStatementCycleValueInEditMode(), regularSavingsAccount.getStatementCycle(), "'Statement Cycle' value does not match");

//        LOG.info("Step 12: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

//        LOG.info("Step 13: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        // TODO: Implement verification at Maintenance History page
    }
}
