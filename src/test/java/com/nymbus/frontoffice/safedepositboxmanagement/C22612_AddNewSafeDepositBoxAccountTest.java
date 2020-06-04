package com.nymbus.frontoffice.safedepositboxmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.Generator;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.verifyingmodels.SafeDepositKeyValues;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;


@Epic("Frontoffice")
@Feature("Box Accounts Management")
@Owner("Dmytro")
public class C22612_AddNewSafeDepositBoxAccountTest extends BaseTest {

    private IndividualClient client;
    private Account safeDepositBoxAccount;
    private String clientID;

    @BeforeMethod
    public void preConditions() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up Safe Deposit Box Account
        safeDepositBoxAccount = new Account().setSafeDepositBoxData();
        safeDepositBoxAccount.setCurrentOfficer(Constants.FIRST_NAME + " " + Constants.LAST_NAME);

        // Login to the system and create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Get safeDepositKeyValues
        Actions.usersActions().openSafeDepositBoxSizesPage();
        List<SafeDepositKeyValues> safeDepositKeyValues = Actions.usersActions().getSafeDepositBoxValues();
        Assert.assertTrue(safeDepositKeyValues.size() > 0, "Safe deposits values are not set!");

        // Set box size and amount
        setSafeDepositBoxSizeAndRentalAmount(safeDepositBoxAccount, safeDepositKeyValues);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        clientID = Pages.clientDetailsPage().getClientID();

