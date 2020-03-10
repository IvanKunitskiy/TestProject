package com.nymbus.models.client.other.account.type;

import com.nymbus.models.client.clientdetails.type.organisation.MailCode;
import com.nymbus.models.client.other.account.AccountType;
import com.nymbus.models.client.other.account.ProductType;
import com.nymbus.models.settings.product.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CHKAccount implements Account {
    @NonNull
    private ProductType productType;
    @NonNull private Product product;
    private String accountNumber;
    private String accountTitle;
    @NonNull private AccountType accountType;
    private MailCode mailCode;
}
