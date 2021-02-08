package ru.temansky.tempcard.webcore.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.temansky.tempcard.webcore.models.SensorValue;

import java.util.List;

public interface SensorValueRepository extends CrudRepository<SensorValue, Long> {
}
