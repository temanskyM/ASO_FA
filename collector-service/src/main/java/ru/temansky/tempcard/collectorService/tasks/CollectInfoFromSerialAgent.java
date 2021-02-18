package ru.temansky.tempcard.collectorService.tasks;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.temansky.tempcard.UartService.models.arduino.MessageFromArduino;
import ru.temansky.tempcard.collectorService.models.Agent;
import ru.temansky.tempcard.collectorService.models.Sensor;
import ru.temansky.tempcard.collectorService.models.SensorValue;
import ru.temansky.tempcard.collectorService.service.AgentService;
import ru.temansky.tempcard.collectorService.service.SensorService;
import ru.temansky.tempcard.collectorService.service.SensorValueService;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CollectInfoFromSerialAgent {
    private static final Logger log = LoggerFactory.getLogger(CollectInfoFromSerialAgent.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    AgentService agentService;
    SensorService sensorService;
    SensorValueService sensorValueService;

    public CollectInfoFromSerialAgent(AgentService agentService, SensorService sensorService, SensorValueService sensorValueService) {
        this.agentService = agentService;
        this.sensorService = sensorService;
        this.sensorValueService = sensorValueService;
    }

    @Scheduled(fixedRate = 30000)
    public void reportCurrentTime() {
        //log.info("The time is now {}", dateFormat.format(new Date()));

        //Получаем список агентов и пытаем получить от них JSON
        List<Agent> agentList = agentService.getAll();
        for (Agent agent : agentList) {
            List<Sensor> sensorFromDbList = sensorService.findAllByAgentId(agent.getId());
//            RestTemplate restTemplate = new RestTemplate();
            try {
                //Получаем json от агента
                MessageFromArduino messageFromArduino = agentService.getResponse(agent);
//
                //Проверяем наличие новых датчиков, если обнаружатся новые,
                List<Sensor> newSensors = getNewSensors(messageFromArduino, sensorFromDbList);

                //Добавляем их в БД
                if (newSensors.size() > 0) {
                    log.info("Found new sensors. Begin add to DB");
                    sensorService.addAllToAgent(newSensors, agent.getId());
                    //Так как мы добавили новые сенсоры, то переполучаем список сенсоров c новыми айдишниками
                    sensorFromDbList = sensorService.findAllByAgentId(agent.getId());
                }

                //Добавляем значения в базу
                for (Sensor sensor : sensorFromDbList) {
                    //Находим показания этого датчика в сообщении
                    Optional<ru.temansky.tempcard.UartService.models.arduino.Sensor> sensorValue = Arrays.stream(messageFromArduino.getSensors())
                            .filter(e -> e.getName().equals(sensor.getName()))
                            .findFirst();
                    if (sensorValue.isPresent()) {
                        SensorValue sensorValueNew = new SensorValue(
                                sensorValue.get().getTemp(),
                                sensorValue.get().getHum(),
                                LocalDateTime.now());

                        sensorValueService.addBySensorId(sensorValueNew, agent.getId(), sensor.getId());
                    }
                }


//                    //Ищем нужный сенсор
//                    List<ArduinoSensor> arduinoSensorsFromDb = arduinoSensorRepository.findByNameAndAgentId(sensorFromArduino.getName(), agent.getId());
//                    ArduinoSensor arduinoSensor;
//                    if (arduinoSensorsFromDb.size() > 0) {//Если нашли то, что надо
//                        arduinoSensor = arduinoSensorsFromDb.get(0);
//                    } else {
//                        log.warn("Sensor (" + sensorFromArduino.getName() + ") not found");
//                        //Добавляем в базу
//                        arduinoSensor = new ArduinoSensor(sensorFromArduino.getName(), agent);
//                        arduinoSensorRepository.save(arduinoSensor);
//                    }
//
//                    //Добавляем новые значения в базу
//                    sensorValueRepository.save(new SensorValue(sensorFromArduino.getTemp(), sensorFromArduino.getHum(), LocalDateTime.now(), arduinoSensor));
//
//                    //И добавляем к нему новые значения
//                    log.info("Agent: " + agent.getHostname() + " sensor: " + sensorFromArduino.getName() + " temp: " + sensorFromArduino.getTemp() + " hum: " + sensorFromArduino.getHum());
//
            } catch (Exception e) {
                log.info(e.getMessage());
            }
//
        }

        //log.info("The time is after execute {}", dateFormat.format(new Date()));

    }

    private List<Sensor> getNewSensors(MessageFromArduino messageFromArduino, List<Sensor> sensorFromDbList) {
        //Получаем список имен сенсоров, которые уже в бд
        List<String> namesSensorsFromDB = sensorFromDbList.stream()
                .map(Sensor::getName)
                .collect(Collectors.toList());

        //Получаем список тех сенсоров, которых нет в базе
        return Arrays.stream(messageFromArduino.getSensors())
                .filter(newSensor -> !namesSensorsFromDB.contains(newSensor.getName())) //Если сенсор не содержится в БД
                .map(newSensor -> new Sensor(newSensor.getName())) //Создаем новый сенсор для передачи его сервису
                .collect(Collectors.toList());
    }
}
