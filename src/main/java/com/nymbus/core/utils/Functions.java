package com.nymbus.core.utils;

import java.io.File;

public class Functions {

    public static String getOnlyDigitsFromString(String s) {
        return s.replaceAll("[^0-9?!.]", "");
    }

    public static String getFilePathByName(String fileName) {
        return new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + "client" + File.separator + fileName).getAbsolutePath();
    }

    public static String getCalculatedInterestAmount(double currentBalance, double rate, String fromDate, String toDate, boolean includeToDate) {
        int days = DateTime.getDaysBetweenTwoDates(fromDate, toDate, includeToDate);
        double result = (currentBalance * (rate/ 365) * days)/100;
        return String.format("%.2f", result);
    }
}