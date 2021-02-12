package ru.temansky.tempcard.api.exceptions;

public class SensorNotFoundException extends RuntimeException{
    public SensorNotFoundException(Long id){
        super("Could not find sensor with id " + id);
    }
}
