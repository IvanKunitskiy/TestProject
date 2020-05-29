package com.nymbus.actions.balanceinquiry;

import com.nymbus.core.utils.Generator;
import com.nymbus.core.utils.ImageParser;
import com.nymbus.pages.Pages;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.tesseract.TessBaseAPI;

import java.io.File;

import static org.bytedeco.leptonica.global.lept.pixDestroy;
import static org.bytedeco.leptonica.global.lept.pixRead;

public class BalanceInquiryActions {

    public File saveBalanceInquiryImage() {
        Pages.balanceInquiryModalPage().waitForLoadingSpinnerInvisibility();

        // Get the 'src' attribute value from the balance inquiry image
        String src = Pages.balanceInquiryModalPage().getBalanceInquiryImageSrc();

        // Generate name for balance inquiry image
        String imageName = "bi-image-" + Generator.genInt(100000000, 999999999);
        File biImage = new File(System.getProperty("user.dir") + "/screenshots/" + imageName + ".png");

        // Save the image
        ImageParser.loadImage(src, biImage.getAbsolutePath());
        return biImage;
    }

    public String readBalanceInquiryImage(File balanceInquiryImage) {
        BytePointer outText;
        TessBaseAPI api = new TessBaseAPI();

        // Initialize tesseract-ocr with English, without specifying tessdata path
        // TESSDATA_PREFIX=/usr/local/Cellar/tesseract-lang/4.0.0/share
        // TODO: move dictionary to current lib path?
        if (api.Init(System.getProperty("user.dir") + "/src/main/resources/tessdata", "eng") != 0) {
            System.out.println(System.getProperty("TESSDATA_PREFIX"));
            System.out.println(System.getenv("TESSDATA_PREFIX"));
            System.err.println("Could not initialize tesseract.");
            System.exit(1);
        }

        while (!balanceInquiryImage.exists()) {
            try {
                Thread.sleep(1000);
                System.out.println("File does not exist in folder");
                // TODO: add check if file does not exist after some time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Open input image with leptonica library
        PIX image = pixRead(balanceInquiryImage.getAbsolutePath());
        System.out.println("Path: -> " + balanceInquiryImage.getAbsolutePath()); // TODO: remove
        api.SetImage(image);

        // Get OCR result
        outText = api.GetUTF8Text();
        System.out.println("OCR output:\n" + outText.getString()); // TODO: remove

        // Destroy used object and release memory
        api.End();
        outText.deallocate();
        pixDestroy(image);

        return outText.getString();
    }

    public String getAccountBalanceValueByType(String imageText, String accountBalanceType) {
        String[] lines = imageText.split("\n");
        String accountAvailableBalanceValue = "";

        for (String line : lines) {
            if (line.contains(accountBalanceType)) {
                accountAvailableBalanceValue = line.split(" ")[3].replaceAll("[^0-9.]", "").trim();
            }
        }
        return accountAvailableBalanceValue;
    }
}
