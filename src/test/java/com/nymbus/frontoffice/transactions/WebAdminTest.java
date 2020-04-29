package com.nymbus.frontoffice.transactions;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.transaction.enums.GLFunctionValue;
import com.nymbus.newmodels.transaction.verifyingModels.WebAdminTransactionData;
import com.nymbus.pages.webadmin.WebAdminPages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WebAdminTest extends BaseTest {

    @Test()
    @Severity(SeverityLevel.CRITICAL)
    public void verifyTransactionOnWebAdminPage() {
        WebAdminTransactionData transactionData = new WebAdminTransactionData();
        String date = "04/24/2020";
        transactionData.setPostingDate(date);
        transactionData.setGlFunctionValue(GLFunctionValue.DEPOSIT_ITEM);


        Selenide.open(Constants.WEB_ADMIN_URL);

        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        String accountNumber = "739768805";
        WebAdminActions.webAdminTransactionActions().goToTransactionUrl(accountNumber);

        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult() > 0,
                "Transaction items doesn't find !");

        Assert.assertEquals(WebAdminPages.rulesUIQueryAnalyzerPage().getDatePosted(1), transactionData.getPostingDate(),
                "Posted date doesn't match!");

        Assert.assertEquals(WebAdminPages.rulesUIQueryAnalyzerPage().getGLFunctionValue(1),
                 transactionData.getGlFunctionValue().getGlFunctionValue(),
                "Function value  doesn't match!");

        String transactionHeader = WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionHeaderIdValue(1);

        transactionData.setAmount(WebAdminPages.rulesUIQueryAnalyzerPage().getAmount(1));

        WebAdminActions.webAdminTransactionActions().goToGLInterface(transactionHeader);

        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult() > 0,
                "Transaction items doesn't find!");

        Assert.assertEquals( WebAdminPages.rulesUIQueryAnalyzerPage().getGLFunctionValue(1),
                            transactionData.getGlFunctionValue().getGlFunctionValue(),
                            "Function value doesn't match!");

        Assert.assertEquals( WebAdminPages.rulesUIQueryAnalyzerPage().getAmount(1),
                             transactionData.getAmount(),
                            "Amount value doesn't match!");

        Assert.assertEquals( WebAdminPages.rulesUIQueryAnalyzerPage().getGLInterfaceTransactionHeaderIdValue(1),
                             transactionHeader,
                            "HeaderId value doesn't match!");
    }
}