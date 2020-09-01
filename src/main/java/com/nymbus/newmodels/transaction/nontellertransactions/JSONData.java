package com.nymbus.newmodels.transaction.nontellertransactions;

import com.nymbus.core.utils.Generator;
import com.nymbus.newmodels.transaction.enums.ATMTransactionType;
import com.nymbus.newmodels.transaction.verifyingModels.NonTellerTransactionData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class JSONData {

    public static JSONObject getAtmCreditData(String cardNumber, String cardExpiration, String amount) {
        JSONObject json = new JSONObject();
        json.put("actions", getActionsArray());
        json.put("ruleType", "eft");
        json.put("beans", getBeansArray(cardNumber, cardExpiration, amount));

        return json;
    }

    public static JSONObject getAtmCreditData(String cardNumber, String cardExpiration, String amount, String onusTerminalID) {
        JSONObject json = new JSONObject();
        json.put("actions", getActionsArray());
        json.put("ruleType", "eft");
        json.put("beans", getBeansArray(cardNumber, cardExpiration, amount, onusTerminalID));

        return json;
    }

    public static JSONObject get224AtmCreditData(String cardNumber, String cardExpiration, String amount, String onusTerminalID) {
        JSONObject json = new JSONObject();
        json.put("actions", getActionsArray());
        json.put("ruleType", "eft");
        json.put("beans", get224BeansArray(cardNumber, cardExpiration, amount, onusTerminalID));

        return json;
    }

    public static JSONObject getAtmDepositData(String cardNumber, String cardExpiration, String amount) {
        JSONObject json = new JSONObject();
        json.put("actions", getDepositActionsArray());
        json.put("ruleType", "eft");
        json.put("beans", getDepositBeansArray(cardNumber, cardExpiration, amount));

        return json;
    }

    public static JSONObject getAtmDepositData(String cardNumber, String cardExpiration, String amount, String onusTerminalID) {
        JSONObject json = new JSONObject();
        json.put("actions", getDepositActionsArray());
        json.put("ruleType", "eft");
        json.put("beans", getDepositBeansArray(cardNumber, cardExpiration, amount, onusTerminalID));

        return json;
    }

    private static JSONObject getFieldsArr(String cardNumber, String cardExpiration, String amount) {
        JSONObject fields = new JSONObject();
        fields.put("0", "0200");
        fields.put("3", "000000");
        fields.put("4", amount);
        fields.put("11", String.valueOf(Generator.genInt(100000000, 922337203)));
        fields.put("18", "6010");
        fields.put("22","801");
        fields.put("35", getCardNumberExpirationString(cardNumber, cardExpiration));
        fields.put("41","FNBWT003");
        fields.put("43","Long ave. bld. 34      Nashville      US");
        fields.put("58","0000000002U");

        return fields;
    }

    private static JSONObject getFieldsArr(String cardNumber, String cardExpiration, String amount, String onusTerminalID) {
        JSONObject fields = new JSONObject();
        fields.put("0", "0200");
        fields.put("3", "000000");
        fields.put("4", amount);
        fields.put("11", String.valueOf(Generator.genInt(100000000, 922337203)));
        fields.put("18", "6010");
        fields.put("22","801");
        fields.put("35", getCardNumberExpirationString(cardNumber, cardExpiration));
        fields.put("41", onusTerminalID);
        fields.put("43","Long ave. bld. 34      Nashville      US");
        fields.put("58","0000000002U");

        return fields;
    }

    private static JSONObject get224FieldsArr(String cardNumber, String cardExpiration, String amount, String onusTerminalID) {
        JSONObject fields = new JSONObject();
        fields.put("0", "0200");
        fields.put("3", "011000");
        fields.put("4", amount);
        fields.put("11", String.valueOf(Generator.genInt(100000000, 922337203)));
        fields.put("18", "6010");
        fields.put("22","801");
        fields.put("35", getCardNumberExpirationString(cardNumber, cardExpiration));
        fields.put("41", onusTerminalID);
        fields.put("43","Long ave. bld. 34      Nashville      US");
        fields.put("58","0000000002U");

        return fields;
    }

    private static JSONObject getDepositFieldsArr(String cardNumber, String cardExpiration, String amount) {
        JSONObject fields = new JSONObject();
        fields.put("0", "0200");
        fields.put("3", "210000");
        fields.put("4", amount);
        fields.put("11", String.valueOf(Generator.genInt(100000000, 922337203)));
        fields.put("18", "5542");
        fields.put("22","022");
        fields.put("35", getCardNumberExpirationString(cardNumber, cardExpiration));
        fields.put("42","01 sample av.");
        fields.put("43","Long ave. bld. 34      Nashville      US");
        fields.put("48","SHELL");
        fields.put("49","840");
        fields.put("58","0000000002U");

        return fields;
    }

    private static JSONObject getDepositFieldsArr(String cardNumber, String cardExpiration, String amount, String onusTerminalID) {
        JSONObject fields = new JSONObject();
        fields.put("0", "0200");
        fields.put("3", "210000");
        fields.put("4", amount);
        fields.put("11", String.valueOf(Generator.genInt(100000000, 922337203)));
        fields.put("18", "5542");
        fields.put("22","022");
        fields.put("35", getCardNumberExpirationString(cardNumber, cardExpiration));
        fields.put("41", onusTerminalID);
        fields.put("42","01 sample av.");
        fields.put("43","Long ave. bld. 34      Nashville      US");
        fields.put("48","SHELL");
        fields.put("49","840");
        fields.put("58","10000000612");

        return fields;
    }

    private static JSONArray getBeansArray(String cardNumber, String cardExpiration, String amount) {
        JSONArray beans = new JSONArray();
        JSONObject json = new JSONObject();
        json.put("fields", getFieldsArr(cardNumber, cardExpiration, amount));
        beans.put(json);

        return beans;
    }

    private static JSONArray getBeansArray(String cardNumber, String cardExpiration, String amount, String onusTerminalID) {
        JSONArray beans = new JSONArray();
        JSONObject json = new JSONObject();
        json.put("fields", getFieldsArr(cardNumber, cardExpiration, amount, onusTerminalID));
        beans.put(json);

        return beans;
    }

    private static JSONArray getBeansArray(JSONObject fieldsArr) {
        JSONArray beans = new JSONArray();
        JSONObject json = new JSONObject();
        json.put("fields", fieldsArr);
        beans.put(json);

        return beans;
    }

    private static JSONArray get224BeansArray(String cardNumber, String cardExpiration, String amount, String onusTerminalID) {
        JSONArray beans = new JSONArray();
        JSONObject json = new JSONObject();
        json.put("fields", get224FieldsArr(cardNumber, cardExpiration, amount, onusTerminalID));
        beans.put(json);

        return beans;
    }

    private static JSONArray getDepositBeansArray(String cardNumber, String cardExpiration, String amount) {
        JSONArray beans = new JSONArray();
        JSONObject json = new JSONObject();
        json.put("fields", getDepositFieldsArr(cardNumber, cardExpiration, amount));
        beans.put(json);

        return beans;
    }

    private static JSONArray getDepositBeansArray(String cardNumber, String cardExpiration, String amount, String onusTerminalID) {
        JSONArray beans = new JSONArray();
        JSONObject json = new JSONObject();
        json.put("fields", getDepositFieldsArr(cardNumber, cardExpiration, amount, onusTerminalID));
        beans.put(json);

        return beans;
    }

    private static JSONArray getActionsArray() {
        JSONArray actions = new JSONArray();
        actions.put("0200");

        return actions;
    }

    private static JSONArray getActionsArray(String [] actions) {
        return new JSONArray(actions);
    }

    private static JSONArray getDepositActionsArray() {
        JSONArray actions = new JSONArray();
        actions.put("0200");

        return actions;
    }

    private static String getCardNumberExpirationString(String cardNumber, String cardExpiration) {
        return cardNumber + "=" + cardExpiration;
    }

   public static JSONObject getATMData(NonTellerTransactionData nonTellerTransactionData, ATMTransactionType type) {
       JSONObject json = new JSONObject();
       json.put("actions", getActionsArray());
       json.put("ruleType", "eft");
       json.put("beans", getBeansArray(getFieldsArr(nonTellerTransactionData, type)));

       return json;
   }

    public static JSONObject getATMData(Map <String, String> fields) {
        JSONObject json = new JSONObject();
        json.put("actions", getActionsArray());
        json.put("ruleType", "eft");
        json.put("beans", getBeansArray(getFieldsArr(fields)));

        return json;
    }

    public static JSONObject getATMData(Map <String, String> fields, String [] actions) {
        JSONObject json = new JSONObject();
        json.put("actions", getActionsArray(actions));
        json.put("ruleType", "eft");
        json.put("beans", getBeansArray(getFieldsArr(fields)));

        return json;
    }
    private static JSONObject getFieldsArr(Map <String, String> fields) {
        return new JSONObject(fields);
    }

    private static JSONObject getFieldsArr(NonTellerTransactionData nonTellerTransactionData, ATMTransactionType type) {
        switch (type) {
            default:
            case ATM_WITHDRAWAL_224:
                return get224FieldsArr(nonTellerTransactionData);
            case ATM_DEPOSIT_ONUS_108:
                return get108FieldsArr(nonTellerTransactionData);
        }
    }

    private static JSONObject get224FieldsArr(NonTellerTransactionData nonTellerTransactionData) {
        JSONObject fields = new JSONObject();
        fields.put("0", "0200");
        fields.put("3", "011000");
        fields.put("4", nonTellerTransactionData.getAmount());
        fields.put("11", String.valueOf(Generator.genInt(100000000, 922337203)));
        fields.put("18", "6010");
        fields.put("22","801");
        fields.put("35", getCardNumberExpirationString(nonTellerTransactionData.getCardNumber(), nonTellerTransactionData.getExpirationDate()));
        fields.put("41", nonTellerTransactionData.getTerminalId());
        fields.put("43","Long ave. bld. 34      Nashville      US");
        fields.put("58","0000000002U");

        return fields;
    }

    private static JSONObject get108FieldsArr(NonTellerTransactionData nonTellerTransactionData) {
        JSONObject fields = new JSONObject();
        fields.put("0", "0200");
        fields.put("3", "210000");
        fields.put("4", nonTellerTransactionData.getAmount());
        fields.put("11", String.valueOf(Generator.genInt(100000000, 922337203)));
        fields.put("18", "5542");
        fields.put("22", "022");
        fields.put("35", getCardNumberExpirationString(nonTellerTransactionData.getCardNumber(), nonTellerTransactionData.getExpirationDate()));
        fields.put("41", nonTellerTransactionData.getTerminalId());
        fields.put("42", "01 sample av.");
        fields.put("43", "Long ave. bld. 34      Nashville      US");
        fields.put("48", "SHELL");
        fields.put("49", "840");
        fields.put("58", "10000000612");

        return fields;
    }
}