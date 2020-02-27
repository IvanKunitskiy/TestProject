package com.nymbus.models.client.other.debitcard;

import com.nymbus.models.RequiredField;
import com.nymbus.models.settings.BinControl;
import lombok.Data;

@Data
public class DebitCard {
    @RequiredField private BinControl binControl;
    @RequiredField private boolean isInstantIssue;

    private String cardNumber;
    private String clientNumber;
    @RequiredField private String nameOfCard;
    private String secondLineEmbossing;
    @RequiredField private String accountN1;
    private String cardDesign;
    @RequiredField CardStatus cardStatus;
}
