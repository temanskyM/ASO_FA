package ru.temansky.tempcard.api.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.temansky.tempcard.api.models.Agent;
import ru.temansky.tempcard.api.models.ArduinoSensor;

import java.util.List;

public interface ArduinoSensorRepository extends CrudRepository<ArduinoSensor, Long> {
    List<ArduinoSensor> findByNameAndAgent(String name, Agent agent);
    List<ArduinoSensor> findAllByAgentId(Long id);
}
