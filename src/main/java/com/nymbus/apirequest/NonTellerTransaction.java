package com.nymbus.apirequest;

import com.nymbus.core.allure.AllureLogger;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.transaction.nontellertransactions.JSONData;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBodyExtractionOptions;
import org.json.JSONObject;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class NonTellerTransaction extends AllureLogger {
    private static final String GENERIC_PROCESS_URL = Constants.API_URL + "widget._GenericProcess/";

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

    public void generateMixDepCashTransaction(Map<String, String> fields) {
        JSONObject requestBody = JSONData.getATMData(fields);

        logInfo("Request body: " + requestBody.toString());

        given().
                auth().preemptive().basic(Constants.USERNAME, Constants.PASSWORD).
                contentType(ContentType.JSON).
                relaxedHTTPSValidation().
                body(requestBody.toString()).
        when().
                post(GENERIC_PROCESS_URL).
        then().
                statusCode(200).
                body("data[0].field.39", equalTo("00")).
                body("data[0].field.54", equalTo(fields.get("54"))).
                body("data[0].field.104", equalTo(fields.get("104")));
    }

    public void generateATMTransaction(Map<String, String> fields) {
        JSONObject requestBody = JSONData.getATMData(fields);

        logInfo("Request body: " + requestBody.toString());

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

    public void generateATMTransaction(Map<String, String> fields, String [] actions) {
        JSONObject requestBody = JSONData.getATMData(fields, actions);

        logInfo("Request body: " + requestBody.toString());

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

    public void generateATMTransaction(Map<String, String> fields, String responseCodeOfField39) {
        JSONObject requestBody = JSONData.getATMData(fields);

        logInfo("Request body: " + requestBody.toString());

        given().
                auth().preemptive().basic(Constants.USERNAME, Constants.PASSWORD).
                contentType(ContentType.JSON).
                relaxedHTTPSValidation().
                body(requestBody.toString()).
        when().
                post(GENERIC_PROCESS_URL).
        then().
                statusCode(200).
                body("data[0].field.39", equalTo(responseCodeOfField39));
    }

    public void generatePaymentDueRecordForNonCyclePrincipalAndInterestLoan(String[] actions, String accountId) {
        JSONObject requestBody = JSONData.getPaymentData(actions, accountId);

        logInfo("Request body: " + requestBody.toString());

        ResponseBodyExtractionOptions responseBody = given().
                auth().preemptive().basic(Constants.USERNAME, Constants.PASSWORD).
                contentType(ContentType.JSON).
                relaxedHTTPSValidation().
                body(requestBody.toString()).
                when().
                post(GENERIC_PROCESS_URL).
                then().
                statusCode(200).extract().body();

        logInfo("Response body: " + responseBody.asString());

        // TODO:
    }

    public String getFiledValue(Map<String, String> fields, String field) {
        JSONObject requestBody = JSONData.getATMData(fields);

        logInfo("Request body: " + requestBody.toString());

        return given().
               auth().preemptive().basic(Constants.USERNAME, Constants.PASSWORD).
               contentType(ContentType.JSON).
               relaxedHTTPSValidation().
               body(requestBody.toString()).
        when().
               post(GENERIC_PROCESS_URL).
        then().
               statusCode(200).
               body("data[0].field.39", equalTo("00")).
               extract().path("data[0].field." + field);
    }

    public Map<String, String> getDataMap(Map<String, String> fields) {
        JSONObject requestBody = JSONData.getATMData(fields);

        logInfo("Request body: " + requestBody.toString());

        ResponseBodyExtractionOptions responseBody =
        given().
                auth().preemptive().basic(Constants.USERNAME, Constants.PASSWORD).
                contentType(ContentType.JSON).
                relaxedHTTPSValidation().
                body(requestBody.toString()).
        when().
                post(GENERIC_PROCESS_URL).
        then().
                statusCode(200).extract().body();

        logInfo("Response body: " + responseBody.asString());

        return responseBody.jsonPath().getMap("data[0].field");
    }
}