package ru.temansky.tempcard.UartService.models.arduino;

public class Sensor {
    private String name;
    private double temp;
    private double hum;

    public Sensor(String name, double temp, double hum) {
        this.name = name;
        this.temp = temp;
        this.hum = hum;
    }

    public Sensor() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
