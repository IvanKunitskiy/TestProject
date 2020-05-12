package com.nymbus.newmodels.generation.accountinstructions.builder;

import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.accountinstructions.ActivityHoldInstruction;
import com.nymbus.newmodels.accountinstructions.enums.InstructionType;
import com.nymbus.util.Random;

public class ActivityHoldInstructionBuilder implements InstructionBuilder {

    private ActivityHoldInstruction instruction;

    public ActivityHoldInstructionBuilder() {
        instruction = new ActivityHoldInstruction();
    }

    @Override
    public void setType() {
        this.instruction.setInstructionType(InstructionType.ACTIVITY_HOLD);
    }

    @Override
    public void setDetails() {
        this.instruction.setExpirationDate(DateTime.getDateTodayPlusDaysWithFormat(60, "MM/dd/yyyy"));
        this.instruction.setNotes(Random.genString(20));
    }

    @Override
    public ActivityHoldInstruction getResult() {
        return instruction;
    }
}
