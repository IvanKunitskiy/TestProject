package com.nymbus.actions.balanceinquiry;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Functions;
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
        File biImage = new File(System.getProperty("user.dir") + "/screenshots/" + imageName + ".png");

        // Save the image
        ImageParser.loadImage(src, biImage.getAbsolutePath());
        return biImage;
    }

    public File saveImageFile(String src) {
        String imageName = "client-document-image-" + DateTime.getLocalDateTimeByPattern("yyMMddHHmmss");
        File cdImage = new File(System.getProperty("user.dir") + "/screenshots/" + imageName + ".png");
        ImageParser.loadImageFromUrl(src, cdImage.getAbsolutePath());
        return cdImage;
    }

    public File saveTransactionReceipt(String imgSrc) {
        String imageName = "transactionReceipt-" + DateTime.getLocalDateTimeByPattern("yyMMddHHmmss");
        String directory = System.getProperty("user.dir") + File.separator + Constants.RECEIPTS + File.separator;
        Functions.verifyPath(directory);

        // Save the image
        File transactionReceipt = new File(directory + imageName + ".tif");
        ImageParser.loadImage(imgSrc,"TIFF", transactionReceipt.getAbsolutePath());

        return transactionReceipt;
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

        int i = 0;
        while (!balanceInquiryImage.exists() && i < 3) {
            try {
                Thread.sleep(Constants.MINI_TIMEOUT);
                i++;
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

    public boolean isBalancePresentByType(String imageText, String balanceAmount, String accountBalanceType) {
        String[] lines = imageText.split("\n");
        boolean contains = false;

        for (String line : lines) {
            if (line.contains(accountBalanceType)) {
                contains = line.contains(balanceAmount);
            }
        }
        return contains;
    }

    public void assertAvailableAndCurrentBalanceValuesFromReceipt(File file, String availableBalance, String currentBalance) {
        String balanceInquiryImageData = readBalanceInquiryImage(file);
        Assert.assertTrue(isBalancePresentByType(balanceInquiryImageData, availableBalance, "Available Balance"),
                "'Available balance' values are not equal");
        Assert.assertTrue(isBalancePresentByType(balanceInquiryImageData, currentBalance, "Current Balance"),
                "'Current balance' values are not equal");
    }

    public String[] getReceiptLines(String src) {
        File transactionReceipt = saveTransactionReceipt(src);
        String data = readBalanceInquiryImage(transactionReceipt);

        return data.split("\n");
    }

    public boolean isLineEndsWithChars(String[] lines, String lineStartsWith, String lineEndsWith) {
        boolean contains = false;

        for (String line : lines) {
            if (line.startsWith(lineStartsWith)) {
                contains = line.endsWith(lineEndsWith);
                break;
            }
        }
        return contains;
    }

    public void uncheckPrintBalanceOnReceipt(int printBalanceOnReceiptValue) {
        if (printBalanceOnReceiptValue == 1) {
            Pages.balanceInquiryModalPage().clickPrintBalancesOnReceiptCheckbox();
        }
    }
}