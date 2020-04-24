package com.nymbus.newmodels.generation.accountinstructions;

import com.nymbus.newmodels.accountinstructions.BaseInstruction;
import com.nymbus.newmodels.generation.accountinstructions.builder.InstructionBuilder;

public class InstructionConstructor {
    private InstructionBuilder instructionBuilder;

    public InstructionConstructor(InstructionBuilder instructionBuilder) {
        this.instructionBuilder = instructionBuilder;
    }

    public <T extends BaseInstruction> T constructInstruction(Class<T> type) {
        instructionBuilder.setType();
        instructionBuilder.setDetails();

        return type.cast(instructionBuilder.getResult());
    }
}