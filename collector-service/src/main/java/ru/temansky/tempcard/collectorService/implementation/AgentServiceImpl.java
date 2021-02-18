package ru.temansky.tempcard.collectorService.implementation;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.temansky.tempcard.UartService.models.arduino.MessageFromArduino;
import ru.temansky.tempcard.collectorService.models.Agent;
import ru.temansky.tempcard.collectorService.service.AgentService;

import java.util.List;

@Service("AgentService")
public class AgentServiceImpl implements AgentService {
    final private String API_URL = "http://172.31.21.191:20084/api/";
    final WebClient webClientApi = WebClient.create(API_URL);

    @Override
    public List<Agent> getAll() {
        return webClientApi.get().uri("/agents").retrieve().bodyToFlux(Agent.class).collectList().block();
    }

    @Override
    public Agent getById(Long id) {
        return webClientApi.get().uri("/agents/" + id).retrieve().bodyToMono(Agent.class).block();
    }

    @Override
    public MessageFromArduino getResponse(Agent agent) {
        String agentURL = "http://"+ agent.getIp() + ":" + agent.getPort();
        WebClient webClientAgent = WebClient.create(agentURL);
        return webClientAgent.get().uri("/getValues").retrieve().bodyToMono(MessageFromArduino.class).block();
    }
}
