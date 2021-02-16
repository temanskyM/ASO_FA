package ru.temansky.tempcard.collectorService.service;

import ru.temansky.tempcard.api.models.Sensor;

import java.util.List;

public interface SensorService {
    List<Sensor> findAllByAgentId(Long agentId);

    List<Sensor> findAll();

    Sensor findById(Long id);

    void addToAgent(Sensor sensor, Long agentId);

    void addAllToAgent(List<Sensor> sensors,  Long agentId);

}
