package com.nymbus.core.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

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

    public static void cleanDirectory(String path) {
        try {
            FileUtils.cleanDirectory(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(String path) {
        new File(path).delete();
    }

    public static void deleteDirectory(String path) {
        try {
            FileUtils.deleteDirectory(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String setNameOnDebitCard(String str) {
        return str.substring(0, Math.min(Constants.MAX_CHARACTERS_ON_DEBIT_CARD, str.length()));
    }

    public static String getNumberWithLocale(double num) {
        NumberFormat nf = NumberFormat.getInstance(new Locale(Locale.ENGLISH.getLanguage()));
        return nf.format(num);
    }
}