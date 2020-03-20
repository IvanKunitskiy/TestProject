package com.nymbus.models;

import com.nymbus.core.utils.Generator;

import java.util.Objects;

public class CashDrawer {
    private String name;
    private String type;
    private String defaultUser;
    private String branch;
    private String location;
    private String glAccountNumber;
    private boolean isFloating;

    public CashDrawer setDefaultTellerValues(){
        CashDrawer cashDrawer = new CashDrawer();
        cashDrawer.setName(Generator.genString(5));
        cashDrawer.setType("Teller");
        return cashDrawer;
    }

    @Override
    public String toString() {
        return "CashDrawer{" +
                "name='" + name + '\'' +
                ", basicinformation='" + type + '\'' +
                ", defaultUser='" + defaultUser + '\'' +
                ", branch='" + branch + '\'' +
                ", location='" + location + '\'' +
                ", glAccountNumber='" + glAccountNumber + '\'' +
                ", isFloating=" + isFloating +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CashDrawer that = (CashDrawer) o;
        return isFloating == that.isFloating &&
                name.equals(that.name) &&
                type.equals(that.type) &&
                Objects.equals(defaultUser, that.defaultUser) &&
                branch.equals(that.branch) &&
                location.equals(that.location) &&
                glAccountNumber.equals(that.glAccountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, defaultUser, branch, location, glAccountNumber, isFloating);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefaultUser() {
        return defaultUser;
    }

    public void setDefaultUser(String defaultUser) {
        this.defaultUser = defaultUser;
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

    public String getGlAccountNumber() {
        return glAccountNumber;
    }

    public void setGlAccountNumber(String glAccountNumber) {
        this.glAccountNumber = glAccountNumber;
    }

    public boolean isFloating() {
        return isFloating;
    }

    public void setFloating(boolean floating) {
        isFloating = floating;
    }
}
