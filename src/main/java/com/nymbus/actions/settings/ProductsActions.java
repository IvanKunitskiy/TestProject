package com.nymbus.actions.settings;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.other.account.InterestFrequency;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;

import java.util.List;
import java.util.Random;

public class ProductsActions {

    public void expandAllRows() {
        while(SettingsPage.productsOverviewPage().isLoadMoreResultsButtonVisible()) {
            SettingsPage.productsOverviewPage().clickLoadMoreResultsButton();
            SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        }
    }

    public String getProduct(Products products, AccountType accountType, RateType rateType) {
        String aType = accountType.getAccountType();
        String rType = rateType.getRateType();

        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().clickViewAllProducts();
        SettingsPage.productsOverviewPage().clickProductTypeSelectorButton();
        SettingsPage.productsOverviewPage().clickProductTypeOption(products.getProduct());
        SettingsPage.productsOverviewPage().waitForResultsIsVisible();
        expandAllRows();
        List<String> productTypeList = SettingsPage.productsOverviewPage().getAccountDescriptionsByAccountType(aType, rType);

        return productTypeList.get(new Random().nextInt(productTypeList.size()));
    }

    public String getProduct(Products products, String initials) {
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().clickViewAllProducts();
        SettingsPage.productsOverviewPage().clickProductTypeSelectorButton();
        SettingsPage.productsOverviewPage().clickProductTypeOption(products.getProduct());
        SettingsPage.productsOverviewPage().waitForResultsIsVisible();
        expandAllRows();
        List<String> productTypeList = SettingsPage.productsOverviewPage().getAccountDescriptionsByInitials(initials);

        return productTypeList.get(new Random().nextInt(productTypeList.size()));
    }

    public String getMinTerm(Products products, Account account) {
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().clickViewAllProducts();
        SettingsPage.productsOverviewPage().clickProductTypeSelectorButton();
        SettingsPage.productsOverviewPage().clickProductTypeOption(products.getProduct());
        SettingsPage.productsOverviewPage().waitForResultsIsVisible();

        expandAllRows();
        SettingsPage.productsOverviewPage().clickRowByDescription(account.getProduct());
        return SettingsPage.productOverviewPage().getMinTerm();
    }

    public String getTermType(Products products, Account account) {
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().clickViewAllProducts();
        SettingsPage.productsOverviewPage().clickProductTypeSelectorButton();
        SettingsPage.productsOverviewPage().clickProductTypeOption(products.getProduct());
        SettingsPage.productsOverviewPage().waitForResultsIsVisible();

        expandAllRows();
        SettingsPage.productsOverviewPage().clickRowByDescription(account.getProduct());
        return SettingsPage.productOverviewPage().getTermType();
    }

    public String getInterestType(Products products, Account account) {
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().clickViewAllProducts();
        SettingsPage.productsOverviewPage().clickProductTypeSelectorButton();
        SettingsPage.productsOverviewPage().clickProductTypeOption(products.getProduct());
        SettingsPage.productsOverviewPage().waitForResultsIsVisible();

        expandAllRows();
        SettingsPage.productsOverviewPage().clickRowByDescription(account.getProduct());
        return SettingsPage.productOverviewPage().getInterestType();
    }

    public String getInterestFrequency(Products products, Account account) {
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().clickViewAllProducts();
        SettingsPage.productsOverviewPage().clickProductTypeSelectorButton();
        SettingsPage.productsOverviewPage().clickProductTypeOption(products.getProduct());
        SettingsPage.productsOverviewPage().waitForResultsIsVisible();

        expandAllRows();
        SettingsPage.productsOverviewPage().clickRowByDescription(account.getProduct());
        return SettingsPage.productOverviewPage().getInterestFrequency();
    }

    public String getInterestRate(Products products, Account account) {
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().clickViewAllProducts();
        SettingsPage.productsOverviewPage().clickProductTypeSelectorButton();
        SettingsPage.productsOverviewPage().clickProductTypeOption(products.getProduct());
        SettingsPage.productsOverviewPage().waitForResultsIsVisible();

        expandAllRows();
        SettingsPage.productsOverviewPage().clickRowByDescription(account.getProduct());
        return SettingsPage.productOverviewPage().getInterestRate();
    }

    public String getMaturityDateValue(Account account, int minTerm) {
        String maturityDate = "";

        if (account.getTermType().equals("Days")) {
            maturityDate = DateTime.getDateWithFormatPlusDays(account.getDateOpened(), "MM/dd/yyyy", "MM/dd/yyyy", minTerm);
        } else if (account.getTermType().equals("Months")) {
            maturityDate = DateTime.getDateWithNMonthAdded(account.getDateOpened(), "MM/dd/yyyy", minTerm);
        }
        return maturityDate;
    }

    public String getDateNextInterestValue(Account account) {
        String interestFrequencyValue = "";

        if (account.getInterestFrequency().equals(InterestFrequency.MONTHLY.getInterestFrequency())) {
            interestFrequencyValue = DateTime.getDateWithNMonthAdded(account.getDateOpened(), "MM/dd/yyyy", 1);
        } else if (account.getInterestFrequency().equals(InterestFrequency.ANNUAL.getInterestFrequency())) {
            interestFrequencyValue = DateTime.getDatePlusYearsWithFormat(1, "MM/dd/yyyy");
        } else if (account.getInterestFrequency().equals(InterestFrequency.ONE_TIME_PAY.getInterestFrequency())) {
            interestFrequencyValue = account.getMaturityDate();
        }
        return interestFrequencyValue;
    }
}
