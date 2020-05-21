package com.nymbus.core.utils;

public class Constants {
    public static String URL = getBaseUrl();
    public static String WEB_ADMIN_URL = getWebAdminUrl();
    public static String API_URL = getApiUrl();
    public static String REMOTE_URL = System.getProperty("remoteurl");
    public static String BIN_NUMBER = getBinNumber();

    public static String USERNAME = "autotest";
    public static String PASSWORD = "autotest";
    public static String FIRST_NAME = "autotest";
    public static String LAST_NAME = "autotest";

    /**
     * Browsers
     */
    public static String CHROME = "chrome";
    public static String FIREFOX = "firefox";

    private static String getBaseUrl() {
        switch (System.getProperty("domain", "dev6")) {
            case "dev6":
            default:
                return "https://dev6.nymbus.com/";
            case "dev12":
                return "https://dev12.nymbus.com/frontoffice";
            case "dev21":
                return "https://dev21.nj1.nymbus.com/frontoffice/";
        }
    }

    private static String getWebAdminUrl() {
        switch (System.getProperty("domain", "dev6")) {
            case "dev6":
            default:
                return "https://nymbus-u-was-07.nj1.nymbus.com:9445/webadmin/";
            case "dev12":
                return "https://nymbus-d-was-12.nj1.nymbus.com:9445/webadmin/";
            case "dev21":
                return "https://was-21.nj1.nymbus.com:9445/webadmin/";
        }
    }

    private static String getApiUrl() {
        switch (System.getProperty("domain", "dev6")) {
            case "dev6":
            default:
                return "https://nymbus-u-was-07.nj1.nymbus.com:9443/coreweb/controller/";
            case "dev12":
                return "https://nymbus-d-was-12.nj1.nymbus.com:9443/coreweb/controller/";
            case "dev21":
                return "https://was-21.nj1.nymbus.com:9445/coreweb/controller/";
        }
    }

    private static String getBinNumber() {
        switch (System.getProperty("domain", "dev6")) {
            case "dev6":
            case "dev12":
            default:
                return "510986";
            case "dev21":
                return "408079";
        }
    }

    /**
     * Timeouts
     */
    public static final int MICRO_TIMEOUT = 2;
    public static final int MINI_TIMEOUT = 5;
    public static final int SMALL_TIMEOUT = 10;
}