package com.mist.cloudtestingplatform.protocol.test;

import com.mist.cloudtestingplatform.protocol.config.Config;
import com.mist.cloudtestingplatform.protocol.messenger.Messenger;
import com.mist.cloudtestingplatform.protocol.model.ControlData;
import com.mist.cloudtestingplatform.protocol.model.Option;
import com.mist.cloudtestingplatform.protocol.model.Payload;
import com.mist.cloudtestingplatform.protocol.model.PayloadFactory;
import org.junit.Test;

/**
 * Created by Prophet on 2018/1/6.
 */
public class MessengerTest {

    @Test
    public void sendMessageTest() {
        Option option = new Option();
        option.setQos(2);
        ControlData controlData = new ControlData();
        controlData.setCommand("test");
        Payload payload = PayloadFactory.createPayload(option, controlData);
        payload.setDestination("/topic/messenger/device/test");

        Messenger messenger = new Messenger();
        try {
            System.out.println("start");
            messenger.connect(new Config());
            messenger.subscribe("/topic/messenger/device/test", 2);
            messenger.publish(payload);

            Thread.sleep(2000);

            messenger.disconnect();
            System.out.println("disconnect");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
