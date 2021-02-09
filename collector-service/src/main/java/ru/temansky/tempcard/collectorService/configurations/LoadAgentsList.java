package ru.temansky.tempcard.collectorService.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadAgentsList {
    private static final Logger log = LoggerFactory.getLogger(LoadAgentsList.class);
// Временно. Для создания новых агентов
//    @Bean
//    CommandLineRunner initDatabase(AgentsRepository repository) {
//
//        return args -> {
//            log.info("Preloading " + repository.save(new Agent("room403", "127.0.0.1","8000")));
//            log.info("Preloading " + repository.save(new Agent("room404", "127.0.0.1","8001")));
//        };
//    }
}
