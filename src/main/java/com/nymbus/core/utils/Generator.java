package com.nymbus.core.utils;

import org.apache.commons.lang3.RandomStringUtils;

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
}
