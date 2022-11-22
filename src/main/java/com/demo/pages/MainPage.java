package com.demo.pages;

import com.demo.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class MainPage extends PageTools {
    private final By booksOption = By.xpath("//select[@id='searchDropdownBox']//option[text()='Books']");
    private final By searchButton = By.xpath("//input[@id='nav-search-submit-button']");
    @Step("Select book option")
    public void selectBooksOption() {
        waitForElementClickable(booksOption);
        click(booksOption);
    }
    @Step("Type into search field")
    public void typeIntoSearchField(String text) {
        typeWithActions(text);
        click(searchButton);
    }
}
