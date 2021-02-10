package ru.temansky.tempcard.api.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.temansky.tempcard.api.models.SensorValue;

public interface SensorValueRepository extends CrudRepository<SensorValue, Long> {
}
