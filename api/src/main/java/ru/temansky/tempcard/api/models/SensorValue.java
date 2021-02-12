package ru.temansky.tempcard.api.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "SensorValues")
public class SensorValue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "temp", length = 10)
    private double temp;

    @Column(name = "hum", length = 10)
    private double hum;

    @Column(name = "local_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime localDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;

    public SensorValue(double temp, double hum, LocalDateTime localDateTime, Sensor sensor) {
        this.temp = temp;
        this.hum = hum;
        this.localDateTime = localDateTime;
        this.sensor = sensor;
    }

    public Long getId() {
        return id;
    }

    public SensorValue() {
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHum() {
        return hum;
    }

    public void setHum(double hum) {
        this.hum = hum;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
