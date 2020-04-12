package com.nymbus.newmodels.accountinstructions;

import com.nymbus.newmodels.accountinstructions.enums.InstructionType;
import lombok.Data;

@Data
public class HoldInstruction extends BaseInstruction {
    private double amount;
    private String expirationDate;
    private String reason;
    private String notes;

    public String getType() {
        return instructionType.getInstructionType();
    }
}
