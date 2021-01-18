package com.nymbus.frontoffice.backoffice;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.accountinstructions.StopPaymentInstruction;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.accountinstructions.InstructionConstructor;
import com.nymbus.newmodels.generation.accountinstructions.builder.StopPaymentInstructionBuilder;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22822_WeilandImportfile extends BaseTest {

    private String chkAccountNumber;
    private String chkAccountNumber2;
    private String chkAccountNumber3;
    private String chkAccountNumber4;
    private String chkAccountNumber5;
    private StopPaymentInstruction instruction;
    private String systemDate;

    @BeforeMethod
    public void preCondition() {
        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up CHK  account
        Account chkAccount = new Account().setCHKAccountData();
        Account chkAccount2 = new Account().setCHKAccountData();
        Account chkAccount3 = new Account().setCHKAccountData();
        Account chkAccount4 = new Account().setCHKAccountData();
        Account chkAccount5 = new Account().setCHKAccountData();
        chkAccountNumber = chkAccount.getAccountNumber();
        chkAccountNumber2 = chkAccount2.getAccountNumber();
        chkAccountNumber3 = chkAccount3.getAccountNumber();
        chkAccountNumber4 = chkAccount4.getAccountNumber();
        chkAccountNumber5 = chkAccount5.getAccountNumber();
        systemDate = WebAdminActions.loginActions().getSystemDate();

        // Set up instruction
        instruction = new InstructionConstructor(new StopPaymentInstructionBuilder())
                .constructInstruction(StopPaymentInstruction.class);

        // Log in
        SelenideTools.openUrl(Constants.URL);
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set product
        chkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, "MSBC"));
        chkAccount2.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, "MSBC"));
        chkAccount3.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, "MSBC"));
        chkAccount4.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, "MSBC"));
        chkAccount5.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, "MSBC"));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK account
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccount(chkAccount);
        Actions.loginActions().doLogOut();
    }


    @Test(description = "C22816, Weiland: Import file")
    @Severity(SeverityLevel.CRITICAL)
    public void weilandImportFile() {

    }
}
