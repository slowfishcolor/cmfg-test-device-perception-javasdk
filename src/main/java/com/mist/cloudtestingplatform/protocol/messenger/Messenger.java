package com.mist.cloudtestingplatform.protocol.messenger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mist.cloudtestingplatform.protocol.config.Config;
import com.mist.cloudtestingplatform.protocol.exception.InvalidPayloadException;
import com.mist.cloudtestingplatform.protocol.exception.UninitializedClientException;
import com.mist.cloudtestingplatform.protocol.model.Payload;
import com.mist.cloudtestingplatform.protocol.util.JsonUtil;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by Prophet on 2018/1/6.
 */
public class Messenger implements IMessenger{

    private MqttClient mqttClient;

    private int qos = 0;

    private Config config;

    @Override
    public void connect(Config config, ProtocolCallback callback) throws MqttException {
        this.config = config;
        MemoryPersistence persistence = new MemoryPersistence();
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        if (config.getUsername() != null && config.getPassword() != null) {
            connOpts.setUserName(config.getUsername());
            connOpts.setPassword(config.getPassword().toCharArray());
        }
        connOpts.setConnectionTimeout(10);
        connOpts.setKeepAliveInterval(20);
        this.mqttClient = new MqttClient(config.getBrokerUrl(), config.getDeviceId(), persistence);
        mqttClient.setCallback(callback);
        mqttClient.connect(connOpts);
    }

    @Override
    public void connect(Config config) throws MqttException {
        connect(config, new ProtocolCallback() {
            @Override
            public void protocolMessageArrived(Payload payload) {
                System.out.println(payload.toString());
            }
        });
    }

    @Override
    public void publish(Payload payload) throws UninitializedClientException, InvalidPayloadException, MqttException {
        if (mqttClient == null) {
            throw new UninitializedClientException();
        }
        if (payload.getOption() == null) {
            throw new InvalidPayloadException();
        }
        try {
            String payloadStr = JsonUtil.payLoadToJson(payload);
            MqttMessage message = new MqttMessage(payloadStr.getBytes());
            message.setQos(payload.getOption().getQos());
            message.setRetained(payload.getOption().getPersistance() != 0);
            mqttClient.publish(payload.getDestination(), message);
        } catch (JsonProcessingException e) {
            throw new InvalidPayloadException();
        }

    }

    @Override
    public void subscribe(String destination, int qos) throws UninitializedClientException, MqttException {
        if (mqttClient == null) {
            throw new UninitializedClientException();
        }
        mqttClient.subscribe(destination, qos);
    }

    @Override
    public void disconnect() throws MqttException {
        if (mqttClient != null) {
            mqttClient.disconnect();
        }
    }
}
