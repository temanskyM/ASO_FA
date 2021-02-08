package ru.tempCard.uartService.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tempCard.uartService.serial.Serial;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SerialService {
    ReentrantLock locker = new ReentrantLock();

    final Logger LOGGER = LoggerFactory.getLogger(SerialService.class);

    @Autowired
    private Serial serial;

    @PostConstruct
    private void postConstruct() {
        Thread newThread = new Thread(this::monitoring);
        newThread.start();
    }


    public synchronized String sendCommandAndReceive(String command) throws Exception {
        String receivedMessage;
        serial.writeSerial(command);
        receivedMessage = serial.readSerial();
        return receivedMessage;
}

    public boolean getStatus() throws Exception {
        String receivedMessage;
        receivedMessage = sendCommandAndReceive("GetStatus");
        if (receivedMessage.equals("Ok")) {
            return true;
        } else {
            LOGGER.warn("getStatus: Unknown response: " + receivedMessage);
            throw new Exception("Unknown response: " + receivedMessage);
        }
    }

    public String getValues() throws Exception {
        String receivedMessage;
        receivedMessage = sendCommandAndReceive("GetValues");
        return receivedMessage;
    }

    private void monitoring() {
        //Открываем соединение
        serial.openConnection();
        String receivedMessage = "";
        while (true) {
            //Раз в две секунды
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                if (getStatus())
                    LOGGER.info("Monitoring: Port is open. Arduino is alive. Work in normal mode.");
            } catch (Exception e) {
                if (e.getMessage().contains("Unknown response")) {
                    LOGGER.error("Monitoring: " + e.getMessage());
                } else {
                    LOGGER.error("Monitoring: Serial port is closed! Try to restore connection");
                    serial.openConnection();
                }
            }
        }
    }
}
