package com.nymbus.frontoffice.depositaccountsmanagement;

import com.codeborne.selenide.Selenide;
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
public class C22578_EditNewRegularSavingsAccountTest extends BaseTest {

    private IndividualClient client;
    private Account regularSavingsAccount;
    private String clientID;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up Savings account
        regularSavingsAccount = new Account().setSavingsAccountData();
        regularSavingsAccount.setBankBranch("Inspire - Langhorne"); // Branch of the 'autotest autotest' user

        // Login to the system and create a client
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        clientID = Pages.clientDetailsPage().getClientID();

        // Create savings account and logout
        AccountActions.createAccount().createSavingsAccount(regularSavingsAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "С22578, Edit New Regular Savings Account")
    @Severity(SeverityLevel.CRITICAL)
    public void editNewRegularSavingsAccount() {
        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the client from the preconditions and open his profile on Accounts tab");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(clientID);
        Pages.clientDetailsPage().clickAccountsTab();
        Pages.clientDetailsPage().openAccountByNumber(regularSavingsAccount.getAccountNumber());

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
//        Assert.assertTrue(Pages.editAccountPage().isCurrentBalanceDisabledInEditMode(), "'Current Balance' field is not disabled");
//        Assert.assertTrue(Pages.editAccountPage().isAvailableBalanceDisabledInEditMode(), "'Available Balance' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAverageBalanceDisabledInEditMode(), "'Average Balance' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isLowBalanceThisStatementCycleDisabledInEditMode(), "'Low Balance This Statement Cycle' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isBalanceLastStatementDisabledInEditMode(), "'Balance Last Statement' field is not disabled");
//        Assert.assertTrue(Pages.editAccountPage().isDateLastWithdrawalDisabledInEditMode(), "'Date Last Withdrawal' field is not disabled");
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
//        Assert.assertTrue(Pages.editAccountPage().isOnlineBankingLoginDisabledInEditMode(), "'Online Banking login' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isTotalEarningsForLifeOfAccountDisabledInEditMode(), "'Total Earnings for Life of Account' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isTotalContributionsDisabledInEditMode(), "'Total Contributions for Life of Account' field is not disabled");

        logInfo("Step 5: Select data in such drop-down fields that were not available in Add New mode:");
        AccountActions.editAccount().selectValuesInDropdownFieldsThatWereNotAvailableDuringSavingsAccountCreation(regularSavingsAccount);

        logInfo("Step 6: Fill in such text fields that were not displayed in Add new mode:");
        AccountActions.editAccount().fillInInputFieldsThatWereNotAvailableDuringSavingsAccountCreation(regularSavingsAccount);

        logInfo("Step 7: Select any other value in such fields");
        AccountActions.createAccount().setCurrentOfficer(regularSavingsAccount);
        Pages.editAccountPage().setInterestRate(regularSavingsAccount.getInterestRate());
        AccountActions.createAccount().setBankBranch(regularSavingsAccount);
        AccountActions.createAccount().setCallClassCode(regularSavingsAccount);
        AccountActions.createAccount().setStatementCycle(regularSavingsAccount);

        logInfo("Step 8: Set such switchers");
        Pages.editAccountPage().clickNewAccountSwitch();
        Pages.editAccountPage().clickTransactionalAccountSwitch();

        logInfo("Step 9: Submit the account editing by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 10: Pay attention to Savings account fields");
        if (Pages.accountDetailsPage().isMoreButtonVisible()) {
            Pages.accountDetailsPage().clickMoreButton();
        }
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

        logInfo("Step 11: Click [Edit] button and pay attention to the fields");
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

        logInfo("Step 12: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 13: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
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
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Call Class Code") == 1,
                "'Call Class Code' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Corresponding Account") == 1,
                "'Corresponding Account' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Frequency") == 1,
                "'Interest Frequency' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Federal W/H reason") == 1,
                "'Federal W/H reason' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Reason Debit Card Charge Waived") == 1,
                "'Reason Debit Card Charge Waived' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("User Defined Field 1") == 1,
                "'User Defined Field 1' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("User Defined Field 2") == 1,
                "'User Defined Field 2' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("User Defined Field 3") == 1,
                "'User Defined Field 3' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("User Defined Field 4") == 1,
                "'User Defined Field 4' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Federal W/H percent") == 1,
                "'Federal W/H percent' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Number of Debit Cards issued") == 1,
                "'Number of Debit Cards issued' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Print Statement Next Update") == 1,
                "'Print Statement Next Update' row count is incorrect!");
    }
}
