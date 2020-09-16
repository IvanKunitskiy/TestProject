package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
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
public class C22577_AddNewRegularSavingsAccountTest extends BaseTest {

    private IndividualClient client;
    private Account regularSavingsAccount;

    @BeforeMethod
    public void preCondition() {
        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up account
        regularSavingsAccount = new Account().setSavingsAccountData();

        // Set up CHK account (required as Corresponding Account)
        Account checkingAccount = new Account().setCHKAccountData();

        // Login to the system
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set the bank branch of the user to account
        regularSavingsAccount.setBankBranch(Actions.usersActions().getBankBranch());

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create CHK account and logout
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22577, Add New Regular Savings Account")
    @Severity(SeverityLevel.CRITICAL)
    public void addNewRegularSavingsAccount() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the client from the preconditions and open his profile on Accounts tab");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(client.getIndividualType().getClientID());
        Pages.accountNavigationPage().clickAccountsTab();

        logInfo("Step 3: Click 'Add New' drop down and select 'Account'");
        Pages.clientDetailsPage().clickAddNewButton();
        AccountActions.createAccount().setAddNewOption(regularSavingsAccount);

        logInfo("Step 4: Select 'Product Type' = 'Savings Account'");
        AccountActions.createAccount().setProductType(regularSavingsAccount);

        logInfo("Step 5: Select any product not IRA (e.g. Regular Savings Account)");
        AccountActions.createAccount().setProduct(regularSavingsAccount);

        logInfo("Step 6: Look through the fields. Check that fields are prefilled by default");
        Assert.assertEquals(Pages.addAccountPage().getAccountType(), client.getIndividualType().getClientType().getClientType(), "'Account type' is prefilled with wrong value");
        final String accountHolderName = client.getIndividualType().getFirstName() + " " + client.getIndividualType().getLastName() + " (" + client.getIndividualType().getClientID() + ")";
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderName(), accountHolderName, "'Name' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderRelationship(), "Owner", "'Relationship' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderClientType(), client.getIndividualType().getClientType().getClientType(), "'Client type' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderTaxID(), client.getIndividualType().getTaxID(), "'Tax ID' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getDateOpened(), DateTime.getLocalDateTimeByPattern("MM/dd/yyyy"), "'Date' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getOriginatingOfficer(), client.getIndividualClientDetails().getSelectOfficer(), "'Originating officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getCurrentOfficer(), client.getIndividualClientDetails().getSelectOfficer(), "'Current officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getBankBranch(), regularSavingsAccount.getBankBranch(), "'Bank branch' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getMailCode(), client.getIndividualClientDetails().getMailCode().getMailCode(), "'Mail code' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getApplySeasonalAddress().toLowerCase(), "yes", "'Apply Seasonal Address' is prefilled with wrong value");

        logInfo("Step 7: Select any values in drop-down fields");
        logInfo("Step 8: Fill in text fields with valid data. NOTE: do not fill in Account Number field");
        logInfo("Step 9: Select Date Opened as any date < Current Date");
        AccountActions.createAccount().setValuesInFieldsRequiredForSavingsAccount(regularSavingsAccount);

        logInfo("Step 10: Submit the account creation by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 11: Pay attention to the fields that were filled in during account creation");
        if (Pages.accountDetailsPage().isMoreButtonVisible()) {
            Pages.accountDetailsPage().clickMoreButton();
        }
        Assert.assertEquals(Pages.accountDetailsPage().getProductValue(), regularSavingsAccount.getProduct(), "'Product' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCurrentOfficerValue(), regularSavingsAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankBranchValue(), regularSavingsAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getStatementCycle(), regularSavingsAccount.getStatementCycle(), "'Statement Cycle' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCorrespondingAccount(), regularSavingsAccount.getCorrespondingAccount());
        if (regularSavingsAccount.getCallClassCode() != null) {
            Assert.assertEquals(Pages.accountDetailsPage().getCallClassCode(), regularSavingsAccount.getCallClassCode(), "'Call Class' value does not match");
        }
        Assert.assertEquals(Pages.accountDetailsPage().getAccountTitleValue(), regularSavingsAccount.getAccountTitle(), "'Title' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestRateValue(), regularSavingsAccount.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDateOpenedValue(), regularSavingsAccount.getDateOpened(), "'Date Opened' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getMailCodeValue(), regularSavingsAccount.getMailCode(), "'Mail Code' value does not match");

        logInfo("Step 12: Click [Edit] button and pay attention to the fields that were filled in during account creation");
        Pages.accountDetailsPage().clickEditButton();
        Assert.assertEquals(Pages.editAccountPage().getProductValueInEditMode(), regularSavingsAccount.getProduct(), "'Product' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCurrentOfficerValueInEditMode(), regularSavingsAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getBankBranchValueInEditMode(), regularSavingsAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getStatementCycleValueInEditMode(), regularSavingsAccount.getStatementCycle(), "'Statement Cycle' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCorrespondingAccount(), regularSavingsAccount.getCorrespondingAccount(), "'Corresponding Account' value does not match");
        if (regularSavingsAccount.getCallClassCode() != null) {
            Assert.assertEquals(Pages.editAccountPage().getCallClassCodeValueInEditMode(), regularSavingsAccount.getCallClassCode(), "'Call Class' value does not match");
        }
        Assert.assertEquals(Pages.editAccountPage().getMailCode(), regularSavingsAccount.getMailCode(), "'Mail Code' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getAccountTitleValueInEditMode(), regularSavingsAccount.getAccountTitle(), "'Title' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getInterestRateValueInEditMode(), regularSavingsAccount.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getDateOpenedValueInEditMode(), regularSavingsAccount.getDateOpened(), "'Date Opened' value does not match");

        logInfo("Step 13: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 14: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        AccountActions.accountMaintenanceActions().verifySavingsAccountRecords(regularSavingsAccount);
    }
}