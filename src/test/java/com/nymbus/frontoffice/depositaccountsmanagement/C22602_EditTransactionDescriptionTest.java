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
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22602_EditTransactionDescriptionTest extends BaseTest {

    private IndividualClient client;
    private Account chkAccount;
    private Transaction transaction;

    @BeforeMethod
    public void preCondition() {

        // Set up a client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up account
        chkAccount = new Account().setCHKAccountData();

        // Set up transaction
        transaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        transaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());

        // Create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK account
        AccountActions.createAccount().createCHKAccount(chkAccount);
        Actions.transactionActions().performGLDebitMiscCreditTransaction(transaction);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22602, Edit Transaction Description")
    @Severity(SeverityLevel.CRITICAL)
    public void editTransactionDescription() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for account from the precondition and open it on Notes tab");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccount);
        Pages.accountNavigationPage().clickTransactionsTab();

        logInfo("Step 3: Click on [3 dots menu icon] next to any transaction record and click [Edit button]");
        Pages.transactionsPage().clickThreeDotsIconForTransactionByNumberInList(1);
        Pages.transactionsPage().clickEditOption();

        logInfo("Step 4: Fill in Description field with any data and click [Save Changes] button");
        Pages.editAccountTransactionModal().editDescriptionField();
        Pages.editAccountTransactionModal().clickTheSaveChangesButton();
        // TODO: retest after switching back to dev6 env
    }
}
