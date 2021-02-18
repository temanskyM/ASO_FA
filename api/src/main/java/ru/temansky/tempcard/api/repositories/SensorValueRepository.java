package ru.temansky.tempcard.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.temansky.tempcard.api.models.SensorValue;

import java.awt.print.Pageable;
import java.util.Collection;

public interface SensorValueRepository extends PagingAndSortingRepository<SensorValue, Long> {
    //Page<SensorValue> findAll(Pageable page);
}
