package com.nymbus.actions.backoffice.printchecks;

import com.codeborne.pdftest.PDF;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.pages.Pages;
import org.testng.Assert;

public class PrintChecksActions {

    public void expandAllRows() {
        while (Pages.printChecksPage().isLoadMoreResultsButtonVisible()) {
            Pages.printChecksPage().clickLoadMoreResultsButton();
            SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        }
    }

    public void verifyMicrLineInPdf(String checkNumber, String officialCheckControl) {
        PDF pdf = new PDF(Pages.checkPrintPage().downloadCallStatementPdf());
        String[] lines = pdf.text.split("\n");
        String micrLine = lines[lines.length - 1];

        Assert.assertTrue(micrLine.contains(checkNumber), "MICR line does not contain 'Check#'");
        Assert.assertTrue(micrLine.contains("231387550"), "MICR line does not contain 'Hardcoded data'");
        Assert.assertTrue(micrLine.contains(officialCheckControl), "MICR line does not contain 'FI Owned Account#'");
    }
}
