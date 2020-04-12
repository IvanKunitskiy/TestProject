package com.nymbus.core.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageParser {

    private static BufferedImage getBufferedImage(String imageSource) {
        try {
            String base64Image = imageSource.split(",")[1];
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            return ImageIO.read(new ByteArrayInputStream(imageBytes));
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
