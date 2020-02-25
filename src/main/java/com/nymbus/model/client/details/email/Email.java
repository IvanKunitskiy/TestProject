package com.nymbus.model.client.details.email;

import com.nymbus.model.RequiredField;

import java.util.Objects;

public class Email {
    @RequiredField private EmailType emailType;
    @RequiredField private String email;

    public EmailType getEmailType() {
        return emailType;
    }

    public void setEmailType(EmailType emailType) {
        this.emailType = emailType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return emailType == email1.emailType &&
                Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailType, email);
    }

    @Override
    public String toString() {
        return "Email{" +
                "emailType=" + emailType +
                ", email='" + email + '\'' +
                '}';
    }
}
