package com.nymbus.newmodels.accountinstructions;

import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.accountinstructions.enums.StopType;
import com.nymbus.util.Random;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StopPaymentInstruction  extends BaseInstruction {
    private StopType stopType;
    private String expirationDate;
    private String beginCheck;
    private String endCheck;
    private double lowAmount;
    private double highAmount;
    private double chargeAmount;
    private String notes;

    public String getType() {
        return instructionType.getInstructionType();
    }

    public void updateInstructionData() {
        this.stopType = StopType.STOP_ALL_ITEMS;
        this.expirationDate = DateTime.getDateTodayPlusDaysWithFormat(Random.genInt(10, 100), "MM/dd/yyyy");
        this.beginCheck = "0001";
        this.endCheck = "99999";
        this.lowAmount = 100.00;
        this.highAmount = 9999.00;
        this.notes = Random.genString(10);
    }
}
