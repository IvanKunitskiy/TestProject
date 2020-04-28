package com.nymbus.frontoffice.depositaccountsmanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.note.Note;
import com.nymbus.pages.Pages;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.annotations.BeforeMethod;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Petro")
public class C22607_EditDeleteAccountLevelNotesTest extends BaseTest {

    private Client client;
    private Account chkAccount;
    private Note note;

    @BeforeMethod
    public void preCondition() {
        // possibly replace with new solution
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");

        note = new Note().setDefaultNoteData();

        // Set up account
        chkAccount = new Account().setCHKAccountData();

        // Create a client
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);

        // Create CHK account
        AccountActions.createAccount().createCHKAccount(chkAccount);

        // Create a note
        Pages.accountNavigationPage().clickNotesTab();
        Pages.notesPage().clickAddNewNoteButton();
        Pages.notesPage().typeToNewNoteTextArea(note.getNewNote());
        Pages.notesPage().setDueDateValue(note.getDueDate());
        Pages.notesPage().setExpirationDateValue(note.getExpirationDate());
        Pages.notesPage().clickSaveButton();
        Actions.loginActions().doLogOut();
    }
}
