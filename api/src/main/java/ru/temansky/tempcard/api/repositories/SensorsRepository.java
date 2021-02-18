package ru.temansky.tempcard.api.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.temansky.tempcard.api.models.Agent;
import ru.temansky.tempcard.api.models.Sensor;

import java.util.List;

public interface SensorsRepository extends PagingAndSortingRepository<Sensor, Long> {
    List<Sensor> findByNameAndAgent(String name, Agent agent);
}
