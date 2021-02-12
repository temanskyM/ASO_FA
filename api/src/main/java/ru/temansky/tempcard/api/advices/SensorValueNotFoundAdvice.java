package ru.temansky.tempcard.api.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.temansky.tempcard.api.exceptions.SensorValueNotFoundException;

@ControllerAdvice
public class SensorValueNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(SensorValueNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String sensorNotFoundHandler(SensorValueNotFoundException ex) {
        return ex.getMessage();
    }
}
