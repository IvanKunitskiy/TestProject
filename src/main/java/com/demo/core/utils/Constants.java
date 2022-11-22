package com.demo.core.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Constants {
    public static String URL = "https://www.amazon.com/";
    public static String REMOTE_URL = System.getProperty("remoteurl");

    public static String USERNAME = "";
    public static String PASSWORD = "";
    public static String FIRST_NAME = "";
    public static String LAST_NAME = "";


    public static String BROWSER = System.getProperty("browserName", "chrome");



    public final static int PROJECT_ID = 1;
    public final static int SUITE_ID = 1;

    public static String TEST_RAIL_USER = "";
    public static String TEST_RAIL_PASSWORD = "";
    public static String TEST_RAIL_URL = "";

    public static String CURRENT_TIME;

    static {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();

        CURRENT_TIME = dtf.format(now);
    }

    public final static boolean SEND_RESULT_TO_TESTRAIL = Boolean.parseBoolean(System.getProperty("sendResultToRestRail", "false"));

    public final static String TEST_RAIL_RUN_NAME = System.getProperty("testRunName");
    public static long TEST_RAIL_RUN_ID = Integer.parseInt(System.getProperty("testRunId", "0"));
    public final static String SUITE_NAME = System.getProperty("suiteXmlFile", "All Tests");

    /**
     * Browsers
     */
    public static String CHROME = "chrome";
}