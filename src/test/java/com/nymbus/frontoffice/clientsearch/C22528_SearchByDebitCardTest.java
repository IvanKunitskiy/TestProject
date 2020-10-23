package com.nymbus.frontoffice.clientsearch;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.debitcard.DebitCard;
import com.nymbus.newmodels.generation.bincontrol.BinControlConstructor;
import com.nymbus.newmodels.generation.bincontrol.builder.BinControlBuilder;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.debitcard.DebitCardConstructor;
import com.nymbus.newmodels.generation.debitcard.builder.DebitCardBuilder;
import com.nymbus.newmodels.settings.bincontrol.BinControl;
import com.nymbus.pages.Pages;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Epic("Frontoffice")
@Feature("Clients search")
@Owner("Dmytro")
public class C22528_SearchByDebitCardTest extends BaseTest {
    private DebitCard debitCard;

    @BeforeMethod
    public void preCondition() {
        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up CHK account
        Account checkingAccount = new Account().setCHKAccountData();

        // Set up bin control and debit card
        DebitCardConstructor debitCardConstructor = new DebitCardConstructor();
        DebitCardBuilder debitCardBuilder = new DebitCardBuilder();
        debitCardConstructor.constructDebitCard(debitCardBuilder);
        debitCard = debitCardBuilder.getCard();

        BinControlConstructor binControlConstructor = new BinControlConstructor();
        BinControlBuilder binControlBuilder = new BinControlBuilder();
        binControlConstructor.constructBinControl(binControlBuilder);
        BinControl binControl = binControlBuilder.getBinControl();

        binControl.setBinNumber(Constants.BIN_NUMBER);
        binControl.setCardDescription("Consumer Debit");

        debitCard.setBinControl(binControl);
        debitCard.setATMDailyDollarLimit(binControl.getATMDailyDollarLimit());
        debitCard.setATMTransactionLimit(binControl.getATMTransactionLimit());
        debitCard.setDBCDailyDollarLimit(binControl.getDBCDailyDollarLimit());
        debitCard.setDBCTransactionLimit(binControl.getDBCTransactionLimit());
        debitCard.setNameOnCard(client.getNameForDebitCard());

        // Login
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        //Set product
        checkingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

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

    @Severity(CRITICAL)
    @Test(description = "C22528, Search individualClient by debitcard number")
    public void searchByCardNumber() {
        String lastFourNumbers = debitCard.getCardNumber().substring(debitCard.getCardNumber().length() - 4);
        String hiddenNumber = "XXXX-XXXX-XXXX-" + lastFourNumbers;

        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Pages.navigationPage().waitForUserMenuVisible();

        logInfo("Step 2: Click within search field and try to search for an existing Debit Card");
        Pages.clientsSearchPage().typeToClientsSearchInputField(lastFourNumbers);

        int lookupResultsCount = Pages.clientsSearchPage().getLookupResultOptionsCount();
        Assert.assertTrue(lookupResultsCount > 0);
        if (lookupResultsCount == 9) {
            assertTrue(Pages.clientsSearchPage().isLoadMoreResultsButtonVisible(), "Load more button is not visible!");
        }

        logInfo("Step 3: Pay attention to the display of the Debit Card in the quick search field");
        List<String> clients = Pages.clientsSearchPage().getAllLookupResults();
        clients.stream()
                .filter(s -> s.contains("XXXX-XXXX-XXXX-"))
                .forEach(s -> Assert.assertEquals(s, hiddenNumber, "Search result is incorrect!"));

        logInfo("Step 4: Clear the data from the field and try to search for an existing client");
        Pages.clientsSearchPage().clickOnSearchInputFieldClearButton();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Pages.clientsSearchPage().typeToClientsSearchInputField(debitCard.getCardNumber());

        clients = Pages.clientsSearchPage().getAllLookupResults();
        assertEquals(clients.get(0), hiddenNumber, "Search result is incorrect!");
    }
}