package ru.temansky.tempcard.collectorService.service;

import ru.temansky.tempcard.UartService.models.arduino.MessageFromArduino;
import ru.temansky.tempcard.api.models.Agent;

import java.util.List;

public interface AgentService {
    List<Agent> getAll();
    Agent getById(Long id);
    MessageFromArduino getResponse(Agent agent);
}
