package com.nymbus.util;

public class Random {
    private static int shortRandAdd = 0;
    private static int longRandAdd = 0;

    public static int genInt(int from, int to) {
        int tmp = 0;
        if (to >= from)
            tmp = (int) (from + Math.round((Math.random() * (to - from))));
        return tmp;
    }

    private static float genFloat(double from, double to) {
        float tmp = .0f;
        if (to >= from)
            tmp = (float) (from + (Math.random() * (to - from)));
        return tmp;
    }

    private static synchronized long genShortRandNumberByTime() {
        return (genInt(1, 9) * 10_000_000) + (System.currentTimeMillis() % 10_000_000) + shortRandAdd++;
    }

    private static String genString(int length) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < length; i++) {
            s.append((char) Random.genInt(97, 120));
        }
        return s.toString();
    }

    public static long genLong(long from, long to) {
        long tmp = 0;
        if (to >= from)
            tmp = from + Math.round((Math.random() * (to - from)));
        return tmp;
    }

    public static String genAddress() {
        return "Test " + Random.genString(4) + " Avenue, " + Random.genInt(1, 1000);
    }

    public static String genPostalCode() {
        return "" + Random.genInt(1, 9) + Random.genInt(10, 99) + Random.genInt(10, 99);
    }

    public static String genURL() {
        return "http://test" + Random.genString(5) + ".com";
    }

    public static String genTimestart() {
        String[] times = {"Immediatelly", "In about a week", "In about a month"};
        int value = Random.genInt(0, 2);
        return times[value];
    }

    public static String genCity() {
        return "New test " + Random.genString(4) + "ville";
    }


    public static String genMobilePhone() {
        return "555" + genInt(1000000, 9999999);
    }


    public static String genEmail(String emailPattern) {
        return emailPattern.substring(0, emailPattern.indexOf("@")) + "+" + genShortRandNumberByTime()
                + emailPattern.substring(emailPattern.indexOf("@"));
    }

    public static float genFloat(double from, double to, int precision) {
        float number = genFloat(from, to);
        return (float) Math.round(number * Math.pow(10, precision)) / (float) Math.pow(10, precision);
    }

    public static synchronized long genRandNumberByTime() {
        return System.currentTimeMillis() % 10_000_000_000L + longRandAdd++;
    }
}
