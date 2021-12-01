package com.nymbus.actions.transaction;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.UserCredentials;
import com.nymbus.newmodels.account.product.ProductType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.transaction.MultipleTransaction;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.Denominations;
import com.nymbus.newmodels.transaction.enums.DestinationType;
import com.nymbus.newmodels.transaction.enums.SourceType;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.pages.Pages;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionActions {

    public void goToTellerPage() {
        Pages.aSideMenuPage().clickTellerMenuItem();
        Pages.tellerPage().waitForTransactionSectionVisibility();
    }

    public void doLoginTeller() {
        Pages.tellerModalPage().clickEnterButton();

        if(Pages.tellerModalPage().isModalWindowVisible() && Pages.tellerModalPage().isEnterButtonClickable()) {
            Pages.tellerModalPage().clickEnterButton();
        }

        Pages.tellerModalPage().waitForModalInvisibility();
    }

    public void performCashInMiscCreditTransaction(Transaction transaction) {
        loginTeller();
        goToTellerPage();
       /* doLoginTeller();*/
        createCashInMiscCreditTransaction(transaction);
        clickCommitButton();
        Pages.verifyConductorModalPage().clickVerifyButton();
        Pages.tellerPage().closeModal();
    }

    public void performGLDebitMiscCreditTransaction(Transaction transaction) {
        /*loginTeller();*/
        goToTellerPage();
        doLoginTeller();
        createGlDebitMiscCreditTransaction(transaction);
        clickCommitButtonWithProofDateModalVerification();

        Pages.tellerPage().closeModal();
    }

    public void performMiscDebitGLCreditTransaction(Transaction transaction) {
        loginTeller();
        goToTellerPage();
       /* doLoginTeller();*/
        createMiscDebitGLCreditTransaction(transaction);
        clickCommitButton();
        Pages.tellerPage().closeModal();
    }

    public void performMultipleTransaction(MultipleTransaction transaction) {
        loginTeller();
        goToTellerPage();
       /* doLoginTeller();*/
        createTransactionWithMultipleSources(transaction);
        clickCommitButton();
        if (isCashInOrCashOutTransactionType(transaction)) {
            Pages.verifyConductorModalPage().clickVerifyButton();
        }
        Pages.tellerPage().closeModal();
    }

    private boolean isCashInOrCashOutTransactionType(MultipleTransaction transaction) {
        boolean isCashOperationsInSources = transaction.getSources().stream()
                                            .map(TransactionSource::getSourceType)
                                            .anyMatch(x -> x.equals(SourceType.CASH_IN));
        boolean isCashOperationsInDestinations = transaction.getDestinations().stream()
                                            .map(TransactionDestination::getSourceType)
                                            .anyMatch(x -> x.equals(DestinationType.CASH_OUT));

        return isCashOperationsInSources || isCashOperationsInDestinations;
    }

    private boolean isCashInOrCashOutTransactionType(Transaction transaction) {
        boolean isCashOperationsInSource = transaction.getTransactionSource().getSourceType().equals(SourceType.CASH_IN);
        boolean isCashOperationsInDestination = transaction.getTransactionDestination().getSourceType().equals(DestinationType.CASH_OUT);

        return isCashOperationsInSource || isCashOperationsInDestination;
    }

    private void setTransactionSource(TransactionSource source, int index) {
        switch (source.getSourceType()) {
            case GL_DEBIT:
                setGLDebitSource(source, index);
                break;
            case CASH_IN:
                setCashInSource(source);
                break;
            case MISC_DEBIT:
                setMiscDebitSource(source, index);
                break;
            default:
                break;
        }
    }

    private void setGLDebitSource(TransactionSource source, int index) {
        int tempIndex = 1 + index;
        Pages.tellerPage().clickGLDebitButton();
        fillSourceAccountNumber(source.getAccountNumber(), tempIndex);
        fillSourceAmount(String.format("%.2f", source.getAmount()), tempIndex);
        fillSourceDetails(source.getNotes(), tempIndex);
    }

    private void setTransactionDestination(TransactionDestination destination, int index) {
        switch (destination.getSourceType()) {
            case GL_CREDIT:
                setGLCreditDestination(destination, index);
                break;
            case CASH_OUT:
                setCashOutDestination(destination);
                break;
            case DEPOSIT:
                setDepositDestination(destination, index);
                break;
            case MISC_CREDIT:
                setMiscCreditDestination(destination, index);
                break;
            default:
                break;
        }
    }

    public void createTransactionWithMultipleSources(MultipleTransaction multipleTransaction) {
        setMultipleTransactionSources(multipleTransaction.getSources());
        setMultipleTransactionDestinations(multipleTransaction.getDestinations());
    }

    private void setMultipleTransactionSources(List<TransactionSource> sources) {
        for (int i = 0; i < sources.size(); i++) {
            setTransactionSource(sources.get(i), i);
        }
    }

    private void setMultipleTransactionDestinations(List<TransactionDestination> destinations) {
        for (int i = 0; i < destinations.size(); i++) {
            setTransactionDestination(destinations.get(i), i);
        }
    }

    public void setCashInSource(TransactionSource source) {
        Pages.tellerPage().clickCashInButton();
        setAmounts(source.getDenominationsHashMap());
        Pages.cashInOutModalWindowPage().clickOKButton();
        Pages.cashInOutModalWindowPage().waitForModalWindowInVisibility();
    }

    private void setAmounts(HashMap<Denominations, Double> denominationsHashMap) {
        for (Map.Entry<Denominations,Double> entry : denominationsHashMap.entrySet()) {
            setMappedAmount(entry.getKey(), entry.getValue());
        }
    }

    private void setMappedAmount(Denominations key, Double value) {
        switch (key) {
            case HUNDREDS:
                Pages.cashInOutModalWindowPage().typeHundredsAmountValue(String.format("%.0f", value));
                break;
            case FIFTIES:
                Pages.cashInOutModalWindowPage().typeFiftiesAmountValue(String.format("%.0f", value));
                break;
            case HALFDOLLARS:
                Pages.cashInOutModalWindowPage().typeHalfDollarsAmountValue(String.format("%.0f", value));
                break;
            case QUARTERS:
                Pages.cashInOutModalWindowPage().typeQuartersAmountValue(String.format("%.0f", value));
                break;
            default:
                break;
        }
    }

    public void setMiscDebitSource(TransactionSource source, int index) {
        int tempIndex = 1 + index;
        Pages.tellerPage().clickMiscDebitButton();
        fillSourceAccountNumber(source.getAccountNumber(), tempIndex);
        fillSourceAccountCode(source.getTransactionCode(), tempIndex);
        fillSourceAmount(String.format("%.2f", source.getAmount()), tempIndex);
    }

    public void setGlDebitSource(TransactionSource source, int index) {
        int tempIndex = 1 + index;
        Pages.tellerPage().clickGLDebitButton();
        fillSourceAccountNumber(source.getAccountNumber(), tempIndex);
        fillSourceAccountCode(source.getTransactionCode(), tempIndex);
        fillSourceAmount(String.format("%.2f", source.getAmount()), tempIndex);
        fillSourceDetails(source.getNotes(), tempIndex);
    }

    public void setMiscDebitSourceForWithDraw(TransactionSource source, int index) {
        int tempIndex = 1 + index;
        Pages.tellerPage().clickMiscDebitButton();
        fillSourceAccountNumber(source.getAccountNumber(), tempIndex);
        fillSourceAccountCode(source.getTransactionCode(), tempIndex);
        fillSourceAmount(String.format("%.2f", source.getAmount()), tempIndex);
    }

    public void setWithDrawalSource(TransactionSource source, int index) {
        int tempIndex = 1 + index;
        Pages.tellerPage().clickWithdrawalButton();
        fillSourceAccountNumber(source.getAccountNumber(), tempIndex);
        fillSourceAccountCode(source.getTransactionCode(), tempIndex);
        fillSourceAmount(String.format("%.2f", source.getAmount()), tempIndex);
    }

    public void setCheckSource(TransactionSource source, int index) {
        int tempIndex = 1 + index;
        Pages.tellerPage().clickCheckButton();

        fillCheckSourceAccountCode(source.getTransactionCode(),tempIndex);
        fillSourceAccountNumber(source.getAccountNumber(), tempIndex);
        fillCheckNumber(source.getCheckNumber(), tempIndex);
        fillSourceAmount(String.format("%.2f", source.getAmount()), tempIndex);
    }

    public void setCashOutDestination(TransactionDestination transactionDestination) {
        Pages.tellerPage().clickCashOutButton();
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
//        TestRailAssert.assertTrue(Pages.cashInOutModalWindowPage().isCashMachineRadioButtonChecked(),
//                new CustomStepResult("'Cash Machine' radio button checked", "'Cash Machine' radio button is not checked"));
        setAmounts(transactionDestination.getDenominationsHashMap());
        Pages.cashInOutModalWindowPage().clickOKButton();
    }

    public void setDepositDestination(TransactionDestination transactionDestination, int index) {
        int tempIndex= 1 + index;
        Pages.tellerPage().clickDepositButton();
        fillDestinationAccountNumber(transactionDestination.getAccountNumber(), tempIndex);
        fillDestinationAccountCode(transactionDestination.getTransactionCode(), tempIndex);
        fillDestinationAmount(String.format("%.2f", transactionDestination.getAmount()), tempIndex);
    }

    public void setMiscCreditDestination(TransactionDestination transactionDestination, int index) {
        int tempIndex= 1 + index;
        Pages.tellerPage().clickMiscCreditButton();
        fillDestinationAccountNumber(transactionDestination.getAccountNumber(), tempIndex);
        fillDestinationAccountCode(transactionDestination.getTransactionCode(), tempIndex);
        fillDestinationAmount(String.format("%.2f", transactionDestination.getAmount()), tempIndex);
    }

    public void setGLCreditDestination(TransactionDestination transactionDestination, int index) {
        int tempIndex = index + 1;
        Pages.tellerPage().clickGLCreditButton();
        fillDestinationAccountNumber(transactionDestination.getAccountNumber(), tempIndex);
        fillDestinationAmount(String.format("%.2f", transactionDestination.getAmount()), tempIndex);
        fillDestinationDetails(transactionDestination.getNotes(), tempIndex);
    }

    private void fillDestinationDetails(String notes, int tempIndex) {
        Pages.tellerPage().clickDestinationDetailsArrow(tempIndex);

        Pages.tellerPage().typeDestinationNotesValue(tempIndex, notes);
    }

    public void createCashInMiscCreditTransaction(Transaction transaction) {
        Pages.tellerPage().clickCashInButton();
        Pages.cashInOutModalWindowPage().typeHundredsAmountValue(String.format("%.0f", transaction.getTransactionSource().getAmount()));
        Pages.cashInOutModalWindowPage().clickOKButton();
        Pages.tellerPage().clickMiscCreditButton();
        fillDestinationInformation(transaction.getTransactionDestination());
    }

    public void createGlDebitMiscCreditTransaction(Transaction transaction) {
        Pages.tellerPage().clickGLDebitButton();
//        String text = SelenideTools.switchTo().alert().getText();
//        System.out.println(1+ text);
//        SelenideTools.switchTo().alert().dismiss();
        fillSourceInformation(transaction.getTransactionSource());
        Pages.tellerPage().clickMiscCreditButton();
        fillDestinationInformation(transaction.getTransactionDestination());
    }

    public void createTransitCheckDepositTransaction(Transaction transaction) {
        int tempIndex = 1;
        Pages.tellerPage().clickDepositButton();
        fillDestinationInformation(transaction.getTransactionDestination());
        Pages.tellerPage().clickCheckButton();
        fillSourceDetails(transaction.getTransactionSource().getNotes(), tempIndex);
        Pages.tellerPage().clickSourceDetailsArrow(tempIndex);
        fillRoutingNumber(transaction.getTransactionSource().getRoutingNumber(),tempIndex);
        fillSourceAmount(String.format("%.2f", transaction.getTransactionSource().getAmount()), tempIndex);
        fillCheckNumber(transaction.getTransactionSource().getCheckNumber(), tempIndex);
        Pages.tellerPage().clickCommitButton();
        Pages.confirmModalPage().clickOk();
        Pages.tellerPage().clickAccountNumberDiv(tempIndex);
        Pages.tellerPage().typeAccNumber(tempIndex, transaction.getTransactionSource().getAccountNumber());
        Pages.tellerPage().clickCommitButton();
    }

    public void createMiscDebitGLCreditTransaction(Transaction transaction) {
        int currentIndex = 0;
        setMiscDebitSource(transaction.getTransactionSource(), currentIndex);
        setGLCreditDestination(transaction.getTransactionDestination(), currentIndex);
    }

    public void createWithdrawMiscDebitGLCreditTransaction (Transaction transaction) {
        int currentIndex = 0;
        setMiscDebitSourceForWithDraw(transaction.getTransactionSource(), currentIndex);
        setGLCreditDestination(transaction.getTransactionDestination(), currentIndex);
    }

    public void createWithdrawalGlCreditTransaction(Transaction transaction) {
        int currentIndex = 0;
        setWithDrawalSource(transaction.getTransactionSource(), currentIndex);
        setGLCreditDestination(transaction.getTransactionDestination(), currentIndex);
    }

    public void clickCommitButton() {
        Pages.tellerPage().clickCommitButton();
    }

    public void clickCommitButtonWithProofDateModalVerification() {
        Pages.tellerPage().clickCommitButton();
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        if(Pages.tellerModalPage().isModalWindowVisible()) {
            doLoginTeller();
            Pages.tellerPage().clickCommitButton();
        }
    }

    private void fillDestinationInformation(TransactionDestination transactionDestination) {
        int tempIndex = 1;
        fillDestinationAccountNumber(transactionDestination.getAccountNumber(), tempIndex);
        fillDestinationAccountCode(transactionDestination.getTransactionCode(), tempIndex);
        fillDestinationAmount(String.format("%.2f", transactionDestination.getAmount()), tempIndex);
    }

    public void fillDestinationAccountCode(String transactionCode, int tempIndex) {
        Pages.tellerPage().clickOnDestinationCodeField(tempIndex);

        Pages.tellerPage().clickOnDropDownItem(transactionCode);
    }

    public void fillDestinationAmount(String amount, int tempIndex) {
        Pages.tellerPage().clickDestinationAmountDiv(tempIndex);

        Pages.tellerPage().typeDestinationAmountValue(tempIndex, amount);
    }

    public void fillDestinationAccountNumber(String accountNumber, int tempIndex) {
        Pages.tellerPage().typeDestinationAccountNumber(tempIndex, accountNumber);

        Pages.tellerPage().clickOnAutocompleteDropDownItem(accountNumber);
    }

    private void fillSourceInformation(TransactionSource transactionSource) {
        int tempIndex = 1;
        fillSourceAmount(String.format("%.2f", transactionSource.getAmount()), tempIndex);
        fillSourceAccountNumber(transactionSource.getAccountNumber(), tempIndex);
        fillSourceDetails(transactionSource.getNotes(), tempIndex);
    }

    private void fillSourceDetails(String notes, int tempIndex) {
        Pages.tellerPage().clickSourceDetailsArrow(tempIndex);

        Pages.tellerPage().typeSourceNotesValue(tempIndex, notes);
    }

    private void fillRoutingNumber(String number, int tempIndex) {
        Pages.tellerPage().clickRoutingNumberDiv(tempIndex);

        Pages.tellerPage().typeRoutingNumber(tempIndex, number);
    }

    private void fillCheckNumber(String number, int tempIndex) {
        Pages.tellerPage().clickCheckNumberDiv(tempIndex);

        Pages.tellerPage().typeCheckNumber(tempIndex, number);
    }

    public void fillSourceAmount(String amount, int tempIndex) {
        Pages.tellerPage().clickAmountDiv(tempIndex);

        Pages.tellerPage().typeAmountValue(tempIndex, amount);
    }

    public void fillSourceAccountCode(String transactionCode, int tempIndex) {
        Pages.tellerPage().clickSourceTransactionCodeArrow(tempIndex);

        Pages.tellerPage().clickOnDropDownItem(transactionCode);
    }

    private void fillCheckSourceAccountCode(String transactionCode, int tempIndex) {
        Pages.tellerPage().clickSourceTransactionCode(tempIndex);

        Pages.tellerPage().clickOnDropDownItem(transactionCode);
    }

    public void fillSourceAccountNumber(String accountNumber, int tempIndex) {
        Pages.tellerPage().clickAccountNumberDiv(tempIndex);

        Pages.tellerPage().typeAccountNumber(tempIndex, accountNumber);

        Pages.tellerPage().clickOnAutocompleteDropDownItem(accountNumber);
    }

    public void waitForLoadSpinnerInvisibility() {
        Pages.tellerPage().waitForLoadingSpinnerVisibility();

        Pages.tellerPage().waitForLoadingSpinnerInvisibility();
    }

    public void performTransactionList(List<Transaction> transactions) {
        goToTellerPage();
        if (Pages.tellerModalPage().isModalWindowVisible()) {
            doLoginTeller();
        }
        for (Transaction transaction : transactions) {
            setTransactionSource(transaction.getTransactionSource(), 0);
            setTransactionDestination(transaction.getTransactionDestination(), 0);
            clickCommitButton();

            if (isCashInOrCashOutTransactionType(transaction)) {
                Pages.verifyConductorModalPage().clickVerifyButton();
            }
            Pages.tellerPage().closeModal();
        }
    }

    public void createTransaction(Transaction transaction) {
        int tempIndex = 0;
        setTransactionSource(transaction.getTransactionSource(), tempIndex);
        setTransactionDestination(transaction.getTransactionDestination(), tempIndex);
    }

    public void loginTeller() {
        Pages.navigationPage().clickAccountButton();

        Pages.navigationPage().clickProofDateLogin();

        SelenideTools.sleep(2);

        Pages.tellerModalPage().clickEnterButton();

        Pages.tellerModalPage().waitForModalInvisibility();
    }

    public void openProofDateLoginModalWindow() {
        Pages.navigationPage().clickAccountButton();

        Pages.navigationPage().clickProofDateLogin();

        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
    }

    public void doLoginProofDate() {
        Pages.tellerModalPage().clickEnterButton();

        Pages.tellerModalPage().waitForModalInvisibility();
    }

    public double getAvailableBalance() {
        if(!Pages.tellerPage().isAccountQuickViewVisible()) {
            Pages.tellerPage().clickAccountQuickViewArrow();
        }
        String availableBalanceValue = Pages.tellerPage().getAvailableBalance();
        return Double.parseDouble(availableBalanceValue);
    }

    public double getOriginalBalance() {
        if(!Pages.tellerPage().isAccountQuickViewVisible()) {
            Pages.tellerPage().clickAccountQuickViewArrow();
        }
        String originalBalance = Pages.tellerPage().getOriginalBalance();
        return Double.parseDouble(originalBalance);
    }

    public double getCurrentBalance() {
        if(!Pages.tellerPage().isAccountQuickViewVisible()) {
            Pages.tellerPage().clickAccountQuickViewArrow();
        }
        String currentBalance = Pages.tellerPage().getCurrentBalance();
        return Double.parseDouble(currentBalance);
    }

    public double getAccruedInterest() {
        if(!Pages.tellerPage().isAccountQuickViewVisible()) {
            Pages.tellerPage().clickAccountQuickViewArrow();
        }
        String accruedInterest = Pages.tellerPage().getAccruedInterest();
        return Double.parseDouble(accruedInterest);
    }

    public String getPIFNumber() {
        if(!Pages.tellerPage().isAccountQuickViewVisible()) {
            Pages.tellerPage().clickAccountQuickViewArrow();
        }
        return Pages.tellerPage().getPIFNumber();
    }

    public String getDateOpened() {
        if(!Pages.tellerPage().isAccountQuickViewVisible()) {
            Pages.tellerPage().clickAccountQuickViewArrow();
        }
        return Pages.tellerPage().getDateOpened();
    }

    public String getBoxSize() {
        if(!Pages.tellerPage().isAccountQuickViewVisible()) {
            Pages.tellerPage().clickAccountQuickViewArrow();
        }
        return Pages.tellerPage().getBoxSize();
    }

    public String getRentalAmount() {
        if(!Pages.tellerPage().isAccountQuickViewVisible()) {
            Pages.tellerPage().clickAccountQuickViewArrow();
        }
        return Pages.tellerPage().getRentalAmount();
    }

    public String getAccountType() {
        if(!Pages.tellerPage().isAccountQuickViewVisible()) {
            Pages.tellerPage().clickAccountQuickViewArrow();
        }
        return Pages.tellerPage().getAccountType();
    }

    public String getProductType() {
        if(!Pages.tellerPage().isAccountQuickViewVisible()) {
            Pages.tellerPage().clickAccountQuickViewArrow();
        }
        return Pages.tellerPage().getProductType();
    }

    public String getAutomaticOverdraftLimit() {
        if(!Pages.tellerPage().isAccountQuickViewVisible()) {
            Pages.tellerPage().clickAccountQuickViewArrow();
        }
        return Pages.tellerPage().getAutomaticOverdraftLimit().trim();
    }

    public String getFirstAutomaticOverdraftLimit() {
        if(!Pages.tellerPage().isAccountQuickViewVisible()) {
            Pages.tellerPage().clickAccountQuickViewArrow();
        }
        return Pages.tellerPage().getFirstAutomaticOverdraftLimit().trim();
    }

    public double getPayoffAmount() {
        if(!Pages.tellerPage().isAccountQuickViewVisible()) {
            Pages.tellerPage().clickAccountQuickViewArrow();
        }
        return Double.parseDouble(Pages.tellerPage().getPayoffAmount());
    }

    public String getLateChargesDue() {
        if(!Pages.tellerPage().isAccountQuickViewVisible()) {
            Pages.tellerPage().clickAccountQuickViewArrow();
        }
        return Pages.tellerPage().getLateChargesDue();
    }

    public double getTotalPastDue() {
        if(!Pages.tellerPage().isAccountQuickViewVisible()) {
            Pages.tellerPage().clickAccountQuickViewArrow();
        }
        return Double.parseDouble(Pages.tellerPage().getTotalPastDue());
    }

    public double getPrincipalNextDue() {
        if(!Pages.tellerPage().isAccountQuickViewVisible()) {
            Pages.tellerPage().clickAccountQuickViewArrow();
        }
        return Double.parseDouble(Pages.tellerPage().getPrincipalNextDue());
    }

    public double getInterestNextDue() {
        if(!Pages.tellerPage().isAccountQuickViewVisible()) {
            Pages.tellerPage().clickAccountQuickViewArrow();
        }
        return Double.parseDouble(Pages.tellerPage().getInterestNextDue());
    }

    public double getTotalNextDue() {
        if(!Pages.tellerPage().isAccountQuickViewVisible()) {
            Pages.tellerPage().clickAccountQuickViewArrow();
        }
        return Double.parseDouble(Pages.tellerPage().getTotalNextDue());
    }

    public String getCurrentDateDue() {
        if(!Pages.tellerPage().isAccountQuickViewVisible()) {
            Pages.tellerPage().clickAccountQuickViewArrow();
        }
        return Pages.tellerPage().getCurrentDateDue();
    }

    public String getLoanClassCode() {
        if(!Pages.tellerPage().isAccountQuickViewVisible()) {
            Pages.tellerPage().clickAccountQuickViewArrow();
        }
        return Pages.tellerPage().getLoanClassCode();
    }

    public double getPaymentAmount() {
        if(!Pages.tellerPage().isAccountQuickViewVisible()) {
            Pages.tellerPage().clickAccountQuickViewArrow();
        }
        return Double.parseDouble(Pages.tellerPage().getPaymentAmount());
    }

    public boolean isTransactionCodePresent(String transCode) {
        boolean result = false;
        int transactionItems = Pages.accountTransactionPage().getTransactionItemsCount();
        for (int i = 1; i <= transactionItems; ++i) {
            if (Pages.accountTransactionPage().getTransactionCodeByIndex(i).contains(transCode)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean isTransactionCodePresent(String transCode, int offset) {
        boolean result = false;
        int transactionItems = Pages.accountTransactionPage().getTransactionItemsCount();
        for (int i = 1; i <= transactionItems; ++i) {
            if (Pages.accountTransactionPage().getTransactionCodeByIndex(i, offset).contains(transCode)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean isATMTransactionCodePresent(String transCode) {
        boolean result = false;
        int transactionItems = Pages.accountTransactionPage().getTransactionItemsCount();
        for (int i = 1; i <= transactionItems; ++i) {
            if (Pages.accountTransactionPage().getTransactionCodeByIndex1(i).contains(transCode)) {
                result = true;
            }
        }
        return result;
    }

    public void verifyPreSelectedClientFields(IndividualClient client) {
        SoftAssert asert = new SoftAssert();

        asert.assertEquals(Pages.verifyConductorModalPage().getFirstNameValue(), client.getIndividualType().getFirstName(),
                "Client's First name is incorrect !");
        asert.assertEquals(Pages.verifyConductorModalPage().getLastNameValue(), client.getIndividualType().getLastName(),
                "Client's Last name is incorrect !");
        asert.assertEquals(Pages.verifyConductorModalPage().getTaxIDValue(), client.getIndividualType().getTaxID(),
                "Client's TAX Id is incorrect !");
        asert.assertEquals(Pages.verifyConductorModalPage().getOccupationValue(), client.getIndividualClientDetails().getOccupation(),
                "Client's occupation is incorrect !");
        asert.assertEquals(Pages.verifyConductorModalPage().getBirthDateValue(), client.getIndividualType().getBirthDate(),
                "Client's Birth Date is incorrect !");
        asert.assertEquals(Pages.verifyConductorModalPage().getAddressValue(), client.getIndividualType().getAddresses().get(0).getAddress(),
                "Client's Address is incorrect !");
        asert.assertEquals(Pages.verifyConductorModalPage().getCityValue(), client.getIndividualType().getAddresses().get(0).getCity(),
                "Client's City is incorrect !");
        asert.assertEquals(Pages.verifyConductorModalPage().getZipCodeValue(), client.getIndividualType().getAddresses().get(0).getZipCode(),
                "Client's Zip Code is incorrect !");
        asert.assertEquals(Pages.verifyConductorModalPage().getPhoneValue(), client.getIndividualClientDetails().getPhones().get(1).getPhoneNumber(),
                "Client's Phone value is incorrect!");
        asert.assertAll();
    }

    public String getImageSrcFromPopup() {
        Pages.tellerPage().waitForPrintReceipt();
        Pages.tellerPage().waitForPopupSpinnerInvisibility();

        return Pages.tellerPage().getPopupImg();
    }

    public void clickCheckBoxOnReceiptPopUp() {
        Pages.tellerPage().clickPrintBalancesOnReceiptCheckbox();
        Pages.tellerPage().waitForPrintReceipt();
        Pages.tellerPage().waitForPopupSpinnerInvisibility();
    }

    public void setTransactionCode(Transaction transaction) {
        if (Pages.accountDetailsPage().getProductTypeValue().equals(ProductType.CHK_ACCOUNT.getProductType())) {
            transaction.getTransactionDestination().setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        }
    }

    public void fillingSupervisorModal(UserCredentials userCredentials) {
        Pages.supervisorModalPage().inputJsLogin(userCredentials.getUserName());
        Pages.supervisorModalPage().inputJsPassword(userCredentials.getPassword());
        Pages.supervisorModalPage().clickJsEnter();
        Pages.supervisorModalPage().waitForModalWindowInvisibility();
    }

    public void confirmTransaction(){
        Pages.confirmModalPage().clickYes();
    }

    public void inputAccountNumber(String accountNumber) {
        Pages.tellerPage().clickAccountNumberDiv(1);
        Pages.tellerPage().typeAccountNumber(1,accountNumber);
        Pages.tellerPage().clickOnAutocompleteDropDownItem(accountNumber);
    }
}