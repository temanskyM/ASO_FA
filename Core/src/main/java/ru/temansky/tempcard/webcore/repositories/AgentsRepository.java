package ru.temansky.tempcard.webcore.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.temansky.tempcard.webcore.models.Agent;
import java.util.List;

public interface AgentsRepository extends CrudRepository<Agent, Long> {
    List<Agent> findByHostname(String hostname);
    Agent findById(long id);

    List<Agent> findAll();
}
