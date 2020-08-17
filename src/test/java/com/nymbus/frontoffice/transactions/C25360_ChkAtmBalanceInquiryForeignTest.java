package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.newmodels.client.other.debitcard.DebitCard;
import com.nymbus.newmodels.transaction.verifyingModels.NonTellerTransactionData;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

@Owner("Petro")
@Epic("Frontoffice")
@Feature("Transactions")
public class C25360_ChkAtmBalanceInquiryForeignTest {

    @BeforeMethod
    public void preCondition() {

    }

    @Test(description = "C25360, CHK ATM Balance Inquiry FOREIGN")
    @Severity(SeverityLevel.CRITICAL)
    public void chkAtmBalanceInquiryForeign() {

    }

    private void createDebitCard(String clientInitials, DebitCard debitCard) {
        Actions.clientPageActions().searchAndOpenClientByName(clientInitials);
        Pages.clientDetailsPage().clickOnMaintenanceTab();
        Pages.clientDetailsPage().clickOnNewDebitCardButton();
        Actions.debitCardModalWindowActions().fillDebitCard(debitCard);
        Pages.debitCardModalWindow().clickOnSaveAndFinishButton();
        Pages.debitCardModalWindow().waitForAddNewDebitCardModalWindowInvisibility();
    }

    private Map<String, String> getFieldsMap(NonTellerTransactionData transactionData) {
        Map<String, String > result = new HashMap<>();
        result.put("0", "0200");
        result.put("3", "311000");
        result.put("11", "430392");
        result.put("18", "4900");
        result.put("22", "012");
        result.put("32", "469212");
        result.put("39", "00");
        result.put("35", String.format("%s=%s", transactionData.getCardNumber(), transactionData.getExpirationDate()));
        result.put("41", "notonus");
        result.put("43", "123456789012345678901234567890123456TXUS");
        result.put("58", "10111000251");

        return  result;
    }

}
