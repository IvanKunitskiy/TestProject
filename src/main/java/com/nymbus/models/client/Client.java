package com.nymbus.models.client;

import com.nymbus.models.client.other.account.Account;
import com.nymbus.models.client.other.debitcard.DebitCard;
import com.nymbus.models.client.other.document.Document;
import lombok.Data;

import java.util.List;

@Data
public abstract class Client {
    /*Step - 3 Upload Documentation*/
    private List<Document> documents;

    /*Step - 4 Client Signature*/
    private String clientSignature;

    private List<Account> accounts;
    private List<DebitCard> debitCards;
}
