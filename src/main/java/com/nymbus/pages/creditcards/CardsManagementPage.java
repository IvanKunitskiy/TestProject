package com.nymbus.pages.creditcards;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CardsManagementPage extends PageTools {

    private By editCardButton = By.xpath("//tbody[@ng-if='isHaveDebitCards']//tr[%s]//td[8]//button[1]");

    @Step("Click edit button {0}")
    public void clickEditButton(int index) {
        waitForElementVisibility(editCardButton, index);
        click(editCardButton, index);
    }
}