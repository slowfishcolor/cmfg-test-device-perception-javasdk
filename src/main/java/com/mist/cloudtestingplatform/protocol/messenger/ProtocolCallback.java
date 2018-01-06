package com.mist.cloudtestingplatform.protocol.messenger;

import com.mist.cloudtestingplatform.protocol.model.Payload;
import com.mist.cloudtestingplatform.protocol.util.JsonUtil;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by Prophet on 2018/1/6.
 */
public abstract class ProtocolCallback implements MqttCallback {

    /**
     * called when new message arrived
     * @param payload protocol payload
     */
    public abstract void protocolMessageArrived(Payload payload);

    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        String messageStr = new String(mqttMessage.getPayload());
        Payload payload = JsonUtil.jsonToPayload(messageStr);
        // set real topic
        payload.setDestination(topic);
        // message arrived callback
        protocolMessageArrived(payload);
    }

    public void connectionLost(Throwable throwable) {

    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

}
