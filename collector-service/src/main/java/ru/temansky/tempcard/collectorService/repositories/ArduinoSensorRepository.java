package ru.temansky.tempcard.collectorService.repositories;

import ru.temansky.tempcard.collectorService.models.db.ArduinoSensor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArduinoSensorRepository extends CrudRepository<ArduinoSensor, Long> {
    List<ArduinoSensor> findByNameAndAgentId(String name,Long id_agent);

}
