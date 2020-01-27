package com.nymbus.pages.settings;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class MainPage extends BasePage {

    // Users
    private Locator userRegion = new XPath("//div[div[h2[text()='Users']]]");
    private Locator addNewUserLink = new XPath("//div[div[h2[text()='Users']]]//span");
    private Locator searchUserField = new XPath("//div[div[h2[text()='Users']]]//form/input[@name='username']");
    private Locator searchUserButton = new XPath("//div[div[h2[text()='Users']]]//form/button");
    private Locator viewAllUsersLink = new XPath("//div[div[h2[text()='Users']]]/div[@class='footer']//a");

     @Step("Waiting 'Users' region")
     public void waitForUserRegion(){
         waitForElementVisibility(userRegion);
     }

     @Step("Click 'Add New' link")
     public void clickAddNewUserLink(){
         waitForElementClickable(addNewUserLink);
         click(addNewUserLink);
     }

     @Step("Setting 'Username' to search field")
     public void setUserNameToSearchField(String userName){
         waitForElementClickable(searchUserField);
         type(userName, searchUserField);
     }

     @Step("Click 'Search' users button")
     public void clickSearchUserButton(){
         waitForElementClickable(searchUserButton);
         click(searchUserButton);
     }

     @Step("Click 'View all users' link")
     public void clickViewAllUsersLink(){
         waitForElementClickable(viewAllUsersLink);
         click(viewAllUsersLink);
     }

}
