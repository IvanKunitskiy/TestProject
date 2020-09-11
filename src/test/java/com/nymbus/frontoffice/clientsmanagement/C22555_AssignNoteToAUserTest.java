package com.nymbus.frontoffice.clientsmanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.notes.NotesActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.data.entity.User;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.note.Note;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Clients Management")
@Owner("Petro")
public class C22555_AssignNoteToAUserTest extends BaseTest {

    private User user1;
    private User user2;
    private Note note;
    private String clientID;

    @BeforeMethod
    public void preCondition() {

        // Set up users
        user1 = new User().setDefaultUserData();
        user2 = new User().setDefaultUserData();

        // Set up clients
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up note
        note = new Note().setDefaultNoteData();
        note.setResponsibleOfficer(user2.getFirstName() + " " + user2.getLastName());

        // Create user1 and set him a password
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.usersActions().createUser(user1);
        Actions.loginActions().doLogOut();
        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminUsersActions().setUserPassword(user1);
        WebAdminActions.loginActions().doLogoutProgrammatically();

        // Create a client with a note. Set note's 'Due Date' to current date
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(user1.getLoginID(), user1.getPassword());
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        clientID = Pages.clientDetailsPage().getClientID();
        Pages.accountNavigationPage().clickNotesTab();
        Actions.clientPageActions().closeAllNotifications();
        Pages.notesPage().clickAddNewNoteButton();
        Pages.notesPage().typeToNewNoteTextArea(note.getNewNote());
        Pages.notesPage().setDueDateValue(note.getDueDate());
        Pages.notesPage().clickSaveButton();
        Pages.notesPage().waitForAddNewNoteButton();
        Actions.loginActions().doLogOut();

        // Create user2 and set him a password
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.usersActions().createUser(user2);
        Actions.loginActions().doLogOut();
        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminUsersActions().setUserPassword(user2);
        WebAdminActions.loginActions().doLogoutProgrammatically();
    }

    @Test(description = "C22555, Assign Note To A User Test")
    @Severity(SeverityLevel.CRITICAL)
    public void assignNoteToAUser() {

        logInfo("Step 1: Log in to the system as User1 from the preconditions");
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(user1.getLoginID(), user1.getPassword());

        logInfo("Step 2: Go to Clients and search for the client from the precondition");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(clientID);

        logInfo("Step 3: Open Client's Profile on Notes tab");
        Pages.accountNavigationPage().clickNotesTab();

        logInfo("Step 4: Click the note from preconditions and click [Edit] button");
        Pages.notesPage().clickNoteByName(note.getNewNote());
        Pages.notesPage().clickEditButton();

        logInfo("Step 5: Remove the responsible officer (if any) and select User2 from the preconditions in that field and save changes");
        if (Pages.notesPage().isResponsibleOfficerSelected()) {
            Pages.notesPage().clickClearResponsibleOfficerIcon();
        }
        Pages.notesPage().waitResponsibleOfficerInput();
        NotesActions.editActions().setResponsibleOfficer(note);
        Pages.notesPage().clickSaveButton();

        logInfo("Step 6: Perform log out from the users sub-menu in the header at the top-right corner of the screen");
        Actions.loginActions().doLogOut();

        logInfo("Step 7: Log in to the system as User2 from the preconditions");
        Actions.loginActions().doLogin(user2.getLoginID(), user2.getPassword());

        logInfo("Step 8: Click the Bell alert icon in the top right corner on header");
        SelenideTools.sleep(Constants.SMALL_TIMEOUT);
        Assert.assertTrue(Pages.navigationPage().isNotificationCircleVisible(), "'Notification circle' is not visible");
        Pages.navigationPage().clickAlertNotificationsButton();
        Pages.alerts().waitForAlertsSidePanelVisible();
        Assert.assertTrue(Pages.alerts().isNotificationCircleVisible(), "'Notification circle' is not visible");
        Pages.alerts().clickAlertByNoteText(note.getNewNote());
        Assert.assertEquals(Pages.notesPage().getDueDateValueInViewMode(), note.getDueDate(), "'Due date' is not equal to 'Current date'");
    }
}