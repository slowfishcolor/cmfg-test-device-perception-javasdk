package com.mist.cloudtestingplatform.protocol.test;

import com.mist.cloudtestingplatform.protocol.model.InstructionData;
import com.mist.cloudtestingplatform.protocol.model.PayloadBase;

/**
 * Created by Prophet on 2018/1/8.
 */
public class PayloadInstructionData extends PayloadBase{

    InstructionData data;

    public InstructionData getData() {
        return data;
    }

    public void setData(InstructionData data) {
        this.data = data;
    }
}
