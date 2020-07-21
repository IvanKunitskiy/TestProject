package com.nymbus.apirequest;

import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.transaction.nontellertransactions.CHKAccountData;
import io.restassured.http.ContentType;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class NonTellerTransaction {
    private static final String GENERIC_PROCESS_URL = Constants.API_URL + "widget._GenericProcess/";

    public static void generateCreditPurchaseTransaction(String cardNumber, String expirationDate, String amount) {
        JSONObject requestBody = CHKAccountData.getAtmCreditData(cardNumber, expirationDate, amount);

        System.out.println(requestBody.toString());

        given().
                auth().preemptive().basic(Constants.USERNAME, Constants.PASSWORD).
                contentType(ContentType.JSON).
                relaxedHTTPSValidation().
                body(requestBody.toString()).
        when().
                post(GENERIC_PROCESS_URL).
        then().
                statusCode(200).
                body("data[0].field.39", equalTo("00"));
    }

    public static void generateDebitPurchaseTransaction(String cardNumber, String expirationDate, String amount) {
        JSONObject requestBody = CHKAccountData.getAtmDepositData(cardNumber, expirationDate, amount);

        System.out.println(requestBody.toString());

        given().
                auth().preemptive().basic(Constants.USERNAME, Constants.PASSWORD).
                contentType(ContentType.JSON).
                relaxedHTTPSValidation().
                body(requestBody.toString()).
        when().
                post(GENERIC_PROCESS_URL).
        then().
                statusCode(200).
                body("data[0].field.39", equalTo("00"));
    }

    public static void generateWithdrawalONUSTransaction(String cardNumber, String expirationDate, String amount, String onusTerminalID) {
        JSONObject requestBody = CHKAccountData.getAtmCreditData(cardNumber, expirationDate, amount, onusTerminalID);

        System.out.println(requestBody.toString());

        given().
                auth().preemptive().basic(Constants.USERNAME, Constants.PASSWORD).
                contentType(ContentType.JSON).
                relaxedHTTPSValidation().
                body(requestBody.toString()).
        when().
                post(GENERIC_PROCESS_URL).
        then().
                statusCode(200).
                body("data[0].field.39", equalTo("00"));
    }

    public static void generateDepositONUSTransaction(String cardNumber, String expirationDate, String amount, String onusTerminalID) {
        JSONObject requestBody = CHKAccountData.getAtmDepositData(cardNumber, expirationDate, amount, onusTerminalID);

        System.out.println(requestBody.toString());

        given().
                auth().preemptive().basic(Constants.USERNAME, Constants.PASSWORD).
                contentType(ContentType.JSON).
                relaxedHTTPSValidation().
                body(requestBody.toString()).
        when().
                post(GENERIC_PROCESS_URL).
        then().
                statusCode(200).
                body("data[0].field.39", equalTo("00"));
    }
}