package com.nymbus.pages.loans;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoanReservePage extends PageTools {

    private final By addNewButton = By.xpath("(//a[text()='Add New'])[1]");

    @Step("Click the 'Add new' button")
    public void clickAddNewButton() {
        waitForElementClickable(addNewButton);
        click(addNewButton);
    }
}