        Actions.loginActions().doLogOut();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "C22612, Add New 'Safe Deposit Box' Account")
    public void createSafeBoxAccount() {
        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Clients screen and search for client from preconditions");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(clientID);
        Pages.clientDetailsPage().clickAccountsTab();
        Actions.clientPageActions().closeAllNotifications();

        logInfo("Step 3: Click 'Add New' drop down and select 'Account'");
        Pages.clientDetailsPage().clickAddNewButton();
        AccountActions.createAccount().setAddNewOption(safeDepositBoxAccount);

        logInfo("Step 4: Select 'Product Type' = 'Safe Deposit Box'");
        AccountActions.createAccount().setProductType(safeDepositBoxAccount);

        logInfo("Step 6: Select any value from Box Size drop-down and check the Rental Amount field");
        AccountActions.createAccount().setBoxSize(safeDepositBoxAccount);
        Assert.assertEquals(Pages.addAccountPage().getRentalAmount(), safeDepositBoxAccount.getRentalAmount(),
                "'Rental Amount' is prefilled with wrong value");

        logInfo("Step 7: Select values in such drop-down fields:");
        AccountActions.createAccount().selectValuesInDropdownFieldsRequiredForSafeDepositBoxAccount(safeDepositBoxAccount);

        logInfo("Step 8: Fill in such text fields with valid data (except Account Number field):");
        AccountActions.createAccount().fillInInputFieldsRequiredForSafeDepositBoxAccount(safeDepositBoxAccount);

        logInfo("Step 9: Select Date Opened as any date < Current Date and Select Date next IRA distribution as any date > Current Date");
        Pages.addAccountPage().setDateOpenedValue(WebAdminActions.loginActions().getSystemDate());

        logInfo("Step 5: Look through the fields. Check that fields are prefilled by default");
        Assert.assertEquals(Pages.addAccountPage().getAccountType(), client.getIndividualType().getClientType().getClientType(), "'Account type' is prefilled with wrong value");
        final String accountHolderName = client.getIndividualType().getFirstName() + " " + client.getIndividualType().getLastName() + " (" + clientID + ")";
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderName(), accountHolderName, "'Name' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderRelationship(), safeDepositBoxAccount.getAccountHolder(), "'Relationship' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderClientType(), client.getIndividualType().getClientType().getClientType(), "'Client type' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderTaxID(), client.getIndividualType().getTaxID(), "'Tax ID' is prefilled with wrong value");
       /* Assert.assertEquals(Pages.addAccountPage().getMailCode(), client.getIndividualClientDetails().getMailCode().getMailCode(), "'Mail code' is prefilled with wrong value");*/
        Assert.assertEquals(Pages.addAccountPage().getDateOpened(), WebAdminActions.loginActions().getSystemDate(), "'Date' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getOriginatingOfficer(), client.getIndividualClientDetails().getSelectOfficer(), "'Originating officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getCurrentOfficer(), client.getIndividualClientDetails().getSelectOfficer(), "'Current officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getBankBranch(), safeDepositBoxAccount.getBankBranch(), "'Bank branch' is prefilled with wrong value");

        logInfo("Step 10: Submit the account creation by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 11: Pay attention to the fields that were filled in during account creation");
        Assert.assertEquals(Pages.accountDetailsPage().getBoxSizeValue(), safeDepositBoxAccount.getBoxSize(), "'Box Size' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getRentalAmount(), safeDepositBoxAccount.getRentalAmount(), "'Rental Amount' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCurrentOfficerValue(), safeDepositBoxAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankBranchValue(), safeDepositBoxAccount.getBankBranch(), "'Bank Branch' value does not match");
        /*Assert.assertTrue(Pages.accountDetailsPage().getCorrespondingAccount().contains(safeDepositBoxAccount.getCorrespondingAccount()), "'Corresponding Account' value does not match"); // TODO change assertion type*/
        /*Assert.assertEquals(Pages.accountDetailsPage().getDiscountReason(), safeDepositBoxAccount.getDiscountReason(), "'Discount Reason' value does not match");*/
        Assert.assertEquals(Pages.accountDetailsPage().getAccountTitleValue(), safeDepositBoxAccount.getAccountTitle(), "'Title' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_1(), safeDepositBoxAccount.getUserDefinedField_1(), "'User defined field 1' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_2(), safeDepositBoxAccount.getUserDefinedField_2(), "'User defined field 2' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_3(), safeDepositBoxAccount.getUserDefinedField_3(), "'User defined field 3' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_4(), safeDepositBoxAccount.getUserDefinedField_4(), "'User defined field 4' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDiscountPeriods(), safeDepositBoxAccount.getDiscountPeriods(), "'Discount Periods' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDateOpenedValue(), WebAdminActions.loginActions().getSystemDate(), "'Date Opened' value does not match");

        logInfo("Step 12: Check the 'Next Billing Date' field value");
        Assert.assertEquals(Pages.accountDetailsPage().getBillingNextDate(), WebAdminActions.loginActions().getSystemDate(), "'Date Opened' value does not match");

        logInfo("Step 13: Click [Edit] button and pay attention to the fields that were filled in during account creation");
        Pages.accountDetailsPage().clickEditButton();
        Assert.assertEquals(Pages.editAccountPage().getBoxSize(), safeDepositBoxAccount.getBoxSize(), "'Box Size' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getRentalAmount(), safeDepositBoxAccount.getRentalAmount(), "'Rental Amount' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCurrentOfficerValueInEditMode(), safeDepositBoxAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getBankBranchValueInEditMode(), safeDepositBoxAccount.getBankBranch(), "'Bank Branch' value does not match");
        /*Assert.assertTrue(Pages.editAccountPage().getCorrespondingAccount().contains(safeDepositBoxAccount.getCorrespondingAccount()), "'Corresponding Account' value does not match"); // TODO change assertion type*/
       /* Assert.assertEquals(Pages.editAccountPage().getDiscountReason(), safeDepositBoxAccount.getDiscountReason(), "'Discount Reason' value does not match");*/
        Assert.assertEquals(Pages.editAccountPage().getAccountTitleValueInEditMode(), safeDepositBoxAccount.getAccountTitle(), "'Title' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField1(), safeDepositBoxAccount.getUserDefinedField_1(), "'User defined field 1' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField2(), safeDepositBoxAccount.getUserDefinedField_2(), "'User defined field 2' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField3(), safeDepositBoxAccount.getUserDefinedField_3(), "'User defined field 3' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField4(), safeDepositBoxAccount.getUserDefinedField_4(), "'User defined field 4' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getDiscountPeriods(), safeDepositBoxAccount.getDiscountPeriods(), "'Discount Periods' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getDateOpenedValueInEditMode(), WebAdminActions.loginActions().getSystemDate(), "'Date Opened' value does not match");

        logInfo("Step 14: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();
        AccountActions.accountMaintenanceActions().expandAllRows();

        logInfo("Step 15: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Rental Amount") > 0,
                "Account creation records with 'Rental Amount' aren't present in table!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Discount Reason") > 0,
                "Account creation records with 'Discount Reason' aren't present in table!");
    }


    private void setSafeDepositBoxSizeAndRentalAmount(Account safeDepositBoxAccount, List<SafeDepositKeyValues> safeDepositKeyValues) {
        int randomIndex = Generator.genInt(0, safeDepositKeyValues.size() - 1);
        safeDepositBoxAccount.setBoxSize(safeDepositKeyValues.get(randomIndex).getSafeBoxSize());
        safeDepositBoxAccount.setRentalAmount(String.format("%.2f", safeDepositKeyValues.get(randomIndex).getSafeBoxRentalAmount()));
    }
}