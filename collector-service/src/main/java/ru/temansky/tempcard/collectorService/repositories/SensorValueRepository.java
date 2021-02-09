package ru.temansky.tempcard.collectorService.repositories;

import ru.temansky.tempcard.collectorService.models.db.SensorValue;
import org.springframework.data.repository.CrudRepository;

public interface SensorValueRepository extends CrudRepository<SensorValue, Long> {
}
