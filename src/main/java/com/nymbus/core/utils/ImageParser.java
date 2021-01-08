package com.nymbus.core.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

public class ImageParser {

    private static BufferedImage getBufferedImage(String imageSource, int index) {
        try {
            String base64Image = imageSource.split(",")[index];
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            return ImageIO.read(new ByteArrayInputStream(imageBytes));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void writeBufferedImage(BufferedImage img, String imagePath) {
        try {
            ImageIO.write(img, "png", new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeBufferedImage(BufferedImage img, String formatName, String imagePath) {
        try {
            ImageIO.write(img, formatName, new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadImage(String imageSource, String filename, int index) {
        writeBufferedImage(getBufferedImage(imageSource, index), filename);
    }

    public static void loadImage(String imageSource, String formatName, String filename) {
        writeBufferedImage(getBufferedImage(imageSource, 1), formatName, filename);
    }

    private static BufferedImage getBufferedImageFromUrl(String url) {
        try {
            URL urlFromString = new URL(url);
            System.out.println(urlFromString.getPath());
            return ImageIO.read(urlFromString);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void loadImageFromUrl(String url, String filename) {
        writeBufferedImage(getBufferedImageFromUrl(url), filename);
    }

    public static void loadPdf(String src, String absolutePath) {
//        OutputStream outStream = null;
//        URLConnection uCon = null;
//        final int size = 1024;
//
//        InputStream is = null;
//        try {
//            URL url;
//            byte[] buf;
//            int byteRead, byteWritten = 0;
//            url = new URL(src);
//            outStream = new BufferedOutputStream(new FileOutputStream(absolutePath));
//
//            uCon = url.openConnection();
//            is = uCon.getInputStream();
//            buf = new byte[size];
//            while ((byteRead = is.read(buf)) != -1) {
//                outStream.write(buf, 0, byteRead);
//                byteWritten += byteRead;
//            }
//            System.out.println("Downloaded Successfully.");
//            System.out.println("File name: bytes :" + byteWritten);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                is.close();
//                outStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        URL url = null;
        try {
            url = new URL(src);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get(absolutePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}