package com.nymbus.models.client.other.account.type;

import com.nymbus.models.client.Client;
import com.nymbus.models.client.clientdetails.type.organisation.MailCode;
import com.nymbus.models.client.other.account.AccountType;
import com.nymbus.models.client.other.account.BankBranch;
import com.nymbus.models.client.other.account.InterestFrequency;
import com.nymbus.models.client.other.account.ProductType;
import com.nymbus.models.settings.product.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
public class CDAccount implements Account {
    @NonNull private ProductType productType;
    @NonNull private Product product;
    private String accountNumber;
    private String accountTitle;
    @NonNull private AccountType accountType;
    private MailCode mailCode;
    @NonNull private List<Client> accountHoldersAndSigners; // TODO: Need to refactor
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
