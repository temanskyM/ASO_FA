package ru.temansky.tempcard.collectorService.models;

import java.time.LocalDateTime;

public class SensorValue {
    private Long id;
    private double temp;
    private double hum;
    private LocalDateTime localDateTime;

    public SensorValue() {
    }

    public SensorValue(Long id, double temp, double hum, LocalDateTime localDateTime) {
        this.id = id;
        this.temp = temp;
        this.hum = hum;
        this.localDateTime = localDateTime;
    }

    public SensorValue(double temp, double hum, LocalDateTime localDateTime) {
        this.temp = temp;
        this.hum = hum;
        this.localDateTime = localDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
