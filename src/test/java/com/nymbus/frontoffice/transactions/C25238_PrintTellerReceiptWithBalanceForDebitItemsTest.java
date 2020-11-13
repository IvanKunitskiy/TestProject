package com.nymbus.frontoffice.transactions;

import com.nymbus.core.base.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Petro")
public class C25238_PrintTellerReceiptWithBalanceForDebitItemsTest extends BaseTest {

    @BeforeMethod
    public void preCondition() {

    }

    @Test(description = "C25238, Print teller receipt with balance for Debit Items (except 128-Check)")
    @Severity(SeverityLevel.CRITICAL)
    public void printTellerReceiptWithBalanceForDebitItems() {

    }
}
