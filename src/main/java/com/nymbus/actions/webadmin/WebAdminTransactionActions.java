package com.nymbus.actions.webadmin;

import com.codeborne.selenide.Selenide;
import com.nymbus.core.utils.Constants;
import com.nymbus.pages.webadmin.WebAdminPages;

public class WebAdminTransactionActions {

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
}