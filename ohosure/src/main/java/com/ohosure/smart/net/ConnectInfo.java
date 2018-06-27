package com.ohosure.smart.net;

import java.io.Serializable;

/**
 * Created by daxing on 2017/3/23.
 */

public class ConnectInfo implements Serializable {
    String name;
    String password;
    String mac;
    String host;
    int port;
    String clientId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getMac() {
        return mac;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
