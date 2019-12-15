package com.nymbus.util;

import io.qameta.allure.Step;

public class AllureSteps {
    @Step("Check is - {expected} - equal to - {actual}")
    public static boolean isExpectedEqualToActual(Object expected, Object actual) {
        return expected.equals(actual);
    }

    @Step("Check is - {expected} - contains - {actual}")
    public static boolean isExpectedContainsActual(String actual, String expected) {
        return actual.contains(expected);
    }

    @Step("Check publishing days for daily publish website")
    public static boolean checkDaysForDailyPublish(String itemDay) {
        switch (itemDay) {
            case "Tuesday":
                return true;
            case "Wednesday":
                return true;
            case "Thursday":
                return true;
            case "Friday":
                return true;
            case "Saturday":
                return true;
            default:
                return false;
        }
    }

    @Step("Check publishing days for weekly publish website")
    public static boolean checkDaysForWeeklyPublish(String itemDay) {
        switch (itemDay) {
            case "Monday":
                return true;
            case "Wednesday":
                return true;
            default:
                return false;
        }
    }
}
