package ru.temansky.tempcard.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.temansky.tempcard.api.exceptions.AgentNotFoundException;
import ru.temansky.tempcard.api.models.Agent;
import ru.temansky.tempcard.api.models.Sensor;
import ru.temansky.tempcard.api.repositories.AgentsRepository;
import ru.temansky.tempcard.api.repositories.SensorsRepository;

import java.util.Optional;

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


    @PostMapping("/api/agents/{agent_id}/sensors")
    Sensor newSensor(@PathVariable long agent_id, @RequestBody Sensor newSensor) {
        Agent agent = agentsRepository.findById(agent_id).orElseThrow(() -> new AgentNotFoundException(agent_id));
        newSensor.setAgent(agent);
        return sensorsRepository.save(newSensor);
    }


    @GetMapping("/api/agents/{agent_id}/sensors")
    Iterable<Sensor> allFromAgents(@PathVariable long agent_id) {
        Agent agent = agentsRepository.findById(agent_id).orElseThrow(() -> new AgentNotFoundException(agent_id));
        return agent.getSensors();
    }

}
