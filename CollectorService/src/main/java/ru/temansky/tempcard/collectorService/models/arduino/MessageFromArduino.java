package ru.temansky.tempcard.collectorService.models.arduino;

public class MessageFromArduino {
    private Sensor[] sensors;

    public MessageFromArduino() {
    }

    public MessageFromArduino(Sensor[] sensors) {
        this.sensors = sensors;
    }

    public Sensor[] getSensors() {
        return sensors;
    }

    public void setSensors(Sensor[] sensors) {
        this.sensors = sensors;
    }
}
