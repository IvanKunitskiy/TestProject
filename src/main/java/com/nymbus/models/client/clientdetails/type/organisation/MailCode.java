package com.nymbus.models.client.clientdetails.type.organisation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MailCode {
    DO_NOT_MAIL_STMT("Do not mail stmt"),
    MAIL("Mail");

    private final String mailCode;
}
