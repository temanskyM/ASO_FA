package ru.temansky.tempcard.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.temansky.tempcard.api.exceptions.AgentNotFoundException;
import ru.temansky.tempcard.api.models.Agent;
import ru.temansky.tempcard.api.models.AgentEntity;
import ru.temansky.tempcard.api.repositories.AgentsRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Agents {
    final Logger LOGGER = LoggerFactory.getLogger(Agents.class);

    private final AgentsRepository agentsRepository;

    public Agents(AgentsRepository agentsRepository) {
        this.agentsRepository = agentsRepository;
    }

    @GetMapping("/api/agents")
    List<Agent> all() {
        return agentsRepository.findAll();
    }

    @PostMapping("/api/agents")
    Agent newAgent(@RequestBody Agent agent){
        return agentsRepository.save(agent);
    }

    @GetMapping("/api/agents/{id}")
    Agent one(@PathVariable Long id){
        return agentsRepository.findById(id).orElseThrow(() -> new AgentNotFoundException(id));
    }

    @PutMapping("/api/agents/{id}")
    Agent replaceAgent(@RequestBody Agent newAgent, @PathVariable Long id){
        return agentsRepository.findById(id).map(agent -> {
            agent.setHostname(newAgent.getHostname());
            agent.setIp(newAgent.getIp());
            return agentsRepository.save(agent);
        }).orElseGet(()->{
            newAgent.setId(id);
            return agentsRepository.save(newAgent);
        });
    }

    @DeleteMapping("/api/agents/{id}")
    void deleteAgent(@PathVariable Long id){
        agentsRepository.deleteById(id);
    }



}
