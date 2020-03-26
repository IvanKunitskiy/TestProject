package com.nymbus.newmodels.client.other.account.type;

import com.nymbus.newmodels.client.clientdetails.type.organisation.MailCode;
import com.nymbus.newmodels.client.other.account.AccountType;
import com.nymbus.newmodels.client.other.account.BankBranch;
import com.nymbus.newmodels.client.other.account.InterestFrequency;
import com.nymbus.newmodels.client.other.account.ProductType;
import com.nymbus.newmodels.settings.product.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CDAccount implements Account {
    @NonNull private ProductType productType;
    @NonNull private Product product;
    private String accountNumber;
    private String accountTitle;
    @NonNull private AccountType accountType;
    private MailCode mailCode;
//    @NonNull private List<Client> accountHoldersAndSigners; // TODO: Need to refactor (recursion warning)
    private String dateOpened;
    private String originatingOfficer; // TODO: Need to refactor
    @NonNull private BankBranch bankBranch;
    private String statementFlag;
    private String correspondingAccount;
    private int termType;
    private boolean autoRenewable;
    private InterestFrequency interestFrequency;
    private String rateIndex;
    @NonNull private String interestRate;
    @NonNull private String applyInterestTo;
    // TODO: Add other fields...
}
