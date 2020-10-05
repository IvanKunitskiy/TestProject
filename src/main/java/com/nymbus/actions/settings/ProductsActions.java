package com.nymbus.actions.settings;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
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

        return productTypeList.get(new Random().nextInt(productTypeList.size())).trim();
    }
}
