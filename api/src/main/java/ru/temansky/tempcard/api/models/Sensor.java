package ru.temansky.tempcard.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Sensors")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

//    @JsonIgnore
//    @OneToMany(cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY,
//            mappedBy = "sensor")
//    private List<SensorValue> sensorValues = new ArrayList<>();

    public Sensor() {
    }

    public Sensor(String name, Agent agent) {
        this.name = name;
        this.agent = agent;
    }

    public Sensor(String name) {
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

//    public List<SensorValue> getSensorValues() {
//        return sensorValues;
//    }

//    public void setSensorValues(List<SensorValue> sensorValues) {
//        this.sensorValues = sensorValues;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sensor that = (Sensor) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
