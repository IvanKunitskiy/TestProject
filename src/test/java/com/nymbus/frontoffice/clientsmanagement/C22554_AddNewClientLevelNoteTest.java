package com.nymbus.frontoffice.clientsmanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.notes.NotesActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.data.entity.User;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.note.Note;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Clients Management")
@Owner("Petro")
public class C22554_AddNewClientLevelNoteTest extends BaseTest {

    private User user;
    private Note note;
    private Client client;

    @BeforeMethod
    public void preCondition() {
        user = new User().setDefaultUserData();
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");
        note = new Note().setDefaultNoteData();
        note.setResponsibleOfficer(user.getFirstName() + " " + user.getLastName());

        // Create user and set him a password
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.usersActions().createUser(user);
        Actions.loginActions().doLogOut();
        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminUsersActions().setUserPassword(user);
        WebAdminActions.loginActions().doLogout();

        // Create a client with a note. Set note's 'Due Date' to current date
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());
        ClientsActions.createClient().createClient(client);
        client.setClientID(Pages.clientDetailsPage().getClientID());
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22554, Add New Client Level Note Test")
    @Severity(SeverityLevel.CRITICAL)
    public void addNewClientLevelNoteTest() {

        logInfo("Step 1: Log in to the system as User1 from the preconditions");
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());

        logInfo("Step 2: Go to Clients and search for the client from the precondition");
        Actions.clientPageActions().searchAndOpenClientByID(client);

        logInfo("Step 3: Open Client's Profile on Notes tab");
        Pages.accountNavigationPage().clickNotesTab();

        logInfo("Step 4: Click [Add New Note] button");
        Pages.notesPage().clickAddNewNoteButton();

        logInfo("Step 5: Select current user as the 'Responsible Officer', Severity, any Due Date and click [Save] button and refresh the page");
        NotesActions.editActions().selectResponsibleOfficer(note);
        Pages.notesPage().setDueDateValue(note.getDueDate());
        NotesActions.editActions().setSeverity(note);
        Pages.notesPage().typeToNewNoteTextArea(note.getNewNote());
        Pages.notesPage().clickSaveButton();

        logInfo("Step 6: Wait about 10 seconds and pay attention to the Bell alert icon in the top right corner on the header" +
                "Click on it and check that newly created note is displayed in the list of notes");
        Pages.navigationPage().waitNotificationCircleVisible();
        Assert.assertTrue(Pages.navigationPage().isNotificationCircleVisible(), "Notification circle not appeared");
        Pages.navigationPage().clickAlertNotificationsButton();
        Pages.alerts().waitForAlertsSidePanelVisible();
        Assert.assertTrue(Pages.alerts().isNotificationCircleVisible(), "'Notification circle' is not visible");
        Assert.assertTrue(Pages.alerts().isNoteVisibleInTheListByText(note.getNewNote()), "Note is not in the list");
        Pages.alerts().clickCloseButton();

        logInfo("Step 7: Open Clients profile on Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 8: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        // TODO: Implement verification at Maintenance History page
    }
}
