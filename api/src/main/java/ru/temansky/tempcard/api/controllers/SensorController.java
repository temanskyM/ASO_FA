package ru.temansky.tempcard.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import ru.temansky.tempcard.api.repositories.SensorRepository;

@RestController
public class SensorController {
    final Logger LOGGER = LoggerFactory.getLogger(SensorController.class);
    private final SensorRepository sensorRepository;

    public SensorController(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }


}
