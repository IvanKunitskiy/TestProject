package com.nymbus.newmodels.accountinstructions;

import com.nymbus.core.utils.DateTime;
import com.nymbus.util.Random;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityHoldInstruction extends BaseInstruction {
    private String expirationDate;
    private String priority;
    private String notes;

    public String getType() {
        return instructionType.getInstructionType();
    }

    public void updateInstructionData() {
        this.expirationDate = DateTime.getDateTodayPlusDaysWithFormat(Random.genInt(10, 100), "MM/dd/yyyy");
        this.notes = Random.genString(10);
    }
}
