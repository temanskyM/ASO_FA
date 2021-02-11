package ru.temansky.tempcard.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.temansky.tempcard.api.models.*;
import ru.temansky.tempcard.api.repositories.AgentsRepository;
import ru.temansky.tempcard.api.repositories.SensorsRepository;
import ru.temansky.tempcard.api.repositories.SensorValueRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiController {
    final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private AgentsRepository agentsRepository;

    @Autowired
    private SensorsRepository sensorsRepository;

    @Autowired
    private SensorValueRepository sensorValueRepository;





    @GetMapping("/api/getSensorData")
    public List<SensorValueEntity> getSensorData() {
        List<SensorValueEntity> result = new ArrayList<>();
        try {
            //Получаем список агентов и пытаем получить от них JSON
            Iterable<SensorValue> sensorValueList = sensorValueRepository.findAll();
            for (SensorValue sensorValue : sensorValueList) {
                result.add(new SensorValueEntity(sensorValue.getId(), sensorValue.getTemp(), sensorValue.getHum(),sensorValue.getLocalDateTime()));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        return result;
    }
}
