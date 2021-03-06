package ru.temansky.tempcard.collectorService.service;


import ru.temansky.tempcard.collectorService.models.SensorValue;

import java.util.List;

public interface SensorValueService {
    List<SensorValue> findAll();

    List<SensorValue> findAllByAgentIdAndSensorId(Long agentId, Long sensorId);

    SensorValue findById(Long id);

    void addBySensorId(SensorValue sensorValue, Long agentId, Long sensorId);
}
