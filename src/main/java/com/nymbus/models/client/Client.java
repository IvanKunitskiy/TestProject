package com.nymbus.models.client;

import com.nymbus.core.utils.Generator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client {

    private String clientID;
    private String clientType;
    private String clientStatus;
    private String firstName;
    private String middleName;
    private String lastName;
    private String taxPayerIDType;
    private String taxID;
    private String birthDate;
    private String accountNumber;
    private String cardNumber;
    private List<IdentityDocument> identityDocument;
    private Address address;

    /*
    Client detailed info
     */
    private String suffix;
    private String maidenFamilyName;
    private String AKA_1;
    private String gender;
    private String education;
    private String income;
    private String maritalStatus;
    private String occupation;
    private String consumerInfoIndicator;
    private String jobTitle;
    private String ownOrRent;
    private String mailCode;
    private String selectOfficer;
    private String userDefined_1;
    private String userDefined_2;
    private String userDefined_3;
    private String userDefined_4;
    private String phone;
    private String phoneType;
    private String email;
    private String emailType;

    public Client setDefaultClientData() {
        Client client = new Client();

        client.setFirstName(Generator.genString(5));
        client.setFirstName(Generator.genString(5));
        client.setMiddleName(Generator.genString(5));
        client.setLastName(Generator.genString(5));
        client.setTaxPayerIDType("Individual SSN");
        client.setTaxID(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));
        client.setAddress(new Address().setDefaultPhysicalData());

        List<IdentityDocument> identityDocuments = new ArrayList<>();
        identityDocuments.add(new IdentityDocument().setDefaultIdentityDocumentData());
        identityDocuments.add(new IdentityDocument().setDefaultStateDriversLicenseData());
        client.setIdentityDocument(identityDocuments);
        client.setBirthDate("01/01/1990");
        client.setSuffix(Generator.genString(3));
        client.setMaidenFamilyName(Generator.genString(5));
        client.setAKA_1(Generator.genString(5));
        client.setOccupation(Generator.genString(5));
        client.setJobTitle(Generator.genString(5));
        client.setUserDefined_1(Generator.genString(5));
        client.setUserDefined_2(Generator.genString(5));
        client.setUserDefined_3(Generator.genString(5));
        client.setUserDefined_4(Generator.genString(5));
        client.setPhone(Generator.genMobilePhone(10));
        client.setEmail(Generator.genEmail());

        return client;
    }

    public Client setConsumerClientData() {
        Client client = new Client();
        client.setClientStatus("Consumer");
        client.setFirstName(Generator.genString(5));
        client.setFirstName(Generator.genString(5));
        client.setMiddleName(Generator.genString(5));
        client.setLastName(Generator.genString(5));
        client.setTaxPayerIDType("Individual SSN");
        client.setTaxID(String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(4));
        client.setAddress(new Address().setDefaultPhysicalData());

        List<IdentityDocument> identityDocuments = new ArrayList<>();
        identityDocuments.add(new IdentityDocument().setDefaultIdentityDocumentData());
        client.setIdentityDocument(identityDocuments);
        client.setBirthDate("01/01/1990");
        client.setSuffix("");
        client.setGender("");
        client.setEducation("");
        client.setIncome("");
        client.setMaritalStatus("");
        client.setMaidenFamilyName("");
        client.setAKA_1("");
        client.setOccupation("");
        client.setConsumerInfoIndicator("");
        client.setOwnOrRent("");
        client.setMailCode("");
        client.setSelectOfficer("");
        client.setJobTitle("");
        client.setUserDefined_1("");
        client.setUserDefined_2("");
        client.setUserDefined_3("");
        client.setUserDefined_4("");
        client.setPhone("");
        client.setEmail("");

        return client;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientType='" + clientType + '\'' +
                ", clientStatus='" + clientStatus + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", taxPayerIDType='" + taxPayerIDType + '\'' +
                ", taxID='" + taxID + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", identityDocument=" + identityDocument +
                ", address=" + address +
                ", suffix='" + suffix + '\'' +
                ", maidenFamilyName='" + maidenFamilyName + '\'' +
                ", AKA_1='" + AKA_1 + '\'' +
                ", gender='" + gender + '\'' +
                ", education='" + education + '\'' +
                ", income='" + income + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", occupation='" + occupation + '\'' +
                ", consumerInfoIndicator='" + consumerInfoIndicator + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", ownOrRent='" + ownOrRent + '\'' +
                ", mailCode='" + mailCode + '\'' +
                ", selectOfficer='" + selectOfficer + '\'' +
                ", userDefined_1='" + userDefined_1 + '\'' +
                ", userDefined_2='" + userDefined_2 + '\'' +
                ", userDefined_3='" + userDefined_3 + '\'' +
                ", userDefined_4='" + userDefined_4 + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return clientType.equals(client.clientType) &&
                clientStatus.equals(client.clientStatus) &&
                firstName.equals(client.firstName) &&
                Objects.equals(middleName, client.middleName) &&
                lastName.equals(client.lastName) &&
                taxPayerIDType.equals(client.taxPayerIDType) &&
                taxID.equals(client.taxID) &&
                birthDate.equals(client.birthDate) &&
                Objects.equals(accountNumber, client.accountNumber) &&
                Objects.equals(cardNumber, client.cardNumber) &&
                identityDocument.equals(client.identityDocument) &&
                address.equals(client.address) &&
                Objects.equals(suffix, client.suffix) &&
                Objects.equals(maidenFamilyName, client.maidenFamilyName) &&
                Objects.equals(AKA_1, client.AKA_1) &&
                Objects.equals(gender, client.gender) &&
                Objects.equals(education, client.education) &&
                Objects.equals(income, client.income) &&
                Objects.equals(maritalStatus, client.maritalStatus) &&
                occupation.equals(client.occupation) &&
                Objects.equals(consumerInfoIndicator, client.consumerInfoIndicator) &&
                Objects.equals(jobTitle, client.jobTitle) &&
                Objects.equals(ownOrRent, client.ownOrRent) &&
                Objects.equals(mailCode, client.mailCode) &&
                selectOfficer.equals(client.selectOfficer) &&
                Objects.equals(userDefined_1, client.userDefined_1) &&
                Objects.equals(userDefined_2, client.userDefined_2) &&
                Objects.equals(userDefined_3, client.userDefined_3) &&
                Objects.equals(userDefined_4, client.userDefined_4) &&
                phone.equals(client.phone) &&
                Objects.equals(email, client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientType, clientStatus, firstName, middleName, lastName, taxPayerIDType, taxID, birthDate, accountNumber, cardNumber, identityDocument, address, suffix, maidenFamilyName, AKA_1, gender, education, income, maritalStatus, occupation, consumerInfoIndicator, jobTitle, ownOrRent, mailCode, selectOfficer, userDefined_1, userDefined_2, userDefined_3, userDefined_4, phone, email);
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getMaidenFamilyName() {
        return maidenFamilyName;
    }

    public void setMaidenFamilyName(String maidenFamilyName) {
        this.maidenFamilyName = maidenFamilyName;
    }

    public String getAKA_1() {
        return AKA_1;
    }

    public void setAKA_1(String AKA_1) {
        this.AKA_1 = AKA_1;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getConsumerInfoIndicator() {
        return consumerInfoIndicator;
    }

    public void setConsumerInfoIndicator(String consumerInfoIndicator) {
        this.consumerInfoIndicator = consumerInfoIndicator;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getOwnOrRent() {
        return ownOrRent;
    }

    public void setOwnOrRent(String ownOrRent) {
        this.ownOrRent = ownOrRent;
    }

    public String getMailCode() {
        return mailCode;
    }

    public void setMailCode(String mailCode) {
        this.mailCode = mailCode;
    }

    public String getSelectOfficer() {
        return selectOfficer;
    }

    public void setSelectOfficer(String selectOfficer) {
        this.selectOfficer = selectOfficer;
    }

    public String getUserDefined_1() {
        return userDefined_1;
    }

    public void setUserDefined_1(String userDefined_1) {
        this.userDefined_1 = userDefined_1;
    }

    public String getUserDefined_2() {
        return userDefined_2;
    }

    public void setUserDefined_2(String userDefined_2) {
        this.userDefined_2 = userDefined_2;
    }

    public String getUserDefined_3() {
        return userDefined_3;
    }

    public void setUserDefined_3(String userDefined_3) {
        this.userDefined_3 = userDefined_3;
    }

    public String getUserDefined_4() {
        return userDefined_4;
    }

    public void setUserDefined_4(String userDefined_4) {
        this.userDefined_4 = userDefined_4;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTaxPayerIDType() {
        return taxPayerIDType;
    }

    public void setTaxPayerIDType(String taxPayerIDType) {
        this.taxPayerIDType = taxPayerIDType;
    }

    public String getTaxID() {
        return taxID;
    }

    public void setTaxID(String taxID) {
        this.taxID = taxID;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public List<IdentityDocument> getIdentityDocument() {
        return identityDocument;
    }

    public void setIdentityDocument(List<IdentityDocument> identityDocument) {
        this.identityDocument = identityDocument;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
