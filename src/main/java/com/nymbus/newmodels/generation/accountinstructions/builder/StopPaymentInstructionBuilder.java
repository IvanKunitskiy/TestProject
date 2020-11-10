package com.nymbus.newmodels.generation.accountinstructions.builder;

import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.accountinstructions.StopPaymentInstruction;
import com.nymbus.newmodels.accountinstructions.enums.InstructionType;
import com.nymbus.newmodels.accountinstructions.enums.StopType;
import com.nymbus.util.Random;

public class StopPaymentInstructionBuilder implements InstructionBuilder {
    private StopPaymentInstruction instruction;

    public StopPaymentInstructionBuilder() {
        instruction = new StopPaymentInstruction();
    }

    @Override
    public void setType() {
        this.instruction.setInstructionType(InstructionType.STOP_PAYMENT);
    }

    @Override
    public void setDetails() {
        this.instruction.setStopType(StopType.STOP_ALL_ITEMS);
        this.instruction.setExpirationDate(DateTime.getDateTodayPlusDaysWithFormat(Random.genInt(10, 100), "MM/dd/yyyy"));
        this.instruction.setBeginCheck("0001");
        this.instruction.setEndCheck("99999");
        this.instruction.setLowAmount(100.00);
        this.instruction.setHighAmount(9999.00);
        this.instruction.setNotes(Random.genString(10));
    }

    @Override
    public StopPaymentInstruction getResult() {
        return instruction;
    }

}
