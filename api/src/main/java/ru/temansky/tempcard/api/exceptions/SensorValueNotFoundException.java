package ru.temansky.tempcard.api.exceptions;

public class SensorValueNotFoundException extends RuntimeException{
    public SensorValueNotFoundException(Long id){
        super("Could not find sensorValue with id " + id);
    }
}
