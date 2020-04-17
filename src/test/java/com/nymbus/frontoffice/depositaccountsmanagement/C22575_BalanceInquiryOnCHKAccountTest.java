package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.core.base.BaseTest;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22575_BalanceInquiryOnCHKAccountTest extends BaseTest {

    private Client client;
    private Account chkAccount;

    @BeforeMethod
    public void preCondition() {

    }

    @Test(description = "C22575, 'Balance inquiry' on CHK account")
    @Severity(SeverityLevel.CRITICAL)
    public void viewClientLevelCallStatement() {

    }
}