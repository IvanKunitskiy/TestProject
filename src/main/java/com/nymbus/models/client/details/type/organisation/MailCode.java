package com.nymbus.models.client.details.type.organisation;

public enum MailCode {
    DO_NOT_MAIL_STMT("Do not mail stmt"),
    MAIL("Mail");

    private final String mailCode;

    MailCode(String mailCode) {
        this.mailCode = mailCode;
    }

    public String getMailCode() {
        return mailCode;
    }
}
