package com.nymbus.core.utils;

import com.nymbus.newmodels.UserCredentials;

import java.util.Stack;

public class Constants {
    public static String URL = getBaseUrl();
    public static String WEB_ADMIN_URL = getWebAdminUrl();
    public static String API_URL = getApiUrl();
    public static String REMOTE_URL = System.getProperty("remoteurl");
    public static String BIN_NUMBER = getBinNumber();
    public static FinancialInstitutionType INSTITUTION_TYPE = getInstitutionType();

    public static String USERNAME = "autestthredone";
    //    public static String USERNAME = "autotest";
    public static String PASSWORD = "autestthredone";
    //    public static String PASSWORD = "autotest";
    //    public static String FIRST_NAME = "autotest";
    public static String FIRST_NAME = "autestthredone";
    //    public static String LAST_NAME = "autotest";
    public static String LAST_NAME = "autestthredone";

    public static int DAYS_BEFORE_SYSTEM_DATE = 3;
    public static int MAX_CHARACTERS_ON_DEBIT_CARD = 25;

    public static String INDIVIDUAL_TYPE_FOR_WEB_ADMIN_QUERY = getIndividualType();

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
                return "https://frontoffice.nymbus.com/frontoffice/#/crm/login/";
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
                return FinancialInstitutionType.BANK;
        }
    }

    public static String getEnvironment() {
        return System.getProperty("domain", "dev6");
    }

    /**
     * Timeouts
     */
    public static final int MICRO_TIMEOUT = 2;
    public static final int MINI_TIMEOUT = 5;
    public static final int SMALL_TIMEOUT = 10;
}