package com.mist.cloudtestingplatform.protocol.test;

import com.mist.cloudtestingplatform.protocol.model.ControlData;
import com.mist.cloudtestingplatform.protocol.model.PayloadBase;

/**
 * Created by Prophet on 2018/1/8.
 */
public class PayloadControlData extends PayloadBase{

    ControlData data;

    public ControlData getData() {
        return data;
    }

    public void setData(ControlData data) {
        this.data = data;
    }
}
