package ru.temansky.tempcard.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.temansky.tempcard.api.exceptions.AgentNotFoundException;
import ru.temansky.tempcard.api.exceptions.SensorNotFoundException;
import ru.temansky.tempcard.api.exceptions.SensorValueNotFoundException;
import ru.temansky.tempcard.api.models.Agent;
import ru.temansky.tempcard.api.models.Sensor;
import ru.temansky.tempcard.api.models.SensorValue;
import ru.temansky.tempcard.api.repositories.SensorValueRepository;
import ru.temansky.tempcard.api.repositories.SensorsRepository;

@RestController
public class SensorValueController {
    final Logger LOGGER = LoggerFactory.getLogger(SensorValueController.class);

    private final SensorsRepository sensorsRepository;
    private final SensorValueRepository sensorValueRepository;


    public SensorValueController(SensorsRepository sensorsRepository, SensorValueRepository sensorValueRepository) {
        this.sensorsRepository = sensorsRepository;
        this.sensorValueRepository = sensorValueRepository;
    }

    @GetMapping("/api/sensorValues")
    Iterable<SensorValue> all() {
        return sensorValueRepository.findAll();
    }

    @GetMapping("/api/sensorValues/{id}")
    SensorValue getOne(@PathVariable Long id) {
        return sensorValueRepository.findById(id).orElseThrow(() -> new SensorValueNotFoundException(id));
    }

    @PostMapping("/api/sensors/{sensor_id}/sensorValues")
    SensorValue newSensor(@PathVariable long sensor_id, @RequestBody SensorValue newSensorValue) {
        Sensor sensor = sensorsRepository.findById(sensor_id).orElseThrow(() -> new SensorNotFoundException(sensor_id));
        newSensorValue.setSensor(sensor);
        return sensorValueRepository.save(newSensorValue);
    }


}
