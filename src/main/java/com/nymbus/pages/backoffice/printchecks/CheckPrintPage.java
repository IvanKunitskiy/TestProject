package com.nymbus.pages.backoffice.printchecks;

import com.nymbus.core.base.PageTools;
import org.openqa.selenium.By;

import java.io.File;

public class CheckPrintPage extends PageTools {
    private By pdfLoadSpinner = By.xpath("//div[@class='spinnerWrapper']");
    private By downloadButtonLink = By.xpath("//a[@download='document.pdf']");

    public void waitForLoadingSpinnerInvisibility() {
        waitForElementInvisibility(pdfLoadSpinner);
    }

    public File downloadCallStatementPdf() {
        return downloadFile(downloadButtonLink);
    }
}
