package com.nymbus.actions.cashierdefined;

import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.ImageParser;
import com.nymbus.pages.Pages;

import java.io.File;

public class NoticeActions {

    public File saveNoticeImage() {
        Pages.noticePage().checkPDFVisible();

        // Get the 'src' attribute value from the balance inquiry image
        String src = Pages.noticePage().getNoticeImageSrc();

        // Generate name for balance inquiry image
        String imageName = "bi-image-" + DateTime.getLocalDateTimeByPattern("yyMMddHHmmss");
        File biImage = new File(System.getProperty("user.dir") + "/screenshots/" + imageName + ".pdf");

        System.out.println(src);
        // Save the image
        ImageParser.loadImage(src, biImage.getAbsolutePath());
        return biImage;
    }




}
