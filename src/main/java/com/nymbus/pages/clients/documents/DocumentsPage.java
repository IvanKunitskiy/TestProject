package com.nymbus.pages.clients.documents;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class DocumentsPage extends PageTools {

    private By addNewDocumentButton = By.xpath("//button[span[contains(text(), 'Add New Document')]]");

    @Step("Click 'Add New Document' button")
    public void clickAddNewDocumentButton() {
        waitForElementVisibility(addNewDocumentButton);
        waitForElementClickable(addNewDocumentButton);
        click(addNewDocumentButton);
    }
}
