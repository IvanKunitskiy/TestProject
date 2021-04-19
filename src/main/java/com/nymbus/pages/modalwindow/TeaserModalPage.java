package com.nymbus.pages.modalwindow;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class TeaserModalPage extends PageTools {

    private final By effectiveDateLabel = By.xpath("//label[text()=' Effective Date ']");
    private final By teaserRateLabel = By.xpath("//label[text()=' Teaser Rate Expire Date ']");
    private final By teaserRateChangeLabel = By.xpath("//label[text()=' Teaser Rate Change Type ']");
    private final By noteRateLabel = By.xpath("//label[text()=' Note Rate ']");
    private final By rateChangeLeadDaysLabel = By.xpath("//label[text()=' Rate Change Lead Days ']");
    private final By minRateLabel = By.xpath("//label[text()=' Min Rate ']");
    private final By maxRateLabel = By.xpath("//label[text()=' Max Rate ']");
    private final By maxRateChangeUpDownLabel = By.xpath("//label[text()=' Max Rate Change Up/Down ']");
    private final By rateIndexLabel = By.xpath("//label[text()=' Rate Index ']");
    private final By rateRoundingFactorLabel = By.xpath("//label[text()=' Rate Rounding Factor ']");
    private final By rateMarginLabel = By.xpath("//label[text()=' Rate Margin ']");
    private final By rateRoundingMethodLabel = By.xpath("//label[text()=' Rate Rounding Method ']");
    private final By rateIndexArrow = By.xpath("//div[@id='ratechangetype']//b");
    private final By rateIndexInput = By.xpath("//div[@id='rateindex']");
    private final By noteRateOption = By.xpath("//span[text()='Note Rate']");
    private final By requiredStars = By.xpath("//i[@class='nyb-icon-required']");
    private final By rateMarginInput = By.xpath("//input[@id='ratemargin']");
    private final By minRateInput = By.xpath("//input[@id='minrate']");
    private final By maxRateInput = By.xpath("//input[@id='maxrate']");
    private final By maxRateChangeInput = By.xpath("//input[@id='maxratechangeupdown']");
    private final By rateRoundingFactorInput = By.xpath("//input[@id='rateroundingfactor']");
    private final By rateRoundingMethodInput = By.xpath("//div[@id='rateroundingmethod']");
    private final By effectiveDateInput = By.xpath("//input[@id='efffectivedate']");
    private final By expirationDateInput = By.xpath("//input[@id='expirationdate']");
    private final By noteRateInput = By.xpath("//input[@id='noterate']");
    private final By rateChangeLeadDaysInput = By.xpath("//input[@id='ratechangeleaddays']");
    private final By doneButton = By.xpath("//button[text()='Done']");


    @Step("Check Rate Rounding Method label")
    public void checkRateRoundingMethodLabel() {
        waitForElementVisibility(rateRoundingMethodLabel);
    }

    @Step("Check Rate Margin label")
    public void checkRateMarginLabel() {
        waitForElementVisibility(rateMarginLabel);
    }

    @Step("Check Rate Rounding Factor label")
    public void checkRateRoundingFactorLabel() {
        waitForElementVisibility(rateRoundingFactorLabel);
    }

    @Step("Check Rate Index label")
    public void checkRateIndexLabel() {
        waitForElementVisibility(rateIndexLabel);
    }

    @Step("Check Max Rate Change UpDown label")
    public void checkMaxRateChangeUpDownLabel() {
        waitForElementVisibility(maxRateChangeUpDownLabel);
    }

    @Step("Check Max Rate Label")
    public void checkMaxRateLabel() {
        waitForElementVisibility(maxRateLabel);
    }

    @Step("Check Min Rate Label")
    public void checkMinRateLabel() {
        waitForElementVisibility(minRateLabel);
    }

    @Step("Check Rate Change Lead Days label")
    public void checkRateChangeLeadDaysLabel() {
        waitForElementVisibility(rateChangeLeadDaysLabel);
    }

    @Step("Check Note Rate label")
    public void checkNoteRateLabel() {
        waitForElementVisibility(noteRateLabel);
    }

    @Step("Check Teaser Rate Change label")
    public void checkTeaserRateChangeLabel() {
        waitForElementVisibility(teaserRateChangeLabel);
    }

    @Step("Check Teaser Rate label")
    public void checkTeaserRateLabel() {
        waitForElementVisibility(teaserRateLabel);
    }

    @Step("Check Effective Date label")
    public void checkEffectiveDateLabel() {
        waitForElementVisibility(effectiveDateLabel);
    }

    @Step("Clock Rate Change Type arrow")
    public void clickChangeRateArrow() {
        waitForElementVisibility(rateIndexArrow);
        waitForElementClickable(rateIndexArrow);
        click(rateIndexArrow);
    }

    @Step("Clock Note Rate Option arrow")
    public void clickNoteRateOption() {
        waitForElementVisibility(noteRateOption);
        waitForElementClickable(noteRateOption);
        click(noteRateOption);
    }

    @Step("Get count required stars")
    public int getCountRequiredStars() {
        waitForElementVisibility(requiredStars);
        return getElements(requiredStars).size();
    }

    @Step("Check Rate Index is disabled")
    public void checkRateIndexIsDisabled() {
        waitForElementVisibility(rateIndexInput);
        getDisabledElementAttributeValue("disabled", rateIndexInput);
    }

    @Step("Check Rate Margin is disabled")
    public void checkRateMarginIsDisabled() {
        waitForElementVisibility(rateMarginInput);
        getDisabledElementAttributeValue("disabled", rateMarginInput);
    }

    @Step("Check Min Rate is disabled")
    public void checkMinRateIsDisabled() {
        waitForElementVisibility(minRateInput);
        getDisabledElementAttributeValue("disabled", minRateInput);
    }

    @Step("Check Max Rate is disabled")
    public void checkMaxRateIsDisabled() {
        waitForElementVisibility(maxRateInput);
        getDisabledElementAttributeValue("disabled", maxRateInput);
    }

    @Step("Check Max Rate Change is disabled")
    public void checkMaxRateChangeIsDisabled() {
        waitForElementVisibility(maxRateChangeInput);
        getDisabledElementAttributeValue("disabled", maxRateChangeInput);
    }

    @Step("Check Rate Rounding Factor is disabled")
    public void checkRateRoundingFactorIsDisabled() {
        waitForElementVisibility(rateRoundingFactorInput);
        getDisabledElementAttributeValue("disabled", rateRoundingFactorInput);
    }

    @Step("Check Rate Rounding Method is disabled")
    public void checkRateRoundingMethodIsDisabled() {
        waitForElementVisibility(rateRoundingMethodInput);
        getDisabledElementAttributeValue("disabled", rateRoundingMethodInput);
    }

    @Step("Input effective date value")
    public void inputEffectiveDate(String effectiveDate) {
        waitForElementVisibility(effectiveDateInput);
        type(effectiveDate, effectiveDateInput);
    }

    @Step("Input expiration date value")
    public void inputExpirationDate(String expirationDate) {
        waitForElementVisibility(expirationDateInput);
        type(expirationDate, expirationDateInput);
    }

    @Step("Input Note Rate value")
    public void inputNoteRate(String noteRate) {
        waitForElementVisibility(noteRateInput);
        type(noteRate, noteRateInput);
    }

    @Step("Input Rate Change Lead Days value")
    public void inputRateChangeLeadDays(String rateChangeLeadDays) {
        waitForElementVisibility(rateChangeLeadDaysInput);
        type(rateChangeLeadDays, rateChangeLeadDaysInput);
    }

    @Step("Click Done Button")
    public void clickDoneButton() {
        waitForElementVisibility(doneButton);
        click(doneButton);
    }


}
