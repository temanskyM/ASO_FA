package ru.temansky.tempcard.api.models;

import java.time.LocalDateTime;

public class SensorValueEntity {
    private Long id;
    private Double temp;
    private Double hum;
    private LocalDateTime localDateTime;

    public SensorValueEntity() {
    }

    public SensorValueEntity(Long id, Double temp, Double hum, LocalDateTime localDateTime) {
        this.id = id;
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

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getHum() {
        return hum;
    }

    public void setHum(Double hum) {
        this.hum = hum;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
