package com.nymbus.newmodels.generation.accountinstructions.builder;

import com.nymbus.newmodels.accountinstructions.BaseInstruction;

public interface InstructionBuilder {
    public void setType();
    public void setDetails();
    public BaseInstruction getResult();
}
