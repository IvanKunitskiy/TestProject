package com.nymbus.frontoffice.depositaccountsmanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.notes.NotesActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.data.entity.User;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.note.Note;
import com.nymbus.pages.Pages;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Petro")
public class C22606_AssignAccountLevelNotesToUserTest extends BaseTest {

    private User user;
    private Account chkAccount;
    private Note note1;
    private Note note2;
    private Note note3;

    @BeforeMethod
    public void preCondition() {

        // Set up a user
        user = new User().setDefaultUserData();

        // Set up a client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up notes
        note1 = new Note().setDefaultNoteData();
        note1.setResponsibleOfficer(user.getFirstName() + " " + user.getLastName());

        note2 = new Note().setDefaultNoteData();
        note2.setResponsibleOfficer(Constants.FIRST_NAME + " " + Constants.LAST_NAME);

        note3 = new Note().setDefaultNoteData();
        note3.setResponsibleOfficer(user.getFirstName() + " " + user.getLastName());
        note3.setDueDate(DateTime.getDateTodayPlusDaysWithFormat(2,"MM/dd/yyyy"));

        // Set up account
        chkAccount = new Account().setCHKAccountData();

        // Create user and set him a password
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.usersActions().createUser(user);
        Actions.loginActions().doLogOut();
        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        WebAdminActions.webAdminUsersActions().setUserPassword(user);
        WebAdminActions.loginActions().doLogout();

        // Create a client
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());

        // Set the product
        chkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK account
        AccountActions.createAccount().createCHKAccount(chkAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22606, Assign account level notes to user")
    public void assignAccountLevelNotesToUser() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());

        logInfo("Step 2: Search for account from the precondition and open it on Notes tab");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccount);
        Pages.accountNavigationPage().clickNotesTab();

        logInfo("Step 3: Click [Add new note] button");
        logInfo("Step 4: Fill in all fields with any valid data:\n" +
                "- select Responsible Officer - current user\n" +
                "- Severity - any\n" +
                "- fill in the Note text field\n" +
                "- Due Date = current date\n" +
                "- Expiration Date - any, e.g. tomorrow\n" +
                "and click [Save] button");
        NotesActions.notePageActions().createNote(note1);
        Assert.assertTrue(Pages.notesPage().isNotePresentInList(note1.getNewNote()), "Created note is not visible in the list");

        logInfo("Step 5: Click [Add New Note] button again and create one more Account Note.\n" +
                "Due Date = current date BUT Responsible Officer NOT the current User");
        NotesActions.notePageActions().createNote(note2);
        Assert.assertTrue(Pages.notesPage().isNotePresentInList(note2.getNewNote()), "Created note is not visible in the list");

        logInfo("Step 6: Click [Add New Note] button again and create one more Account Note.\n" +
                        "Responsible Officer - current user BUT Due Date = future date\n");
        NotesActions.notePageActions().createNote(note3);
        Assert.assertTrue(Pages.notesPage().isNotePresentInList(note3.getNewNote()), "Created note is not visible in the list");

        logInfo("Step 7: Click bell icon in the top right of the header and pay attention to the Alerts panel");
        Pages.navigationPage().waitNotificationCircleVisible();
        Pages.navigationPage().clickAlertNotificationsButton();
        Pages.alerts().waitForAlertsSidePanelVisible();
        Assert.assertTrue(Pages.alerts().isNoteAlertVisible(chkAccount.getAccountNumber() + " - CHK Account", note1.getNewNote()));
        Assert.assertFalse(Pages.alerts().isNoteVisibleInTheListByText(note2.getNewNote()), "Created note is visible in the list");
        Assert.assertFalse(Pages.alerts().isNoteVisibleInTheListByText(note3.getNewNote()), "Created note is visible in the list");
        Pages.alerts().clickCloseButton();

        logInfo("Step 8: Log out from the system");
        Actions.loginActions().doLogOut();

        logInfo("Step 9: Log in to the system as the User specified as Responsible Officer on Step 5");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 10: Click bell icon in the top right of the header and pay attention to the Alerts panel");
        Pages.navigationPage().clickAlertNotificationsButton();
        Pages.alerts().waitForAlertsSidePanelVisible();
        Assert.assertTrue(Pages.alerts().isNoteAlertVisible(chkAccount.getAccountNumber() + " - CHK Account", note2.getNewNote()));

        logInfo("Step 11: Click on the created account note record in the alert panel");
        Pages.alerts().clickAlertByNoteText(note2.getNewNote());
        Assert.assertTrue(Pages.notesPage().isNotePresentInList(note2.getNewNote()), "Created note is not visible in the list");
    }
}
