package ru.temansky.tempcard.webcore.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.temansky.tempcard.webcore.models.SensorValue;

public interface SensorValueRepository extends CrudRepository<SensorValue, Long> {
}
