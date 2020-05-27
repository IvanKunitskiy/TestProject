package com.nymbus.frontoffice.clientsearch;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.debitcard.DebitCard;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.client.other.DebitCardFactory;
import com.nymbus.newmodels.generation.settings.BinControlFactory;
import com.nymbus.newmodels.settings.bincontrol.BinControl;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

@Epic("Frontoffice")
@Feature("Clients search")
@Owner("Dmytro")
public class C22533_SearchByLastFourOfDebitCardTest extends BaseTest {
    private DebitCard debitCard;

    @BeforeMethod
    public void preCondition() {
        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up CHK account
        Account checkingAccount = new Account().setCHKAccountData();
        checkingAccount.setDateOpened(WebAdminActions.loginActions().getSystemDate());

        // Set up bin control and debit card
        DebitCardFactory debitCardFactory = new DebitCardFactory();
        BinControlFactory binControlFactory = new BinControlFactory();

        BinControl binControl = binControlFactory.getBinControl();
        binControl.setBinNumber(Constants.BIN_NUMBER);
        binControl.setCardDescription("Consumer Debit");

        debitCard = debitCardFactory.getDebitCard();
        debitCard.setBinControl(binControl);
        debitCard.setATMDailyDollarLimit(binControl.getATMDailyDollarLimit());
        debitCard.setATMTransactionLimit(binControl.getATMTransactionLimit());
        debitCard.setDBCDailyDollarLimit(binControl.getDBCDailyDollarLimit());
        debitCard.setDBCTransactionLimit(binControl.getDBCTransactionLimit());
        debitCard.setNameOnCard(client.getNameForDebitCard());

        // Login
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkingAccount);
        debitCard.getAccounts().add(checkingAccount.getAccountNumber());

        // Create debit card
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        Pages.clientDetailsPage().clickOnMaintenanceTab();
        Pages.clientDetailsPage().clickOnNewDebitCardButton();
        Actions.debitCardModalWindowActions().fillDebitCard(debitCard);
        Pages.debitCardModalWindow().clickOnSaveAndFinishButton();
        Pages.debitCardModalWindow().waitForAddNewDebitCardModalWindowInvisibility();

        // Set debit card Number
        debitCard.setCardNumber(Actions.debitCardModalWindowActions().getCardNumber(1));

        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22533, Search by last four of debit card number")
    @Severity(SeverityLevel.CRITICAL)
    public void searchByCardNumber() {

        logInfo("Step 1: Log in to the system as the User from the precondition");
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Pages.navigationPage().waitForUserMenuVisible();

        String cardNumber = debitCard.getCardNumber();
        String lastFourNumbers = cardNumber.substring(cardNumber.length() - 4);
        String hiddenNumber = "XXXX-XXXX-XXXX-" + lastFourNumbers;

        logInfo("Step 2: Click within search field and try to search for an existing Debit Card (by last 4 digits)");
        Pages.clientsSearchPage().typeToClientsSearchInputField(lastFourNumbers);
        Assert.assertTrue(Pages.clientsSearchPage().getAllLookupResults().size() > 0, "There are no relevant lookup results");

        if (Pages.clientsSearchPage().getLookupResultOptionsCount() == 9) {
            Assert.assertTrue(Pages.clientsSearchPage().isLoadMoreResultsButtonVisible(), "'Load more results' button is not visible in search lookup list");
        }

        logInfo("Step 3: Pay attention to the display of the Debit Card in the quick search field");
        List<String> clients = Pages.clientsSearchPage().getAllLookupResults();
        clients.stream()
                .filter(s -> s.contains("XXXX-XXXX-XXXX-"))
                .forEach(s -> Assert.assertEquals(s, hiddenNumber, "Search result is incorrect!"));
    }
}