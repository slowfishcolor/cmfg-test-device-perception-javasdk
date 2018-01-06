package com.mist.cloudtestingplatform.protocol.config;

/**
 * Created by Prophet on 2017/11/23.
 */
public class Config {

    private String brokerUrl = "tcp://iot.eclipse.org:1883";

    private String deviceId = "deviceId";

    private String username = null;

    private String password = null;

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
