package ru.temansky.tempcard.collectorService.service;

import ru.temansky.tempcard.UartService.models.arduino.MessageFromArduino;
import ru.temansky.tempcard.collectorService.models.Agent;

import java.util.List;

public interface AgentService {
    List<Agent> getAll();
    Agent getById(Long id);
    MessageFromArduino getResponse(Agent agent);
}
