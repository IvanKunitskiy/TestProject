package com.nymbus.core.utils;

import com.nymbus.newmodels.UserCredentials;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

public class Constants {
    public static String URL = getBaseUrl();
    public static String WEB_ADMIN_URL = getWebAdminUrl();
    public static String API_URL = getApiUrl();
    public static String REMOTE_URL = System.getProperty("remoteurl");
    public static String BIN_NUMBER = getBinNumber();
    public static FinancialInstitutionType INSTITUTION_TYPE = getInstitutionType();

    public static final String CDT_TEMPLATE_NAME = "AUTOMATION_SAV_TO_CHK_CDT";

    public static String USERNAME = "autestthredone";
    //    public static String USERNAME = "autotest";
    public static String PASSWORD = "autestthredone";
    //    public static String PASSWORD = "autotest";
    //    public static String FIRST_NAME = "autotest";
    public static String FIRST_NAME = "autestthredone";
    //    public static String LAST_NAME = "autotest";
    public static String LAST_NAME = "autestthredone";

    public static String NOT_TELLER_USERNAME = "autotesttell";
    public static String NOT_TELLER_PASSWORD = "autotesttell";

    public static String BROWSER = System.getProperty("browserName", "chrome");

    public static int DAYS_BEFORE_SYSTEM_DATE = 3;
    public static int DAYS_BEFORE_SYSTEM_DATE_MONTH = 31;
    public static int MAX_CHARACTERS_ON_DEBIT_CARD = 25;

    public static String INDIVIDUAL_TYPE_FOR_WEB_ADMIN_QUERY = getIndividualType();

    public final static int PROJECT_ID = 2;
    public final static int SUITE_ID = 81;

    public final static String TEST_RAIL_USER = "pkyriushkin";
    public final static String TEST_RAIL_PASSWORD = "@!Xu&|75sSg8F!";
    public final static String TEST_RAIL_URL = "https://testrail.nymbus.com/testrail";

    public static String CURRENT_TIME;

    static {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();

        CURRENT_TIME = dtf.format(now);
    }

    public final static boolean SEND_RESULT_TO_TESTRAIL = Boolean.parseBoolean(System.getProperty("sendResultToRestRail", "false"));

    public final static String TEST_RAIL_RUN_NAME = System.getProperty("testRunName");
    public static long TEST_RAIL_RUN_ID = Integer.parseInt(System.getProperty("testRunId", "0"));
    public final static String DOMAIN_NAME = getEnvironment();
    public final static String SUITE_NAME = System.getProperty("suiteXmlFile", "All Tests");


    public static Stack<UserCredentials> USERS = new Stack<>();

    static {
        USERS.push(new UserCredentials("autotestfirst", "autotestfirst"));
        USERS.push(new UserCredentials("autotestsecond", "autotestsecond"));
        USERS.push(new UserCredentials("autotestthird", "autotestthird"));
        USERS.push(new UserCredentials("autotestfourth", "autotestfourth"));
        USERS.push(new UserCredentials("autotestfifth", "autotestfifth"));
        USERS.push(new UserCredentials("autotestsixth", "autotestsixth"));
        USERS.push(new UserCredentials("autestthredone", "autestthredone"));
        USERS.push(new UserCredentials("autotest", "autotest"));
    }

    /**
     * Browsers
     */
    public static String CHROME = "chrome";
    public static String FIREFOX = "firefox";

    private static String getBaseUrl() {
        switch (getEnvironment()) {
            case "dev6":
            default:
                return "https://dev6.nymbus.com/";
            case "dev12":
                return "https://dev12.nymbus.com/frontoffice";
            case "dev21":
                return "https://dev21.nj1.nymbus.com/frontoffice/";
            case "dev4":
                return "https://frontoffice.nj1.nymbus.com/frontoffice/#/crm/login";
            case "dev18":
                return "https://dev18.nj1.nymbus.com/frontoffice/#/crm/login";
        }
    }

    private static String getWebAdminUrl() {
        switch (getEnvironment()) {
            case "dev6":
            default:
                return "https://nymbus-u-was-07.nj1.nymbus.com:9445/webadmin/";
            case "dev12":
                return "https://nymbus-d-was-12.nj1.nymbus.com:9445/webadmin/";
            case "dev21":
                return "https://was-21.nj1.nymbus.com:9445/webadmin/";
            case "dev4":
                return "https://nymbus-q-was-05.nj1.nymbus.com:9445/webadmin/";
            case "dev18":
                return "https://dev18.nj1.nymbus.com/webadmin/";
        }
    }

    private static String getApiUrl() {
        switch (getEnvironment()) {
            case "dev6":
            default:
                return "https://nymbus-u-was-07.nj1.nymbus.com:9443/coreweb/controller/";
            case "dev12":
                return "https://nymbus-d-was-12.nj1.nymbus.com:9443/coreweb/controller/";
            case "dev21":
                return "https://was-21.nj1.nymbus.com:9445/coreweb/controller/";
            case "dev4":
                return "https://nymbus-q-was-05.nj1.nymbus.com:9445/coreweb/controller/";
            case "dev18":
                return "https://dev18.nj1.nymbus.com/coreweb/controller/";
        }
    }

    private static String getBinNumber() {
        switch (getEnvironment()) {
            case "dev6":
            case "dev12":
            default:
                return "510986";
            case "dev21":
            case "dev4":
            case "dev18":
                return "408078";
        }
    }

    /**
     * Individual type
     */
    private static String getIndividualType() {
        switch (getEnvironment()) {
            case "dev6":
            default:
                return "IndividualType";
            case "dev12":
            case "dev21":
            case "dev4":
                return "Individual";
        }
    }

    private static FinancialInstitutionType getInstitutionType() {
        switch (getEnvironment()) {
            case "dev6":
            case "dev12":
            default:
                return FinancialInstitutionType.FEDERAL_CREDIT_UNION;
            case "dev21":
            case "dev4":
            case "dev18":
                return FinancialInstitutionType.BANK;
        }
    }

    public static String getEnvironment() {
        return System.getProperty("domain", "dev12");
    }

    /**
     * Timeouts
     */
    public static final int MICRO_TIMEOUT = 2;
    public static final int MINI_TIMEOUT = 5;
    public static final int SMALL_TIMEOUT = 10;

    /**
     * Directories
     */
    public static final String RECEIPTS = "receipts";
}