package ru.temansky.tempcard.webcore.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Sensors")
public class ArduinoSensor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "arduinoSensor")
    private List<SensorValue> sensorValues = new ArrayList<>();

    public ArduinoSensor() {
    }

    public ArduinoSensor(String name, Agent agent) {
        this.name = name;
        this.agent = agent;
    }

    public ArduinoSensor(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArduinoSensor that = (ArduinoSensor) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
