package com.nymbus.newmodels.generation.accountinstructions.builder;

import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.accountinstructions.BaseInstruction;
import com.nymbus.newmodels.accountinstructions.DebitHoldInstruction;
import com.nymbus.newmodels.accountinstructions.enums.InstructionType;
import com.nymbus.util.Random;

public class DebitHoldInstructionBuilder implements InstructionBuilder {

    private DebitHoldInstruction instruction;

    public DebitHoldInstructionBuilder() {
        this.instruction = new DebitHoldInstruction();
    }

    @Override
    public void setType() {
        instruction.setInstructionType(InstructionType.DEBIT_HOLD);
    }

    @Override
    public void setDetails() {
        this.instruction.setExpirationDate(DateTime.getDateTodayPlusDaysWithFormat(60, "MM/dd/yyyy"));
        this.instruction.setNotes(Random.genString(20));
    }

    @Override
    public BaseInstruction getResult() {
        return instruction;
    }
}
