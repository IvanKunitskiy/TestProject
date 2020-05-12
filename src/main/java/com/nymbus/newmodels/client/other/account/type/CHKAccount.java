package com.nymbus.newmodels.client.other.account.type;

import com.nymbus.newmodels.client.clientdetails.type.organisation.MailCode;
import com.nymbus.newmodels.client.other.account.ProductType;
import com.nymbus.newmodels.settings.product.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CHKAccount implements Account {
    @NonNull String addNewOption;
    @NonNull private ProductType productType;
    private String accountTitle;
    private String accountNumber;
    @NonNull private Product product;
    private String bankBranch;
    private String statementCycle;
    private String chargeOrAnalyze;
    private String accountAnalysis;
    private String callClassCode;
    private String optInOutDate;
    private String dateOpened;
    private String currentOfficer;
    private String interestRate;
    private String earningCreditRate;
    private String automaticOverdraftStatus;
    private String automaticOverdraftLimit;
    private String numberOfATMCardsIssued;
    private String numberOfDebitCardsIssued;
    private String federalWHPercent;
    private String cashCollDaysBeforeChg;
    private String cashCollInterestChg;
    private String cashCollInterestRate;
    private String cashCollFloat;
    private String userDefinedField_1;
    private String userDefinedField_2;
    private String userDefinedField_3;
    private String userDefinedField_4;
    private String positivePay;
    private MailCode mailCode;
}
