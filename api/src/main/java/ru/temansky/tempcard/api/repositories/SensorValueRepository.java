package ru.temansky.tempcard.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.temansky.tempcard.api.models.Sensor;
import ru.temansky.tempcard.api.models.SensorValue;

public interface SensorValueRepository extends PagingAndSortingRepository<SensorValue, Long> {
    Page<SensorValue> findAllBySensor(Sensor sensor, Pageable page);
}
