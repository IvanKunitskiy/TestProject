package com.nymbus.core.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class ImageParser {

    private static BufferedImage getBufferedImage(String imageSource) {
        try {
//            System.out.println(imageSource);
            String base64Image = imageSource.split(",")[1];
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            return ImageIO.read(new ByteArrayInputStream(imageBytes));
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void saveImage(BufferedImage img) {
        try {
            ImageIO.write(img,"png", new File(System.getProperty("user.dir") + "/screenshots/name.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getImage(String imageSource) {
        BufferedImage bi = getBufferedImage(imageSource);
        saveImage(bi);
    }


}
