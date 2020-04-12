package com.nymbus.newmodels.accountinstructions;

import com.nymbus.newmodels.accountinstructions.enums.InstructionType;
import lombok.Data;

@Data
public abstract class BaseInstruction {
    protected InstructionType instructionType;
}
