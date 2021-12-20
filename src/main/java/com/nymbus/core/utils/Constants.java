package com.nymbus.core.utils;

import com.nymbus.newmodels.UserCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Stack;

public class Constants {
    public static String URL = getBaseUrl();
    public static String WEB_ADMIN_URL = getWebAdminUrl();
    public static String API_URL = getApiUrl();
    public static String REMOTE_URL = System.getProperty("remoteurl");
    public static String BIN_NUMBER = getBinNumber();
    public static FinancialInstitutionType INSTITUTION_TYPE = getInstitutionType();

    public static String USERNAME = "autestthredone";
    public static String PASSWORD = "autestthredone";
    public static String FIRST_NAME = "autestthredone";
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

    public static String TEST_RAIL_USER = "pkyriushkin";
    public static String TEST_RAIL_PASSWORD = "@!Xu&|75sSg8F@!!";
    public static String TEST_RAIL_URL = "https://testrail.nymbus.com/testrail";

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


    /**
     * Browsers
     */
    public static String CHROME = "chrome";
    public static String FIREFOX = "firefox";

    private static String getBaseUrl() {

        try (InputStream input = new FileInputStream("constants.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
            } else {
                prop.load(input);
            }
            switch (getEnvironment()) {
                case "dev6":
                default:
                    return prop.getProperty("db.dev6");
                case "dev12":
                    return prop.getProperty("db.dev12");
                case "dev21":
                    return prop.getProperty("db.dev21");
                case "dev4":
                    return prop.getProperty("db.dev4");
                case "dev18":
                    return prop.getProperty("db.dev18");
                case "dev29":
                    return prop.getProperty("db.dev29");
                case "dev47":
                    return prop.getProperty("db.dev47");
                case "dev58":
                    return prop.getProperty("db.dev58");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static String getWebAdminUrl() {

        try (InputStream input = new FileInputStream("constants.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
            } else {
                prop.load(input);
            }
            switch (getEnvironment()) {
                case "dev6":
                default:
                    return prop.getProperty("db.wadev6");
                case "dev12":
                    return prop.getProperty("db.wadev12");
                case "dev21":
                    return prop.getProperty("db.wadev21");
                case "dev4":
                    return prop.getProperty("db.wadev4");
                case "dev18":
                    return prop.getProperty("db.wadev18");
                case "dev29":
                    return prop.getProperty("db.wadev29");
                case "dev47":
                    return prop.getProperty("db.wadev47");
                case "dev58":
                    return prop.getProperty("db.wadev58");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static String getApiUrl() {
        try (InputStream input = new FileInputStream("constants.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
            } else {
                prop.load(input);
            }
            switch (getEnvironment()) {
                case "dev6":
                default:
                    return prop.getProperty("db.apidev6");
                case "dev12":
                    return prop.getProperty("db.apidev12");
                case "dev21":
                    return prop.getProperty("db.apidev21");
                case "dev4":
                    return prop.getProperty("db.apidev4");
                case "dev18":
                    return prop.getProperty("db.apidev18");
                case "dev29":
                    return prop.getProperty("db.apidev29");
                case "dev47":
                    return prop.getProperty("db.apidev47");
                case "dev58":
                    return prop.getProperty("db.apidev58");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static String getBinNumber() {
        try (InputStream input = new FileInputStream("constants.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
            } else {
                prop.load(input);
            }
            switch (getEnvironment()) {
                case "dev6":
                case "dev12":
                default:
                    return prop.getProperty("db.binDev6");
                case "dev21":
                case "dev4":
                case "dev18":
                case "dev29":
                case "dev47":
                case "dev58":
                    return prop.getProperty("db.binDev21");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
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
            case "dev29":
            case "dev47":
            case "dev58":
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
            case "dev29":
            case "dev47":
            case "dev58":
                return FinancialInstitutionType.BANK;
        }
    }

    public static String getEnvironment() {
        return System.getProperty("domain", "dev12");
    }

    /**
     * Timeouts
     */
    public static int MICRO_TIMEOUT;
    public static int MINI_TIMEOUT;
    public static int SMALL_TIMEOUT;
    public static int BIG_TIMEOUT;

    /**
     * Directories
     */
    public static String RECEIPTS;

    static {
        try (InputStream input = new FileInputStream("constants.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
            } else {
                prop.load(input);

                USERS.push(new UserCredentials(prop.getProperty("db.user"), prop.getProperty("db.user")));
                USERS.push(new UserCredentials(prop.getProperty("db.user2"), prop.getProperty("db.user2")));
                USERS.push(new UserCredentials(prop.getProperty("db.user3"), prop.getProperty("db.user3")));
                USERS.push(new UserCredentials(prop.getProperty("db.user4"), prop.getProperty("db.user4")));
                USERS.push(new UserCredentials(prop.getProperty("db.user5"), prop.getProperty("db.user5")));
                USERS.push(new UserCredentials(prop.getProperty("db.user6"), prop.getProperty("db.user6")));
                USERS.push(new UserCredentials(prop.getProperty("db.user7"), prop.getProperty("db.user7")));
                USERS.push(new UserCredentials(prop.getProperty("db.user8"), prop.getProperty("db.user8")));
                USERS.push(new UserCredentials(prop.getProperty("db.user9"), prop.getProperty("db.user9")));
                USERS.push(new UserCredentials(prop.getProperty("db.user10"), prop.getProperty("db.user10")));
                USERS.push(new UserCredentials(prop.getProperty("db.userdone"), prop.getProperty("db.userdone")));
                USERS.push(new UserCredentials(prop.getProperty("db.userautotest"), prop.getProperty("db.userautotest")));

                RECEIPTS = prop.getProperty("db.receipts");
                MICRO_TIMEOUT = Integer.parseInt(prop.getProperty("db.microTimeout"));
                MINI_TIMEOUT = Integer.parseInt(prop.getProperty("db.miniTimeout"));
                SMALL_TIMEOUT = Integer.parseInt(prop.getProperty("db.smallTimeout"));
                BIG_TIMEOUT = Integer.parseInt(prop.getProperty("db.bigTimeout"));

                TEST_RAIL_USER = prop.getProperty("db.testRailUser");
                TEST_RAIL_PASSWORD = prop.getProperty("db.testRailPass");
                TEST_RAIL_URL = prop.getProperty("db.testRailURL");

                DAYS_BEFORE_SYSTEM_DATE = Integer.parseInt(prop.getProperty("db.DAYS_BEFORE_SYSTEM_DATE"));
                DAYS_BEFORE_SYSTEM_DATE_MONTH = Integer.parseInt(prop.getProperty("db.DAYS_BEFORE_SYSTEM_DATE_MONTH"));
                MAX_CHARACTERS_ON_DEBIT_CARD = Integer.parseInt(prop.getProperty("db.maxCharchtersDebitCard"));

                NOT_TELLER_USERNAME = prop.getProperty("db.notTellerUsername");
                NOT_TELLER_PASSWORD = prop.getProperty("db.notTellerPassword");

                USERNAME = prop.getProperty("db.username");
                PASSWORD = prop.getProperty("db.password");
                FIRST_NAME = prop.getProperty("db.firstname");
                LAST_NAME = prop.getProperty("db.lastname");

            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}