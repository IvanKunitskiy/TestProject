package com.nymbus.core.utils;

import com.nymbus.newmodels.transaction.verifyingModels.NonTellerTransactionData;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Generator {

    public static int genInt(int from, int to) {
        return new Random().nextInt((to - from) + 1) + from;
    }

    public static String getRandomStringNumber() {
        return RandomStringUtils.randomNumeric(10);
    }

    public static String genString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static String getRandomStringNumber(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    public static String genAccountNumber() {
        return genInt(1, 9) + RandomStringUtils.randomNumeric(11);
    }

    public static String genMobilePhone(int length) {
        return "555" + getRandomStringNumber(7);
    }

    public static String genEmail() {
        return "testemail+" + getRandomStringNumber(7) + "@mail.com";
    }

    public static float genFloat(double from, double to, int precision) {
        float number = genFloat(from, to);
        return (float) Math.round(number * Math.pow(10, precision)) / (float) Math.pow(10, precision);
    }

    private static float genFloat(double from, double to) {
        float tmp = .0f;
        if (to >= from)
            tmp = (float) (from + (Math.random() * (to - from)));
        return tmp;
    }

    public static String genAddress() {
        return "Test " + Generator.genString(4) + " Avenue, " + Generator.genInt(1, 1000);
    }

    public static long genLong(long from, long to) {
        long tmp = 0;
        if (to >= from)
            tmp = from + Math.round((Math.random() * (to - from)));
        return tmp;
    }

    public static String getRandomFormattedDecimalStringValue(String pattern) {
        Random ran = new Random();
        DecimalFormat df = new DecimalFormat(pattern);
        return String.valueOf(df.format(ran.nextFloat() * 100));
    }

    public static String getRandomIntMinMaxInclusiveStringValue(int min, int max ) {
        Random ran = new Random();
        return Integer.toString(ran.nextInt(max - min + 1) + min);
    }

    public static Map<String, String> getFieldsMap(NonTellerTransactionData transactionData) {
        Map<String, String> result = new HashMap<>();
        result.put("0", "0200");
        result.put("3", "210000");
        result.put("4", transactionData.getAmount());
        result.put("11", String.valueOf(Generator.genInt(100000000, 922337203)));
        result.put("18", "5542");
        result.put("22", "022");
        result.put("35", String.format("%s=%s", transactionData.getCardNumber(), transactionData.getExpirationDate()));
        result.put("41", transactionData.getTerminalId());
        result.put("42", "01 sample av.");
        result.put("43", "Long ave. bld. 34      Nashville      US");
        result.put("48", "SHELL");
        result.put("49", "840");
        result.put("58", "10000000612");

        return result;
    }
}
