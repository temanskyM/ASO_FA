package ru.temansky.tempcard.api.controllers;

import com.fasterxml.jackson.databind.util.ArrayIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.temansky.tempcard.api.exceptions.AgentNotFoundException;
import ru.temansky.tempcard.api.exceptions.SensorNotFoundException;
import ru.temansky.tempcard.api.exceptions.SensorValueNotFoundException;
import ru.temansky.tempcard.api.models.Agent;
import ru.temansky.tempcard.api.models.Sensor;
import ru.temansky.tempcard.api.models.SensorValue;
import ru.temansky.tempcard.api.repositories.AgentsRepository;
import ru.temansky.tempcard.api.repositories.SensorValueRepository;
import ru.temansky.tempcard.api.repositories.SensorsRepository;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class SensorValueController {
    final Logger LOGGER = LoggerFactory.getLogger(SensorValueController.class);

    private final SensorsRepository sensorsRepository;
    private final SensorValueRepository sensorValueRepository;
    private final AgentsRepository agentsRepository;


    public SensorValueController(SensorsRepository sensorsRepository, SensorValueRepository sensorValueRepository, AgentsRepository agentsRepository) {
        this.sensorsRepository = sensorsRepository;
        this.sensorValueRepository = sensorValueRepository;
        this.agentsRepository = agentsRepository;
    }

    @GetMapping("/api/sensorValues")
    List<SensorValue> all(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        var paging = PageRequest.of(pageNo,pageSize, Sort.by(sortBy));
        Page<SensorValue> pagedResult = sensorValueRepository.findAll(paging);

        if (pagedResult.hasContent())
            return pagedResult.getContent();
        else
            return new ArrayList<>();
    }

    @GetMapping("/api/sensorValues/{id}")
    SensorValue getOne(@PathVariable Long id) {
        return sensorValueRepository.findById(id).orElseThrow(() -> new SensorValueNotFoundException(id));
    }

    @GetMapping("/api/sensors/{sensor_id}/sensorValues")
    List<SensorValue> getSensorValuesFromSensor(@PathVariable long sensor_id) {
        Sensor sensor = sensorsRepository.findById(sensor_id).orElseThrow(() -> new SensorNotFoundException(sensor_id));
        return sensor.getSensorValues();
    }

    @PostMapping("/api/sensors/{sensor_id}/sensorValues")
    SensorValue newSensorValueFromSensorId(@PathVariable long sensor_id, @RequestBody SensorValue newSensorValue) {
        Sensor sensor = sensorsRepository.findById(sensor_id).orElseThrow(() -> new SensorNotFoundException(sensor_id));
        newSensorValue.setSensor(sensor);
        return sensorValueRepository.save(newSensorValue);
    }

    @PostMapping("/api/agents/{agent_id}/sensors/{sensor_id}/sensorValues")
    SensorValue newSensorValueFromAgentIdAndSensorId(@PathVariable long agent_id, @PathVariable long sensor_id, @RequestBody SensorValue newSensorValue) {
        Agent agent = agentsRepository.findById(agent_id).orElseThrow(() -> new AgentNotFoundException(agent_id));
        Sensor sensor = agent.getSensors().stream()
                .filter(findSensor -> findSensor.getId().equals(sensor_id))
                .findFirst()
                .orElseThrow(() -> new SensorNotFoundException(sensor_id));
        newSensorValue.setSensor(sensor);
        return sensorValueRepository.save(newSensorValue);
    }

    @PutMapping("/api/sensorValues/{sensorValueId}")
    SensorValue updateSensorValue(@RequestBody SensorValue newSensorValue, @PathVariable Long sensorValueId) {
        return sensorValueRepository.findById(sensorValueId).map(sensorValue -> {
            sensorValue.setHum(newSensorValue.getHum());
            sensorValue.setTemp(newSensorValue.getTemp());
            sensorValue.setLocalDateTime(newSensorValue.getLocalDateTime());
            return sensorValueRepository.save(sensorValue);
        }).orElseThrow(() -> new SensorValueNotFoundException(sensorValueId));
    }

    @DeleteMapping("api/sensorValues/{id}")
    void deleteSensorValue(@PathVariable Long id) {
        sensorValueRepository.deleteById(id);
    }
}
