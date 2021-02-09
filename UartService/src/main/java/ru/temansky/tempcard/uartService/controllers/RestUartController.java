package ru.temansky.tempcard.UartService.controllers;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import ru.temansky.tempcard.UartService.Service.SerialService;
import ru.temansky.tempcard.UartService.models.arduino.MessageFromArduino;

@org.springframework.web.bind.annotation.RestController
public class RestUartController {
    @Autowired
    SerialService serialService;

    final Logger LOGGER = LoggerFactory.getLogger(RestUartController.class);

    @GetMapping("/getValues")
    public MessageFromArduino getTemp(@RequestParam(value = "name", defaultValue = "World") String name) {
        String output;
        try {
            //Получаем сообщение от Ардуино
            serialService.getStatus();
            output = serialService.getValues();

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        //Демаришализируем
        Gson gson = new Gson();
        MessageFromArduino messageFromArduino;
        try {
            messageFromArduino = gson.fromJson(output, MessageFromArduino.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Failed parse message from arduino.", e);
        }


        return messageFromArduino;
    }
}


