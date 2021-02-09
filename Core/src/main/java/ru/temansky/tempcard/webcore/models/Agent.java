package ru.temansky.tempcard.webcore.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Agents")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "hostname", nullable = false, length = 250)
    private String hostname;

    @Column(name = "ip", nullable = false, length = 250)
    private String ip;

    @Column(name = "port", nullable = false, length = 250)
    private String port;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "agent")
    private List<ArduinoSensor> arduinoSensors = new ArrayList<>();

    public Agent() {
    }

    public Agent(String hostname, String ip, String port) {
        this.hostname = hostname;
        this.ip = ip;
        this.port = port;
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

    public List<ArduinoSensor> getArduinoSensors() {
        return arduinoSensors;
    }

    public void setArduinoSensors(List<ArduinoSensor> arduinoSensors) {
        this.arduinoSensors = arduinoSensors;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "id=" + id +
                ", hostname='" + hostname + '\'' +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
