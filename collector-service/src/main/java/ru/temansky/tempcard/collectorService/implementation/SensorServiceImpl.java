package ru.temansky.tempcard.collectorService.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.temansky.tempcard.collectorService.models.Sensor;
import ru.temansky.tempcard.collectorService.service.SensorService;

import java.util.List;

@Service
public class SensorServiceImpl implements SensorService {
    private static final Logger log = LoggerFactory.getLogger(SensorServiceImpl.class);

    final private String URL = "http://172.31.21.191:20084/api/";
    final WebClient webClient = WebClient.create(URL);

    @Override
    public List<Sensor> findAll() {
        return webClient.get().uri("/sensors").retrieve().bodyToFlux(Sensor.class).collectList().block();
    }

    @Override
    public List<Sensor> findAllByAgentId(Long agentId) {
        return webClient.get().uri("/agents/" + agentId + "/sensors").retrieve().bodyToFlux(Sensor.class).collectList().block();
    }

    @Override
    public Sensor findById(Long id) {
        return webClient.get().uri("/sensors/" + id).retrieve().bodyToMono(Sensor.class).block();
    }

    @Override
    public void addToAgent(Sensor sensor, Long agentId) {
        log.info("Add new sensor with name: " + sensor.getName() + " to agent with Id:" + agentId + " to DB");
        webClient.post()
                .uri("/agents/" + agentId + "/sensors")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(sensor), Sensor.class)
                .exchange().block();
    }


    @Override
    public void addAllToAgent(List<Sensor> sensors, Long agentId) {
        for (Sensor newSensor : sensors) {
            addToAgent(newSensor, agentId);
        }
    }

}
