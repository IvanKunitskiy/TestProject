package com.nymbus.actions.balanceinquiry;

import com.nymbus.actions.Actions;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.ImageParser;
import com.nymbus.pages.Pages;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.tesseract.TessBaseAPI;
import org.testng.Assert;

import java.io.File;

import static org.bytedeco.leptonica.global.lept.pixDestroy;
import static org.bytedeco.leptonica.global.lept.pixRead;

public class BalanceInquiryActions {

    public File saveBalanceInquiryImage() {
        Pages.balanceInquiryModalPage().waitForLoadingSpinnerInvisibility();

        // Get the 'src' attribute value from the balance inquiry image
        String src = Pages.balanceInquiryModalPage().getBalanceInquiryImageSrc();

        // Generate name for balance inquiry image
        String imageName = "bi-image-" + DateTime.getLocalDateTimeByPattern("yyMMddHHmmss");
        File biImage = new File(System.getProperty("user.dir") + "/downloads/" + imageName + ".png");

        // Save the image
        ImageParser.loadImage(src, biImage.getAbsolutePath());
        return biImage;
    }

    public String readBalanceInquiryImage(File balanceInquiryImage) {
        BytePointer outText;
        TessBaseAPI api = new TessBaseAPI();

        // Initialize tesseract-ocr with English, without specifying tessdata path
        // TESSDATA_PREFIX = /src/main/resources/tessdata
        if (api.Init(System.getProperty("user.dir") + "/src/main/resources/tessdata", "eng") != 0) {
            System.err.println("Could not initialize tesseract.");
            System.exit(1);
        }

        while (!balanceInquiryImage.exists()) {
            try {
                Thread.sleep(1000);
                System.err.println("File does not exist in folder");
                // TODO: add check if file does not exist after some time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Open input image with leptonica library
        PIX image = pixRead(balanceInquiryImage.getAbsolutePath());
        api.SetImage(image);

        // Get OCR result
        outText = api.GetUTF8Text();

        // Destroy used object and release memory
        api.End();
        outText.deallocate();
        pixDestroy(image);

        return outText.getString();
    }

    public String getAccountBalanceValueByType(String imageText, String accountBalanceType) {
        String[] lines = imageText.split("\n");

        for (String line : lines) {
            if (line.contains(accountBalanceType)) {
                return line.split("\\$")[1].trim();
            }
        }
        return null;
    }

    public void assertAvailableAndCurrentBalanceValuesFromReceipt(String balanceInquiryImageData, String availableBalance, String currentBalance) {
        String balanceInquiryAvailableBalance = Actions.balanceInquiryActions().getAccountBalanceValueByType(balanceInquiryImageData, "Available Balance");
        Assert.assertEquals(availableBalance, balanceInquiryAvailableBalance, "'Available balance' values are not equal");
        String balanceInquiryCurrentBalance = Actions.balanceInquiryActions().getAccountBalanceValueByType(balanceInquiryImageData, "Current Balance");
        Assert.assertEquals(currentBalance, balanceInquiryCurrentBalance, "'Current balance' values are not equal");
    }
}
