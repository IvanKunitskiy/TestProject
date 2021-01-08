package com.nymbus.actions.cashierdefined;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.ImageParser;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.pages.Pages;

import java.io.File;

public class NoticeActions {

    public File saveNoticeImage() {
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        SelenideTools.switchToLastTab();
        Pages.noticePage().checkPDFVisible();

        // Get the 'src' attribute value from the balance inquiry image
        SelenideTools.sleep(0);
        String src = Pages.noticePage().getNoticeImageSrc();
        System.out.println("src");

        // Generate name for balance inquiry image
        String imageName = "bi-image-" + DateTime.getLocalDateTimeByPattern("yyMMddHHmmss");
        File biImage = new File(System.getProperty("user.dir") + "/screenshots/" + imageName + ".pdf");

        System.out.println(src);
        System.out.println(biImage.getAbsolutePath());
        // Save the image
        ImageParser.loadPdf(src,biImage.getAbsolutePath());
        //ImageParser.loadImage(src, biImage.getAbsolutePath(), 0);
        return biImage;
    }




}
