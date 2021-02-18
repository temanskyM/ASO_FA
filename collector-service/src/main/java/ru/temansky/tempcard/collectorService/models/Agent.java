package ru.temansky.tempcard.collectorService.models;

import java.util.ArrayList;
import java.util.List;

public class Agent {
    private Long id;
    private String hostname;
    private String ip;
    private String port;
    private List<Sensor> sensors = new ArrayList<>();

    public Agent() {
    }

    public Agent(Long id, String hostname, String ip, String port, List<Sensor> sensors) {
        this.id = id;
        this.hostname = hostname;
        this.ip = ip;
        this.port = port;
        this.sensors = sensors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }
}
