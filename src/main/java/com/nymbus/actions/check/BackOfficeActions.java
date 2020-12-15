package com.nymbus.actions.check;

import com.nymbus.newmodels.backoffice.Check;
import com.nymbus.newmodels.backoffice.FullCheck;
import com.nymbus.pages.Pages;

public class BackOfficeActions {

    public Check getCheckFromBankOffice(String number){
        Check check = new Check();
        check.setDate(Pages.checkPage().getDate(number));
        check.setCheckType(Pages.checkPage().getCheckType(number));
        check.setPayee(Pages.checkPage().getPayee(number));
        check.setPurchaser(Pages.checkPage().getPurchaser(number));
        check.setInitials(Pages.checkPage().getInitials(number));
        check.setAmount(Double.parseDouble(Pages.checkPage().getAmount(number)));
        check.setStatus(Pages.checkPage().getStatus(number));
        check.setCheckNumber(number);
        return check;
    }

    public FullCheck getFullCheckFromBankOffice() {
        FullCheck check = new FullCheck();
        check.setStatus(Pages.fullCheckPage().getStatus());
        check.setCheckNumber(Pages.fullCheckPage().getCheckNumber());
        check.setRemitter(Pages.fullCheckPage().getRemitter());
        check.setPhone(Pages.fullCheckPage().getPhone());
        check.setDocumentType(Pages.fullCheckPage().getDocumentType());
        check.setDocumentID(Pages.fullCheckPage().getDocumentID());
        check.setCheckType(Pages.fullCheckPage().getCheckType());
        check.setPurchaser(Pages.fullCheckPage().getPurchaser());
        check.setBranch(Pages.fullCheckPage().getBranch());
        check.setInitials(Pages.fullCheckPage().getInitials());
        check.setAmount(Double.parseDouble(Pages.fullCheckPage().getAmount()));
        check.setFee(Double.parseDouble(Pages.fullCheckPage().getFee()));
        check.setDate(Pages.fullCheckPage().getDate());
        check.setCashPurchased(Pages.fullCheckPage().getCashPurchased());
        return check;
    }
}