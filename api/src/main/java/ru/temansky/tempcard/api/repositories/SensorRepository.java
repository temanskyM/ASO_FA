package ru.temansky.tempcard.api.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.temansky.tempcard.api.models.Agent;
import ru.temansky.tempcard.api.models.Sensor;

import java.util.List;

public interface SensorRepository extends CrudRepository<Sensor, Long> {
    List<Sensor> findByNameAndAgent(String name, Agent agent);
    List<Sensor> findAllByAgentId(Long id);
}
