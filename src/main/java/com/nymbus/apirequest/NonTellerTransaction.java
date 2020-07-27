package com.nymbus.apirequest;

import com.nymbus.core.allure.AllureLogger;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.transaction.enums.ATMTransactionType;
import com.nymbus.newmodels.transaction.nontellertransactions.JSONData;
import com.nymbus.newmodels.transaction.verifyingModels.NonTellerTransactionData;
import io.restassured.http.ContentType;
import org.json.JSONObject;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class NonTellerTransaction extends AllureLogger {
    private static final String GENERIC_PROCESS_URL = Constants.API_URL + "widget._GenericProcess/";

    public void generateCreditPurchaseTransaction(String cardNumber, String expirationDate, String amount) {
        JSONObject requestBody = JSONData.getAtmCreditData(cardNumber, expirationDate, amount);

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

    public void generateDebitPurchaseTransaction(String cardNumber, String expirationDate, String amount) {
        JSONObject requestBody = JSONData.getAtmDepositData(cardNumber, expirationDate, amount);

        logInfo("Request body: " + requestBody.toString());
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

    public void generateWithdrawalONUSTransaction(String cardNumber, String expirationDate, String amount, String onusTerminalID) {
        JSONObject requestBody = JSONData.getAtmCreditData(cardNumber, expirationDate, amount, onusTerminalID);

        logInfo("Request body: " + requestBody.toString());
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

    public void generateDepositONUSTransaction(String cardNumber, String expirationDate, String amount, String onusTerminalID) {
        JSONObject requestBody = JSONData.getAtmDepositData(cardNumber, expirationDate, amount, onusTerminalID);

        logInfo("Request body: " + requestBody.toString());
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

    public void generateATMTransaction(NonTellerTransactionData transactionData, ATMTransactionType type) {
        JSONObject requestBody = JSONData.getATMData(transactionData, type);

        logInfo("Request body: " + requestBody.toString());
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

    public void generateMixDepCashTransaction(Map<String, String> fields) {
        JSONObject requestBody = JSONData.getATMData(fields);

        logInfo("Request body: " + requestBody.toString());
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
                body("data[0].field.39", equalTo(fields.get("39"))).
                body("data[0].field.54", equalTo(fields.get("54"))).
                body("data[0].field.104", equalTo(fields.get("104")));
    }
}