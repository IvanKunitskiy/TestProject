package com.nymbus.actions.account;

import com.nymbus.actions.Actions;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.account.type.CHKAccount;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class CreateAccount {

    private void setBasicAccountFields(Account account) {
        setAddNewOption(account);
        setProductType(account);
        setProduct(account);
        Pages.addAccountPage().setAccountNumberValue(account.getAccountNumber());
    }

    public void createCDAccount(Account account) {
        Pages.clientDetailsPage().clickAccountsTab();
        Actions.clientPageActions().closeAllNotifications();
        setBasicAccountFields(account);
        selectValuesInFieldsRequiredForCDAccount(account);
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createIRAAccount(Account account) {
        Pages.clientDetailsPage().clickAccountsTab();
        setBasicAccountFields(account);
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        selectValuesInDropdownFieldsRequiredForSavingsIRAAccount(account);
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createSavingsAccount(Account account) {
        Pages.clientDetailsPage().clickAccountsTab();
        Actions.clientPageActions().closeAllNotifications();
        setBasicAccountFields(account);
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        setCurrentOfficer(account);
        setBankBranch(account);
        setMailCode(account);
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        // Pages.addAccountPage().setInterestRate(account.getInterestRate());
        setStatementCycle(account);
        //TODO ucom
        //setCallClassCode(account);
        setInterestFrequency(account);
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createSavingAccountForTransactionPurpose(Account account) {
        Pages.clientDetailsPage().clickAccountsTab();
        Actions.clientPageActions().closeAllNotifications();
        setBasicAccountFields(account);
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        setCurrentOfficer(account);
        setBankBranch(account);
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        // Pages.addAccountPage().setInterestRate(account.getInterestRate());
        setStatementCycle(account);
        setCallClassCode(account);
        Pages.addAccountPage().waitForAccountHolderName();
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createCDAccountForTransactionPurpose(Account account) {
        Pages.clientDetailsPage().clickAccountsTab();
        Actions.clientPageActions().closeAllNotifications();
        Pages.clientDetailsPage().clickAddNewButton();
        Pages.clientDetailsPage().clickAddNewValueOption(account.getAddNewOption());
        Pages.addAccountPage().clickProductTypeSelectorButton();
        Pages.addAccountPage().clickProductTypeOption(account.getProductType());
        Pages.addAccountPage().clickProductSelectorButton();
        Pages.addAccountPage().clickProductOption(account.getProduct());
        Pages.addAccountPage().setAccountNumberValue(account.getAccountNumber());
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        Pages.addAccountPage().setInterestRate(account.getInterestRate());
        setApplyInterestTo(account);
        Pages.addAccountPage().waitForAccountHolderName();
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createCHKAccountForTransactionPurpose(Account account) {
        Pages.clientDetailsPage().clickAccountsTab();
        Actions.clientPageActions().closeAllNotifications();
        setBasicAccountFields(account);
        System.out.println(account.getDateOpened());
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        Pages.addAccountPage().setInterestRate(account.getInterestRate());
        setStatementCycle(account);
        Pages.addAccountPage().waitForAccountHolderName();
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createCHKAccount(Account account) {
        Pages.clientDetailsPage().clickAccountsTab();
        Actions.clientPageActions().closeAllNotifications();
        setBasicAccountFields(account);
        setValuesInFieldsRequiredForCheckingAccount(account);
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createCHKAccountForDormantPurpose(Account account) {
        Pages.clientDetailsPage().clickAccountsTab();
        Actions.clientPageActions().closeAllNotifications();
        setBasicAccountFields(account);
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        setCurrentOfficer(account);
        setBankBranch(account);
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        Pages.addAccountPage().setInterestRate(account.getInterestRate());
        setStatementCycle(account);
        Pages.addAccountPage().setOptInOutDateValue(account.getOptInOutDate());
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createCHKAccount(CHKAccount account) {
        Pages.clientDetailsPage().clickAccountsTab();
        setAddNewOption(account);
        setProductType(account);
        setProduct(account);
        Pages.addAccountPage().setAccountNumberValue(account.getAccountNumber());
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        setCurrentOfficer(account);
        setBankBranch(account);
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        Pages.addAccountPage().setInterestRate(account.getInterestRate());
        setStatementCycle(account);
        setChargeOrAnalyze(account);
        setAccountAnalysis(account);
        Pages.addAccountPage().setOptInOutDateValue(account.getOptInOutDate());
        setCallClassCode(account);
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void createSafeDepositBoxAccount(Account account) {
        Pages.clientDetailsPage().clickAccountsTab();
        setAddNewOption(account);
        setProductType(account);
        setBoxSize(account);
        Pages.addAccountPage().setAccountNumberValue(account.getAccountNumber());
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        selectValuesInDropdownFieldsRequiredForSafeDepositBoxAccount(account);
        fillInInputFieldsRequiredForSafeDepositBoxAccount(account);
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    public void setValuesInFieldsRequiredForSavingsAccount(Account account) {
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        setCurrentOfficer(account);
        setBankBranch(account);
        setMailCode(account);
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        Pages.addAccountPage().setInterestRate(account.getInterestRate());
        setInterestFrequency(account);
        setCorrespondingAccount(account);
        setStatementCycle(account);
        setCallClassCode(account);
    }

    public void selectValuesInDropdownFieldsRequiredForSavingsIRAAccount(Account account) {
        setCurrentOfficer(account);
        setBankBranch(account);
        setInterestFrequency(account);
        setStatementCycle(account);
        setCorrespondingAccount(account);
        setCallClassCode(account);
        setIRADistributionFrequency(account);
        setIRADistributionCode(account);
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        Pages.addAccountPage().setIRADistributionAmountValue(account.getIraDistributionAmount());
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        Pages.addAccountPage().setDateNextIRADistributionValue(account.getDateNextIRADistribution());
        Pages.addAccountPage().setDateOfFirstDepositValue(account.getDateOfFirstDeposit());
    }

    public void setValuesInFieldsRequiredForCDIRAAccount(Account account) {
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        setCurrentOfficer(account);
        setBankBranch(account);
        setCorrespondingAccount(account);
        setInterestFrequencyCode(account);
        setApplyInterestTo(account);
        setInterestType(account);
        setCallClassCode(account);
        setIRADistributionFrequency(account);
        setIRADistributionCode(account);
        Pages.addAccountPage().setIRADistributionAmountValue(account.getIraDistributionAmount());
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        Pages.addAccountPage().setDateNextIRADistributionValue(account.getDateNextIRADistribution());
        Pages.addAccountPage().setDateOfFirstDepositValue(account.getDateOfFirstDeposit());
        if (Pages.addAccountPage().getTransactionalAccountSwitchValue().equals("no")) {
            Pages.addAccountPage().clickTransactionalAccountSwitch();
        }
        if (Pages.addAccountPage().getAutoRenewableSwitchValue().equals("yes")) {
            Pages.addAccountPage().clickAutoRenewableSwitch();
        }
        if (Pages.addAccountPage().getApplySeasonalAddressSwitchValue().equals("yes")) {
            Pages.addAccountPage().clickApplySeasonalAddressSwitch();
        }
    }

    public void selectValuesInDropdownFieldsRequiredForSafeDepositBoxAccount(Account account) {
        setCurrentOfficer(account);
        setBankBranch(account);
        setCorrespondingAccount(account);
        /*setDiscountReason(account);*/
    }

    public void setValuesInFieldsRequiredForCheckingAccount(Account account) {
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        setCurrentOfficer(account);
        setBankBranch(account);
        Pages.addAccountPage().setInterestRate(account.getInterestRate());
        setStatementCycle(account);
        setChargeOrAnalyze(account);
        setAccountAnalysis(account);
        Pages.addAccountPage().setOptInOutDateValue(account.getOptInOutDate());
        setCallClassCode(account);
    }

    public void selectValuesInFieldsRequiredForCDAccount(Account account) {
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        setCurrentOfficer(account);
        setBankBranch(account);
        Pages.addAccountPage().setDateOpenedValue(account.getDateOpened());
        setInterestFrequencyCode(account);
        setApplyInterestTo(account);
        Pages.addAccountPage().setInterestRate(account.getInterestRate());
        setInterestType(account);
        setCorrespondingAccount(account);
        setCallClassCode(account);
        Pages.addAccountPage().clickTransactionalAccountSwitch();
    }

    public void fillInInputFieldsRequiredForSafeDepositBoxAccount(Account account) {
        Pages.addAccountPage().setAccountTitleValue(account.getAccountTitle());
        Pages.addAccountPage().setUserDefinedField_1(account.getUserDefinedField_1());
        Pages.addAccountPage().setUserDefinedField_2(account.getUserDefinedField_2());
        Pages.addAccountPage().setUserDefinedField_3(account.getUserDefinedField_3());
        Pages.addAccountPage().setUserDefinedField_4(account.getUserDefinedField_4());
        Pages.addAccountPage().setDiscountPeriods(account.getDiscountPeriods());
    }

    public void setDiscountReason(Account account) {
        Pages.addAccountPage().clickDiscountReasonSelectorButton();
        List<String> listOfDiscountReason = Pages.addAccountPage().getDiscountReasonList();

        Assert.assertTrue(listOfDiscountReason.size() > 0, "There are no 'Discount Reasons' options available");
        if (account.getDiscountReason() == null) {
            account.setDiscountReason(listOfDiscountReason.get(new Random().nextInt(listOfDiscountReason.size())).trim());
        }
        Pages.addAccountPage().clickDiscountReasonSelectorOption(account.getDiscountReason());
    }

    public void setInterestType(Account account) {
        Pages.addAccountPage().clickInterestTypeSelectorButton();
        List<String> listOfInterestType = Pages.addAccountPage().getInterestTypeList();

        Assert.assertTrue(listOfInterestType.size() > 0, "There are no 'Interest Types' options available");
        if (account.getInterestType() == null) {
            account.setInterestType(listOfInterestType.get(new Random().nextInt(listOfInterestType.size())).trim());
        }
        Pages.addAccountPage().clickInterestTypeSelectorOption(account.getInterestType());
    }

    public void setApplyInterestTo(Account account) {
        Pages.addAccountPage().clickApplyInterestToSelectorButton();
        List<String> listOfApplyInterestTo = Pages.addAccountPage().getApplyInterestToList();

        Assert.assertTrue(listOfApplyInterestTo.size() > 0, "There are no 'Apply Interest To' options available");
        if (account.getApplyInterestTo() == null) {
            account.setApplyInterestTo(listOfApplyInterestTo.get(new Random().nextInt(listOfApplyInterestTo.size())).trim());
        }
        Pages.addAccountPage().clickApplyInterestToSelectorOption(account.getApplyInterestTo());
    }

    public void setIRADistributionFrequency(Account account) {
        Pages.addAccountPage().clickIRADistributionFrequencySelectorButton();
        List<String> listOfIRADistributionFrequency = Pages.addAccountPage().getIRADistributionFrequencyList();

        Assert.assertTrue(listOfIRADistributionFrequency.size() > 0, "There are no 'Ira Distribution Frequency' options available");
        if (account.getIraDistributionFrequency() == null) {
            account.setIraDistributionFrequency(listOfIRADistributionFrequency.get(new Random().nextInt(listOfIRADistributionFrequency.size())).trim());
        }
        Pages.addAccountPage().clickIRADistributionFrequencySelectorOption(account.getIraDistributionFrequency());
    }

    public void setIRADistributionCode(Account account) {
        Pages.addAccountPage().clickIRADistributionCodeSelectorButton();
        List<String> listOfIRADistributionCode = Pages.addAccountPage().getIRADistributionCodeList();

        Assert.assertTrue(listOfIRADistributionCode.size() > 0, "There are no 'Ira Distribution Code' options available");
        if (account.getIraDistributionCode() == null) {
            account.setIraDistributionCode(listOfIRADistributionCode.get(new Random().nextInt(listOfIRADistributionCode.size())).trim());
        }
        Pages.addAccountPage().clickIRADistributionCodeSelectorOption(account.getIraDistributionCode());
    }

    public void setCorrespondingAccount(Account account) {
        Pages.addAccountPage().clickCorrespondingAccountSelectorButton();
        List<String> listOfCorrespondingAccount = Pages.addAccountPage().getCorrespondingAccountList();

        if (listOfCorrespondingAccount.size() > 0) {
            if (account.getCorrespondingAccount() == null) {
                account.setCorrespondingAccount(listOfCorrespondingAccount.get(new Random().nextInt(listOfCorrespondingAccount.size())).trim());
            }
            Pages.addAccountPage().clickCorrespondingAccountSelectorOption(account.getCorrespondingAccount().replaceAll("[^0-9]", ""));
        }
    }

    public void setInterestFrequency(Account account) {
        Pages.addAccountPage().clickInterestFrequencySelectorButton();
        List<String> listOfInterestFrequency = Pages.addAccountPage().getInterestFrequencyList();

        Assert.assertTrue(listOfInterestFrequency.size() > 0, "There are no 'Interest Frequency' options available");
        if (account.getInterestFrequency() == null) {
            account.setInterestFrequency(listOfInterestFrequency.get(new Random().nextInt(listOfInterestFrequency.size())).trim());
        }
        Pages.addAccountPage().clickInterestFrequencySelectorOption(account.getInterestFrequency());
    }

    public void setInterestFrequencyCode(Account account) {
        Pages.addAccountPage().clickInterestFrequencyCodeSelectorButton();
        List<String> listOfInterestFrequency = Pages.addAccountPage().getInterestFrequencyCodeList();

        Assert.assertTrue(listOfInterestFrequency.size() > 0, "There are no 'Interest Frequency Code' options available");
        if (account.getInterestFrequency() == null) {
            account.setInterestFrequency(listOfInterestFrequency.get(new Random().nextInt(listOfInterestFrequency.size())).trim());
        }
        Pages.addAccountPage().clickInterestFrequencyCodeSelectorOption(account.getInterestFrequency());
    }

    public void setChargeOrAnalyze(Account account) {
        Pages.addAccountPage().clickChargeOrAnalyzeSelectorButton();
        List<String> listOfChargeOrAnalyze = Pages.addAccountPage().getChargeOrAnalyzeList();

        Assert.assertTrue(listOfChargeOrAnalyze.size() > 0, "There are no 'Charge Or Analyze' options available");
        if (account.getChargeOrAnalyze() == null) {
            account.setChargeOrAnalyze(listOfChargeOrAnalyze.get(new Random().nextInt(listOfChargeOrAnalyze.size())).trim());
        }
        Pages.addAccountPage().clickChargeOrAnalyzeSelectorOption(account.getChargeOrAnalyze());
    }

    public void setChargeOrAnalyze(CHKAccount account) {
        Pages.addAccountPage().clickChargeOrAnalyzeSelectorButton();
        List<String> listOfChargeOrAnalyze = Pages.addAccountPage().getChargeOrAnalyzeList();

        Assert.assertTrue(listOfChargeOrAnalyze.size() > 0, "There are no 'Charge Or Analyze' options available");
        if (account.getChargeOrAnalyze() == null) {
            account.setChargeOrAnalyze(listOfChargeOrAnalyze.get(new Random().nextInt(listOfChargeOrAnalyze.size())).trim());
        }
        Pages.addAccountPage().clickChargeOrAnalyzeSelectorOption(account.getChargeOrAnalyze());
    }

    public void setAccountAnalysis(Account account) {
        Pages.addAccountPage().clickAccountAnalysisSelectorButton();
        List<String> listOfAccountAnalysis = Pages.addAccountPage().getAccountAnalysisList();

        if (listOfAccountAnalysis.size() > 0) {
            if (account.getAccountAnalysis() == null) {
                account.setAccountAnalysis(listOfAccountAnalysis.get(new Random().nextInt(listOfAccountAnalysis.size())).trim());
            }
            Pages.addAccountPage().clickAccountAnalysisSelectorOption(account.getAccountAnalysis());
        }
    }

    public void setAccountAnalysis(CHKAccount account) {
        Pages.addAccountPage().clickAccountAnalysisSelectorButton();
        List<String> listOfAccountAnalysis = Pages.addAccountPage().getAccountAnalysisList();

        Assert.assertTrue(listOfAccountAnalysis.size() > 0, "There are no 'Account Analysis' options available");
        if (account.getAccountAnalysis() == null) {
            account.setAccountAnalysis(listOfAccountAnalysis.get(new Random().nextInt(listOfAccountAnalysis.size())).trim());
        }
        Pages.addAccountPage().clickAccountAnalysisSelectorOption(account.getAccountAnalysis());
    }

    public void setCallClassCode(Account account) {
        Pages.addAccountPage().clickCallClassCodeSelectorButton();
        List<String> listOfCallClassCode = Pages.addAccountPage().getCallClassCodeList();

        if (listOfCallClassCode.size() > 0) {
            if (account.getCallClassCode() == null) {
                account.setCallClassCode(listOfCallClassCode.get(new Random().nextInt(listOfCallClassCode.size())).trim());
            }
            Pages.addAccountPage().clickCallClassCodeSelectorOption(account.getCallClassCode());
        }
    }

    public void setCallClassCode(CHKAccount account) {
        Pages.addAccountPage().clickCallClassCodeSelectorButton();
        List<String> listOfCallClassCode = Pages.addAccountPage().getCallClassCodeList();

        Assert.assertTrue(listOfCallClassCode.size() > 0, "There are no 'Call Class Code' options available");
        if (account.getCallClassCode() == null) {
            account.setCallClassCode(listOfCallClassCode.get(new Random().nextInt(listOfCallClassCode.size())).trim());
        }
        Pages.addAccountPage().clickCallClassCodeSelectorOption(account.getCallClassCode());
    }

    public void setCurrentOfficer(Account account) {
        Pages.addAccountPage().clickCurrentOfficerSelectorButton();
        List<String> listOfCurrentOfficers = Pages.addAccountPage().getCurrentOfficerList();

        Assert.assertTrue(listOfCurrentOfficers.size() > 0, "There are no 'Current Officer' options available");
        if (account.getCurrentOfficer() == null) {
            account.setCurrentOfficer(listOfCurrentOfficers.get(new Random().nextInt(listOfCurrentOfficers.size())).trim());
        }
        Pages.addAccountPage().clickCurrentOfficerSelectorOption(account.getCurrentOfficer());
    }

    public void setCurrentOfficer(CHKAccount account) {
        Pages.addAccountPage().clickCurrentOfficerSelectorButton();
        List<String> listOfCurrentOfficers = Pages.addAccountPage().getCurrentOfficerList();

        Assert.assertTrue(listOfCurrentOfficers.size() > 0, "There are no 'Current Officer' options available");
        if (account.getCurrentOfficer() == null) {
            account.setCurrentOfficer(listOfCurrentOfficers.get(new Random().nextInt(listOfCurrentOfficers.size())).trim());
        }
        Pages.addAccountPage().clickCurrentOfficerSelectorOption(account.getCurrentOfficer());
    }

    public void setStatementCycle(Account account) {
        Pages.addAccountPage().clickStatementCycleSelectorButton();
        List<String> listOfStatementCycle = Pages.addAccountPage().getStatementCycleList();

        Assert.assertTrue(listOfStatementCycle.size() > 0, "There are no 'Statement Cycle' options available");
        if (account.getStatementCycle() == null) {
            account.setStatementCycle(listOfStatementCycle.get(new Random().nextInt(listOfStatementCycle.size())).trim());
        }
        Pages.addAccountPage().clickStatementCycleOption(account.getStatementCycle());
    }

    public void setStatementCycle(CHKAccount account) {
        Pages.addAccountPage().clickStatementCycleSelectorButton();
        List<String> listOfStatementCycle = Pages.addAccountPage().getStatementCycleList();

        Assert.assertTrue(listOfStatementCycle.size() > 0, "There are no 'Statement Cycle' options available");
        if (account.getStatementCycle() == null) {
            account.setStatementCycle(listOfStatementCycle.get(new Random().nextInt(listOfStatementCycle.size())).trim());
        }
        Pages.addAccountPage().clickStatementCycleOption(account.getStatementCycle());
    }

    public void setProduct(Account account) {
        Pages.addAccountPage().clickProductSelectorButton();
        List<String> listOfProduct = Pages.addAccountPage().getProductList();

        Assert.assertTrue(listOfProduct.size() > 0, "There are no 'Product' options available");
        if (account.getProduct() == null) {
            account.setProduct(listOfProduct.get(new Random().nextInt(listOfProduct.size())));
        }

        for (String s : listOfProduct) {
            if (s.trim().equals(account.getProduct())) {
                Pages.addAccountPage().clickProductOption(s);
            }
        }
    }

    public void setProduct(CHKAccount account) {
        Pages.addAccountPage().clickProductSelectorButton();
        List<String> listOfProduct = Pages.addAccountPage().getProductList();

        Assert.assertTrue(listOfProduct.size() > 0, "There are no 'Product' options available");
        Pages.addAccountPage().clickProductOption(account.getProduct().getProduct());
    }

    public void setBankBranch(Account account) {
        Pages.addAccountPage().clickBankBranchSelectorButton();
        List<String> listOfBankBranchOptions = Pages.addAccountPage().getBankBranchList();

        Assert.assertTrue(listOfBankBranchOptions.size() > 0, "There are no 'Bank Branch' options available");
        if (account.getBankBranch() == null) {
            account.setBankBranch(listOfBankBranchOptions.get(new Random().nextInt(listOfBankBranchOptions.size())).trim());
        }
        Pages.addAccountPage().clickBankBranchOption(account.getBankBranch());
    }

    public void setBankBranch(CHKAccount account) {
        Pages.addAccountPage().clickBankBranchSelectorButton();
        List<String> listOfBankBranchOptions = Pages.addAccountPage().getBankBranchList();

        Assert.assertTrue(listOfBankBranchOptions.size() > 0, "There are no 'Bank Branch' options available");
        if (account.getBankBranch() == null) {
            account.setBankBranch(listOfBankBranchOptions.get(new Random().nextInt(listOfBankBranchOptions.size())).trim());
        }
        Pages.addAccountPage().clickBankBranchOption(account.getBankBranch());
    }

    public void setAddNewOption(Account account) {
        Pages.clientDetailsPage().clickAddNewButton();
        List<String> listOfAddNewOptions = Pages.clientDetailsPage().getAddNewList();

        Assert.assertTrue(listOfAddNewOptions.size() > 0, "There are no 'Add New Option' options available");
        if (account.getAddNewOption() == null) {
            account.setAddNewOption(listOfAddNewOptions.get(new Random().nextInt(listOfAddNewOptions.size())).trim());
        }
        Pages.clientDetailsPage().clickAddNewValueOption(account.getAddNewOption());
    }

    public void setAddNewOption(CHKAccount account) {
        Pages.clientDetailsPage().clickAddNewButton();
        List<String> listOfAddNewOptions = Pages.clientDetailsPage().getAddNewList();

        Assert.assertTrue(listOfAddNewOptions.size() > 0, "There are no 'Add New Option' options available");
        Pages.clientDetailsPage().clickAddNewValueOption(account.getAddNewOption());
    }

    public void setProductType(Account account) {
        Pages.addAccountPage().clickProductTypeSelectorButton();
        List<String> listOfProductType = Pages.addAccountPage().getProductTypeList();

        Assert.assertTrue(listOfProductType.size() > 0, "There are no 'Product Type' options available");
        if (account.getProductType() == null) {
            account.setProductType(listOfProductType.get(new Random().nextInt(listOfProductType.size())).trim());
        }
        Pages.addAccountPage().clickProductTypeOption(account.getProductType());
        SelenideTools.sleep(5);
    }

    public void setProductType(CHKAccount account) {
        Pages.addAccountPage().clickProductTypeSelectorButton();
        List<String> listOfProductType = Pages.addAccountPage().getProductTypeList();

        Assert.assertTrue(listOfProductType.size() > 0, "There are no 'Product Type' options available");
        Pages.addAccountPage().clickProductTypeOption(account.getProductType().getProductType());
    }

    public void setBoxSize(Account account) {
        Pages.addAccountPage().clickBoxSizeSelectorButton();
        Pages.addAccountPage().clickBoxSizeSelectorOption(account.getBoxSize());
    }

    public void setMailCode(Account account) {
        Pages.addAccountPage().clickMailCodeSelectorButton();
        List<String> listOfMailCode = Pages.addAccountPage().getMailCodeList();

        Assert.assertTrue(listOfMailCode.size() > 0, "There are no 'Mail Code' options available");
        if (account.getMailCode() == null) {
            account.setMailCode(listOfMailCode.get(new Random().nextInt(listOfMailCode.size())).trim());
        }
        Pages.addAccountPage().clickMailCodeSelectorOption(account.getMailCode());
    }

    public String getDateOpenedValue(Account account) {
        String dateOpened = Pages.addAccountPage().getDateOpened();

        if (dateOpened.isEmpty()) {
            SelenideTools.refresh();
            AccountActions.createAccount().setProductType(account);
            AccountActions.createAccount().setProduct(account);
            dateOpened = Pages.addAccountPage().getDateOpened();
        }
        return dateOpened;
    }

    /**
     * Verify common fields that are prefilled for all account types
     */

    private void verifyAccountPrefilledFields(Account account, IndividualClient client) {
        Assert.assertEquals(getDateOpenedValue(account), DateTime.getLocalDateTimeByPattern("MM/dd/yyyy"), "'Date' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountType(), client.getIndividualType().getClientType().getClientType(), "'Account type' is prefilled with wrong value");
        final String accountHolderName = client.getIndividualType().getFirstName() + " " + client.getIndividualType().getLastName() + " (" + client.getIndividualType().getClientID() + ")";
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderName(), accountHolderName, "'Name' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderRelationship(), "Owner", "'Relationship' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderClientType(), client.getIndividualType().getClientType().getClientType(), "'Client type' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderTaxID(), client.getIndividualType().getTaxID(), "'Tax ID' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getOriginatingOfficer(), client.getIndividualClientDetails().getSelectOfficer(), "'Originating officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getCurrentOfficer(), client.getIndividualClientDetails().getSelectOfficer(), "'Current officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getBankBranch(), account.getBankBranch(), "'Bank branch' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getMailCode(), client.getIndividualClientDetails().getMailCode().getMailCode(), "'Mail code' is prefilled with wrong value");
    }

    /**
     * Verify common prefilled fields for CD accounts
     */

    public void verifyCdAccountPrefilledFields(Account account) {
        Assert.assertEquals(Pages.addAccountPage().getTermType(), account.getMinTerm(), "'Term Type' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getInterestFrequency(), account.getInterestFrequency(), "'Interest Frequency' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getInterestType(), account.getInterestType(), "'Interest Type' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getApplyInterestTo(), "Remain in Account", "'Apply interest to' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAutoRenewable(), "YES", "'Auto Renewable' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getTransactionalAccount().toLowerCase(), "no", "'Transactional Account' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getApplySeasonalAddress().toLowerCase(), "yes", "'Apply Seasonal Address' is prefilled with wrong value");
    }

    /**
     * Verify common prefilled fields for IRA accounts
     */

    public void verifyIraAccountPrefilledFields(Account account, IndividualClient client) {
        Assert.assertEquals(Pages.addAccountPage().getDateOfBirth(), client.getIndividualType().getBirthDate(), "'Date Of Birth' value does not match");
        Assert.assertEquals(Pages.addAccountPage().getIRADistributionFrequency(), account.getIraDistributionFrequency(), "'IRA Distribution Frequency' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getIRADistributionCode(), account.getIraDistributionCode(), "'IRA Distribution Code' is prefilled with wrong value");
    }

    /**
     * Verify prefilled fields for Savings account
     */

    public void verifySavingsAccountPrefilledFields(Account account, IndividualClient client) {
        verifyAccountPrefilledFields(account, client);
        Assert.assertEquals(Pages.addAccountPage().getApplySeasonalAddress().toLowerCase(), "yes", "'Apply Seasonal Address' is prefilled with wrong value");
    }

    /**
     * Verify prefilled fields for Savings IRA account
     */

    public void verifySavingsIraAccountPrefilledFields(Account account, IndividualClient client) {
        verifyAccountPrefilledFields(account, client);
        verifyIraAccountPrefilledFields(account, client);
    }

    /**
     * Verify prefilled fields for CD account
     */

    public void verifyRegularCdAccountPrefilledFields(Account account, IndividualClient client) {
        verifyAccountPrefilledFields(account, client);
        verifyCdAccountPrefilledFields(account);
    }

    /**
     * Verify prefilled fields for CD IRA account
     */

    public void verifyCdIraAccountPrefilledFields(Account account, IndividualClient client) {
        verifyAccountPrefilledFields(account, client);
        verifyCdAccountPrefilledFields(account);
        verifyIraAccountPrefilledFields(account, client);
    }

    /**
     * Verify prefilled fields for Checking account
     */

    public void verifyChkAccountPrefilledFields(Account account, IndividualClient client) {
        verifyAccountPrefilledFields(account, client);
        Assert.assertEquals(Pages.addAccountPage().getOptInOutStatus(), "Client Has Not Responded", "'DBC ODP Opt In/Out Status' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getApplySeasonalAddress().toLowerCase(), "yes", "'Apply Seasonal Address' is prefilled with wrong value");
    }
}
