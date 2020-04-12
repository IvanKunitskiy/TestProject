package com.nymbus.newmodels.accountinstructions.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InstructionType {
    ACTIVITY_HOLD("Activity Hold"),
    CREDIT_HOLD("Credit Hold"),
    DEBIT_HOLD("Debit Hold"),
    HOLD("Hold"),
    SPECIAL_HANDLING_INSTRUCTION("Special Handling Instruction"),
    SPECIAL_INSTRUCTION("Special Instruction"),
    STOP_PAYMENT("Stop Payment"),
    WATCH_DEBITS("Watch Debits");

    private final String instructionType;
}
