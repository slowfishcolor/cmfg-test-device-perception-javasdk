package com.mist.cloudtestingplatform.protocol.messenger;

import com.mist.cloudtestingplatform.protocol.config.Config;
import com.mist.cloudtestingplatform.protocol.exception.InvalidPayloadException;
import com.mist.cloudtestingplatform.protocol.exception.UninitializedClientException;
import com.mist.cloudtestingplatform.protocol.model.Payload;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Created by Prophet on 2018/1/6.
 */
public interface IMessenger {

    public void connect(Config config) throws MqttException;

    public void connect(Config config, ProtocolCallback callback) throws MqttException;

    public void publish(Payload payload) throws UninitializedClientException, InvalidPayloadException, MqttException;

    public void subscribe(String destination, int qos) throws UninitializedClientException, MqttException;

    public void disconnect() throws MqttException;
}
