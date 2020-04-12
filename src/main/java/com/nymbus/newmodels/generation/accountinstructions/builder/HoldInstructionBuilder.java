package com.nymbus.newmodels.generation.accountinstructions.builder;

import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.accountinstructions.HoldInstruction;
import com.nymbus.newmodels.accountinstructions.enums.InstructionType;
import com.nymbus.util.Random;

public class HoldInstructionBuilder implements InstructionBuilder {
    private HoldInstruction instruction;

    public HoldInstructionBuilder() {
        instruction = new HoldInstruction();
    }

    @Override
    public void setType() {
        this.instruction.setInstructionType(InstructionType.HOLD);
    }

    @Override
    public void setDetails() {
        this.instruction.setExpirationDate(DateTime.getDateTodayPlusDaysWithFormat(60, "MM/dd/yyyy"));
        this.instruction.setNotes(Random.genString(20));
        this.instruction.setAmount(10.00);
    }

    @Override
    public HoldInstruction getResult() {
        return instruction;
    }
}
