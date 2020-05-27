package com.nymbus.actions.balanceinquiry;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class BalanceInquiryActions {
    public static void readBalanceInquiryImage() {
        try {
            ITesseract image = new Tesseract();
            File file = new File(System.getProperty("user.dir") + "/screenshots/name.png");
            System.out.println(file.getAbsolutePath());
            String imgText = image.doOCR(file);
            System.out.println("!!!!!!!!!!!!!!!_____________________________!!!!!!!!!!!!!!!!!!");
            System.out.println(imgText);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }
}
