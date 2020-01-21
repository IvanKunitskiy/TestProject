package com.nymbus.models;

import com.nymbus.util.Random;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private String firstName;
    private String middleName;
    private String lastName;
    private String initials;
    private String photoPath;
    private String branch;
    private String location;
    private String title;
    private List<String> taxIDList;
    private String businessPhone;
    private String otherPhone;
    private String email;
    private String loginID;
    private boolean isLoginDisabled;
    private List<String> rolesList;
    private boolean isActive;
    private int checkDepositLimit;
    private String networkPrinter;
    private int officialCheckLimit;
    private int cashOutLimit;
    private boolean isTeller;

    public User setDefaultUserData(){
        User user = new User();
        user.setFirstName(Random.genString(2));
        user.setMiddleName(Random.genString(2));
        user.setLastName(Random.genString(2));
        user.setInitials(Random.genString(2));
        user.setBranch(Random.genString(2));
        user.setLocation(Random.genString(2));
        user.setTitle(Random.genString(2));
        user.setBusinessPhone(Random.genMobilePhone());
        user.setOtherPhone(Random.genMobilePhone());
        user.setEmail(Random.genEmail("test@test.ts"));
        user.setLoginID(Random.genString(5));
        user.setRolesList(new ArrayList<String>(3) {
            {
                add("Systems Groups");
                add("Teller");
                add("Officers");
            }
        });

        return user;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isLoginDisabled == user.isLoginDisabled &&
                isActive == user.isActive &&
                checkDepositLimit == user.checkDepositLimit &&
                officialCheckLimit == user.officialCheckLimit &&
                cashOutLimit == user.cashOutLimit &&
                isTeller == user.isTeller &&
                firstName.equals(user.firstName) &&
                Objects.equals(middleName, user.middleName) &&
                lastName.equals(user.lastName) &&
                initials.equals(user.initials) &&
                Objects.equals(photoPath, user.photoPath) &&
                branch.equals(user.branch) &&
                location.equals(user.location) &&
                title.equals(user.title) &&
                Objects.equals(taxIDList, user.taxIDList) &&
                businessPhone.equals(user.businessPhone) &&
                Objects.equals(otherPhone, user.otherPhone) &&
                email.equals(user.email) &&
                loginID.equals(user.loginID) &&
                rolesList.equals(user.rolesList) &&
                Objects.equals(networkPrinter, user.networkPrinter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                firstName,
                middleName,
                lastName,
                initials,
                photoPath,
                branch,
                location,
                title,
                taxIDList,
                businessPhone,
                otherPhone,
                email,
                loginID,
                isLoginDisabled,
                rolesList,
                isActive,
                checkDepositLimit,
                networkPrinter,
                officialCheckLimit,
                cashOutLimit,
                isTeller);
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

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTaxIDList() {
        return taxIDList;
    }

    public void setTaxIDList(List<String> taxIDList) {
        this.taxIDList = taxIDList;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getOtherPhone() {
        return otherPhone;
    }

    public void setOtherPhone(String otherPhone) {
        this.otherPhone = otherPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public boolean isLoginDisabled() {
        return isLoginDisabled;
    }

    public void setLoginDisabled(boolean loginDisabled) {
        isLoginDisabled = loginDisabled;
    }

    public List<String> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<String> rolesList) {
        this.rolesList = rolesList;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getCheckDepositLimit() {
        return checkDepositLimit;
    }

    public void setCheckDepositLimit(int checkDepositLimit) {
        this.checkDepositLimit = checkDepositLimit;
    }

    public String getNetworkPrinter() {
        return networkPrinter;
    }

    public void setNetworkPrinter(String networkPrinter) {
        this.networkPrinter = networkPrinter;
    }

    public int getOfficialCheckLimit() {
        return officialCheckLimit;
    }

    public void setOfficialCheckLimit(int officialCheckLimit) {
        this.officialCheckLimit = officialCheckLimit;
    }

    public int getCashOutLimit() {
        return cashOutLimit;
    }

    public void setCashOutLimit(int cashOutLimit) {
        this.cashOutLimit = cashOutLimit;
    }

    public boolean isTeller() {
        return isTeller;
    }

    public void setTeller(boolean teller) {
        isTeller = teller;
    }
}
