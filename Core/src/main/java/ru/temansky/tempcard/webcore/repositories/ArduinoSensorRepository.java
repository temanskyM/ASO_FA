package ru.temansky.tempcard.webcore.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.temansky.tempcard.webcore.models.Agent;
import ru.temansky.tempcard.webcore.models.ArduinoSensor;

import java.util.List;

public interface ArduinoSensorRepository extends CrudRepository<ArduinoSensor, Long> {
    List<ArduinoSensor> findByNameAndAgent(String name, Agent agent);
    List<ArduinoSensor> findAllByAgentId(Long id);
}
