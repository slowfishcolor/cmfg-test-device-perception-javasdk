package com.mist.cloudtestingplatform.protocol.test;

import com.mist.cloudtestingplatform.protocol.model.AnalogSampleData;
import com.mist.cloudtestingplatform.protocol.model.PayloadBase;

/**
 * Created by Prophet on 2018/1/8.
 */
public class PayloadAnalogSampleData extends PayloadBase {

    AnalogSampleData data;

    public AnalogSampleData getData() {
        return data;
    }

    public void setData(AnalogSampleData data) {
        this.data = data;
    }
}
