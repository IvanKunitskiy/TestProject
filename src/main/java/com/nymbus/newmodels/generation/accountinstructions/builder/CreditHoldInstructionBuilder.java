package com.nymbus.newmodels.generation.accountinstructions.builder;

import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.accountinstructions.BaseInstruction;
import com.nymbus.newmodels.accountinstructions.CreditHoldInstruction;
import com.nymbus.newmodels.accountinstructions.enums.InstructionType;
import com.nymbus.util.Random;

public class CreditHoldInstructionBuilder implements InstructionBuilder {

    private CreditHoldInstruction instruction;

    public CreditHoldInstructionBuilder() {
        this.instruction = new CreditHoldInstruction();
    }

    @Override
    public void setType() {
        instruction.setInstructionType(InstructionType.CREDIT_HOLD);
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
