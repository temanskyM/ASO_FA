package ru.temansky.tempcard.collectorService.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.temansky.tempcard.collectorService.models.db.Agent;
import ru.temansky.tempcard.collectorService.models.db.ArduinoSensor;
import ru.temansky.tempcard.collectorService.models.db.SensorValue;

import java.util.List;

public interface SensorValueRepository extends CrudRepository<SensorValue, Long> {
}
