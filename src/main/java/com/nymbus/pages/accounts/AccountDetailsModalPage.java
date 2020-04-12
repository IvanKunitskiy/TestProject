package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import org.openqa.selenium.By;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class AccountDetailsModalPage extends PageTools {

    private By modalImage = By.id("receiptTemplate");
    private By imageLoadSpinner =By.xpath("//*[@id='printReceipt']//dn-loading-spinner");


    public String getImageSource() {
        waitForElementVisibility(modalImage);
        return getWebElement(modalImage).getAttribute("src");
    }

    public void waitForLoadSpinnerInvisibility() {
        waitForElementInvisibility(imageLoadSpinner);
    }
}
