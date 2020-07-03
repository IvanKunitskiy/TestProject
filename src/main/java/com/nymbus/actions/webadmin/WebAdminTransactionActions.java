package com.nymbus.actions.webadmin;

import com.codeborne.selenide.Selenide;
import com.nymbus.core.utils.Constants;
import com.nymbus.pages.webadmin.WebAdminPages;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class WebAdminTransactionActions {
    private String getWarehouseTransactionsUrl() {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev6DS&"
                + "dqlQuery=count%3A+10%0D%0A"
                + "from%3A+bank.data.transaction.item%0D%0A"
                + "where%3A+%0D%0A-+effectiveentrydate%3A+%7Bgreater%3A"
                + "+%28currentday%29%7D%0D%0A-+rejectstatus%3A+%7Bnull%7D%0D%0A-"
                + "+amount%3A+%7Bgreater%3A+0%7D%0D%0A-+accountnumber%3A+%7Bnot+null%7D%0D%0A"
                + "orderBy%3A+-id%0D%0A%0D%0A"
                + "formats%3A+%0D%0A-+->bank.data.actmst%3A+%24%7Baccountnumber%7D&source=";
    }

    private String getTransactionUrl(String accountNumber) {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev6DS&"
                + "dqlQuery=count%3A+10%0D%0A"
                + "from%3A+bank.data.transaction.item%0D%0A"
                + "where%3A+%0D%0A-+.accountnumber->accountnumber%3A+"
                + accountNumber
                + "%0D%0AorderBy%3A+-id%0D%0A&source=";
    }

    private String getGLInterfaceUrl(String transactionNumber) {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev6DS&"
                + "dqlQuery=count%3A+10%0D%0A"
                + "from%3A+bank.data.gl.interface%0D%0A"
                + "where%3A+%0D%0A+-+parenttransaction%3A+"
                + transactionNumber
                + "%0D%0A&source=";
    }

    private String getGLInterfaceUrlWithDeletedItems(String transactionNumber) {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev6DS&"
                + "dqlQuery=count%3A+10%0D%0A"
                + "from%3A+bank.data.gl.interface%0D%0A"
                + "where%3A+%0D%0A+-+parenttransaction%3A+"
                + transactionNumber
                + "%0D%0AdeletedIncluded%3A+true&source=";
    }

    public void goToWarehouseTransactionsUrl() {
        Selenide.open(getWarehouseTransactionsUrl());

        waitForSearchResults();
    }

    public void goToTransactionUrl(String accountNumber) {
        Selenide.open(getTransactionUrl(accountNumber));

        waitForSearchResults();
    }

    public void goToGLInterface(String transactionHeader) {
        Selenide.open(getGLInterfaceUrl(transactionHeader));

        waitForSearchResults();
    }

    public void goToGLInterfaceWithDeletedItems(String transactionHeader) {
        Selenide.open(getGLInterfaceUrlWithDeletedItems(transactionHeader));

        waitForSearchResults();
    }

    private void waitForSearchResults() {
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForPageLoad();

        WebAdminPages.rulesUIQueryAnalyzerPage().waitForSearchResultTable();
    }

    public void verifyDeletedRecords() {
        int recordsCount = WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResultInterfaceTable();
        Assert.assertTrue(recordsCount > 0,"Transaction items doesn't find!");
        SoftAssert softAssert = new SoftAssert();
        String empty = "";
        for (int i = 1; i <= recordsCount; i++ ) {
            softAssert.assertNotEquals(WebAdminPages.rulesUIQueryAnalyzerPage().getDeletedWhenValue(i), empty,
                    "Deleted When field is blank!");
            softAssert.assertNotEquals(WebAdminPages.rulesUIQueryAnalyzerPage().getDeletedBy(i), empty,
                    "Deleted By field is blank!");
        }
        softAssert.assertAll();
    }
}