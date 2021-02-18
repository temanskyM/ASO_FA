package ru.temansky.tempcard.collectorService.models;

import java.util.ArrayList;
import java.util.List;

public class Sensor {
    private Long id;
    private String name;
    private Agent agent;
    private List<SensorValue> sensorValues = new ArrayList<>();

    public Sensor() {
    }

    public Sensor(Long id, String name, Agent agent, List<SensorValue> sensorValues) {
        this.id = id;
        this.name = name;
        this.agent = agent;
        this.sensorValues = sensorValues;
    }

    public Sensor(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public List<SensorValue> getSensorValues() {
        return sensorValues;
    }

    public void setSensorValues(List<SensorValue> sensorValues) {
        this.sensorValues = sensorValues;
    }
}
