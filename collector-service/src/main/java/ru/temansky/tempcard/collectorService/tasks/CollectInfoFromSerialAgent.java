package ru.temansky.tempcard.collectorService.tasks;



import ru.temansky.tempcard.UartService.models.arduino.*;
import ru.temansky.tempcard.collectorService.models.db.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.temansky.tempcard.collectorService.models.db.ArduinoSensor;
import ru.temansky.tempcard.collectorService.models.db.SensorValue;
import ru.temansky.tempcard.collectorService.repositories.AgentsRepository;
import ru.temansky.tempcard.collectorService.repositories.ArduinoSensorRepository;
import ru.temansky.tempcard.collectorService.repositories.SensorValueRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
public class CollectInfoFromSerialAgent {
    private static final Logger log = LoggerFactory.getLogger(CollectInfoFromSerialAgent.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private AgentsRepository agentsRepository;

    @Autowired
    private ArduinoSensorRepository arduinoSensorRepository;

    @Autowired
    private SensorValueRepository sensorValueRepository;


    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));

        //Получаем список агентов и пытаем получить от них JSON
        List<Agent> agentList = agentsRepository.findAll();
        for (Agent agent : agentList) {
            RestTemplate restTemplate = new RestTemplate();
            try {
                //Получаем json от агента
                MessageFromArduino messageFromArduino = restTemplate.getForObject("http://" + agent.getIp() + ":" + agent.getPort() + "/getValues", MessageFromArduino.class);

                //Проходимся по каждому сенсору из полученного сообщения
                for (Sensor sensorFromArduino : messageFromArduino.getSensors()) {
                    //Ищем нужный сенсор
                    List<ArduinoSensor> arduinoSensorsFromDb = arduinoSensorRepository.findByNameAndAgentId(sensorFromArduino.getName(), agent.getId());
                    ArduinoSensor arduinoSensor;
                    if (arduinoSensorsFromDb.size() > 0) {//Если нашли то, что надо
                        arduinoSensor = arduinoSensorsFromDb.get(0);
                    } else {
                        log.warn("Sensor (" + sensorFromArduino.getName() + ") not found");
                        //Добавляем в базу
                        arduinoSensor = new ArduinoSensor(sensorFromArduino.getName(), agent);
                        arduinoSensorRepository.save(arduinoSensor);
                    }

                    //Добавляем новые значения в базу
                    sensorValueRepository.save(new SensorValue(sensorFromArduino.getTemp(), sensorFromArduino.getHum(), LocalDateTime.now(), arduinoSensor));

                    //И добавляем к нему новые значения
                    log.info("Agent: " + agent.getHostname() + " sensor: " + sensorFromArduino.getName() + " temp: " + sensorFromArduino.getTemp() + " hum: " + sensorFromArduino.getHum());

                }
            } catch (Exception e) {
                log.info(e.getMessage());
            }

        }

        //log.info("The time is after execute {}", dateFormat.format(new Date()));

    }
}
