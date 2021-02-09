package ru.temansky.tempcard.collectorService.repositories;

import ru.temansky.tempcard.collectorService.models.db.Agent;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AgentsRepository extends CrudRepository<Agent, Long> {
    List<Agent> findByHostname(String hostname);
    Agent findById(long id);
    List<Agent> findAll();
}
