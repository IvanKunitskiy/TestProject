package com.nymbus.frontoffice.safedepositboxmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
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
public class C22614_ViewNewSafeBoxAccountTest extends BaseTest {
    private Account safeDepositBoxAccount;

    @BeforeMethod
    public void preConditions() {
        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up Safe Deposit Box Account
        safeDepositBoxAccount = new Account().setSafeDepositBoxData();
        safeDepositBoxAccount.setCurrentOfficer(Constants.FIRST_NAME + " " + Constants.LAST_NAME);
        safeDepositBoxAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        safeDepositBoxAccount.setDateOpened(WebAdminActions.loginActions().getSystemDate());

        // Set up CHK account (required to point the 'Corresponding Account')
        Account checkingAccount = new Account().setCHKAccountData();

        // Login to the system and create a client with Safe Deposit Box and CHK accounts
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Get safeDepositKeyValues
        Actions.usersActions().openSafeDepositBoxSizesPage();
        List<SafeDepositKeyValues> safeDepositKeyValues = Actions.usersActions().getSafeDepositBoxValues();
        Assert.assertTrue(safeDepositKeyValues.size() > 0, "Safe deposits values are not set!");

        // Set box size and amount
        AccountActions.verifyingAccountDataActions().setSafeDepositBoxSizeAndRentalAmount(safeDepositBoxAccount, safeDepositKeyValues);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create accounts and logout
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkingAccount);
        safeDepositBoxAccount.setCorrespondingAccount(checkingAccount.getAccountNumber());
        Pages.accountDetailsPage().clickAccountsLink();
        AccountActions.createAccount().createSafeDepositBoxAccount(safeDepositBoxAccount);
        Actions.loginActions().doLogOut();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "C22614, View New Safe Box Account")
    public void viewNewSafeBoxAccount() {
        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the Safe Deposit Box Account from the precondition");
        logInfo("Step 3: Open the account");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(safeDepositBoxAccount);

        logInfo("Step 4: Verify the displayed fields in view mode");
        AccountActions.verifyingAccountDataActions().verifyFieldsInViewMode(safeDepositBoxAccount);
    }
}