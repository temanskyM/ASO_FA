package ru.temansky.tempcard.collectorService.implementation;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.temansky.tempcard.api.models.Sensor;
import ru.temansky.tempcard.api.models.SensorValue;
import ru.temansky.tempcard.collectorService.service.SensorValueService;

import java.util.List;

@Service
public class SensorValueImpl  implements SensorValueService {
    final private String URL = "http://172.31.21.191:20084/api/";
    final WebClient webClient = WebClient.create(URL);

    @Override
    public List<SensorValue> findAll() {
        return webClient.get().uri("/sensorValues").retrieve().bodyToFlux(SensorValue.class).collectList().block();
    }

    @Override
    public List<SensorValue> findAllByAgentIdAndSensorId(Long agentId, Long sensorId) {
        return webClient.get().uri("/agents/" + agentId + "/sensors/" + sensorId + "sensorValues").retrieve().bodyToFlux(SensorValue.class).collectList().block();
    }

    @Override
    public SensorValue findById(Long id) {
        return webClient.get().uri("/sensorValues/" + id).retrieve().bodyToMono(SensorValue.class).block();
    }

    @Override
    public void addBySensorId(SensorValue sensorValue, Long agentId, Long sensorId) {
        webClient.post()
                .uri("/agents/" + agentId + "/sensors/" + sensorId + "/sensorValues/")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(sensorValue), SensorValue.class)
                .exchange().block();
    }
}
