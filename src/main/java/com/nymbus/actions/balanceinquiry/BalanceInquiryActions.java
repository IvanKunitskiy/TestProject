package com.nymbus.actions.balanceinquiry;

import com.nymbus.pages.Pages;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.io.FileNotFoundException;

public class BalanceInquiryActions {
    public static void readBalanceInquiryImage() {
        try {
            File file = Pages.balanceInquiryModalPage().getBalanceInquiryImage().download();
            String path = file.getPath();

            ITesseract image = new Tesseract();
            String imgText = image.doOCR(new File(path));
            System.out.println("!!!!!!!!!!!!!!!_____________________________!!!!!!!!!!!!!!!!!!");
            System.out.println(imgText);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (TesseractException e) {
            e.printStackTrace();
        }

    }
}
