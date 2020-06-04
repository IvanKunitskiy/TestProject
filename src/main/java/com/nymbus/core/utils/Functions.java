package com.nymbus.core.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Functions {

    public static String getOnlyDigitsFromString(String s) {
        return s.replaceAll("[^0-9?!.]", "");
    }

    public static String getFilePathByName(String fileName) {
        return new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + "client" + File.separator + fileName).getAbsolutePath();
    }

    public static String getCalculatedInterestAmount(double currentBalance, double rate, String fromDate, String toDate) {
        int days = DateTime.getDaysBetweenTwoDates(fromDate, toDate);
        double result = (currentBalance * (rate/ 365) * days)/100;
        return String.format("%.2f", result);
    }

    public static void cleanDirectory(String path) {
        try {
            FileUtils.cleanDirectory(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}