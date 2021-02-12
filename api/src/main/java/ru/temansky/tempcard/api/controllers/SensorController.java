package ru.temansky.tempcard.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.temansky.tempcard.api.exceptions.AgentNotFoundException;
import ru.temansky.tempcard.api.exceptions.SensorNotFoundException;
import ru.temansky.tempcard.api.models.Agent;
import ru.temansky.tempcard.api.models.Sensor;
import ru.temansky.tempcard.api.repositories.AgentsRepository;
import ru.temansky.tempcard.api.repositories.SensorsRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class SensorController {
    final Logger LOGGER = LoggerFactory.getLogger(SensorController.class);
    private final SensorsRepository sensorsRepository;
    private final AgentsRepository agentsRepository;

    public SensorController(SensorsRepository sensorsRepository, AgentsRepository agentsRepository) {

        this.sensorsRepository = sensorsRepository;
        this.agentsRepository = agentsRepository;
    }

    @GetMapping("/api/sensors")
    Iterable<Sensor> all() {
        return sensorsRepository.findAll();
    }

    @GetMapping("/api/sensors/{id}")
    Sensor getOne(@PathVariable Long id) {
        return sensorsRepository.findById(id).orElseThrow(() -> new SensorNotFoundException(id));
    }

    @PostMapping("/api/agents/{agent_id}/sensors")
    Sensor newSensor(@PathVariable long agent_id, @RequestBody Sensor newSensor) {
        Agent agent = agentsRepository.findById(agent_id).orElseThrow(() -> new AgentNotFoundException(agent_id));
        newSensor.setAgent(agent);
        return sensorsRepository.save(newSensor);
    }

    @GetMapping("/api/agents/{agentId}/sensors/{sensorId}")
    Sensor getOneFromAgent(@PathVariable Long agentId, @PathVariable Long sensorId) {
        Agent agent = agentsRepository.findById(agentId).orElseThrow(() -> new AgentNotFoundException(agentId));
        List<Sensor> listSensor = agent.getSensors().stream()
                .filter(value -> value.getId().equals(sensorId))
                .collect(Collectors.toList());
        if (listSensor.size() == 0)
            throw new SensorNotFoundException(sensorId);
        return listSensor.get(0);
    }

    @PutMapping("/api/agents/{agentId}/sensors/{sensorId}")
    Sensor updateSensorFromAgent(@RequestBody Sensor newSensor, @PathVariable Long agentId, @PathVariable Long sensorId){

        Agent agent = agentsRepository.findById(agentId).orElseThrow(() -> new AgentNotFoundException(agentId));
        List<Sensor> listSensor = agent.getSensors().stream()
                .filter(value -> value.getId().equals(sensorId))
                .collect(Collectors.toList());
        if (listSensor.size() == 0)
            throw new SensorNotFoundException(sensorId);

        Sensor sensor = listSensor.get(0);
        sensor.setName(newSensor.getName());
        return sensorsRepository.save(sensor);
    }


    @GetMapping("/api/agents/{agent_id}/sensors")
    Iterable<Sensor> allFromAgents(@PathVariable long agent_id) {
        Agent agent = agentsRepository.findById(agent_id).orElseThrow(() -> new AgentNotFoundException(agent_id));
        return agent.getSensors();
    }

    @PutMapping("/api/sensors/{sensorId}")
    Sensor updateSensor(@RequestBody Sensor newSensor, @PathVariable Long sensorId){
        return sensorsRepository.findById(sensorId).map(sensor -> {
            sensor.setName(newSensor.getName());
            return sensorsRepository.save(sensor);
        }).orElseThrow(() -> new SensorNotFoundException(sensorId));
    }

    @DeleteMapping("api/sensors/{id}")
    void deleteSensor(@PathVariable Long id) {
        sensorsRepository.deleteById(id);
    }


}
