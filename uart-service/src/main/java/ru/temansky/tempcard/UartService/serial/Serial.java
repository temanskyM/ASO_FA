package ru.temansky.tempcard.UartService.serial;


import com.fazecast.jSerialComm.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Properties;

@Component
public class Serial {
    private static SerialPort serialPort;
    private String comPort;
    private int baudrate;

    private final int LEN_BUFFER = 2000;

    InputStream in;
    OutputStream out;

    final Logger LOGGER = LoggerFactory.getLogger(Serial.class);

    public Serial() throws Exception {
        try {
            loadSettings();

            serialPort = SerialPort.getCommPort(this.comPort);
            serialPort.setBaudRate(this.baudrate);
            //serialPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 100, 0);
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 10000, 0);
            printCurrentSettings();
        } catch (IOException ioException) {
            LOGGER.error("File settings.xml not found. Default file was created. Configure pls.\n");
            saveDefaultSettings();
            System.out.println("List of available port: \n" + printAvailableComPorts());
            throw new Exception("Settings is not loaded. Unable to start communication.");
        }
    }

    private void loadSettings() throws IOException {
        //Load Settings
        Properties loadProps = new Properties();
        loadProps.loadFromXML(new FileInputStream("settings.xml"));
        comPort = loadProps.getProperty("comPort");
        baudrate = Integer.parseInt(loadProps.getProperty("baudrate"));

    }

    public static void saveDefaultSettings() {
        // Save Settings
        Properties saveProps = new Properties();
        saveProps.setProperty("comPort", "COM1");
        saveProps.setProperty("baudrate", "9600");
        try {
            saveProps.storeToXML(new FileOutputStream("settings.xml"), "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printCurrentSettings() {
        System.out.println("Serial. Current settings:");
        System.out.print("\t Port: ");
        System.out.println(this.comPort);
        System.out.print("\t Baudrate: ");
        System.out.println(this.baudrate);
    }

    public static String printAvailableComPorts() {
        StringBuilder stringBuilder = new StringBuilder();
        SerialPort[] portNames = SerialPort.getCommPorts();
        for (SerialPort portName : portNames) {
            stringBuilder.append("  " + portName.getSystemPortName() + " - " + portName.getDescriptivePortName() + "\n");
        }
        return stringBuilder.toString();
    }

    public boolean openConnection() {
        try {
            //Создаем порт
            serialPort.openPort();
            //serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 1000, 0);

            in = serialPort.getInputStream();
            //out=comPort.getOutputStream();

            //Делаем паузу после инициализации
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!serialPort.isOpen()) {
                throw new Exception("Unable to access port. May be it busy?");
            }

        } catch (Exception exception) {
            LOGGER.error(exception.getMessage(), exception.getStackTrace()[0]);
            return false;
        }
        LOGGER.info("Init port is successful");
        return true;
    }

    public String readSerial() throws Exception {
        StringBuilder readMessage = new StringBuilder();
        Character readChar;
        try {
            for (int i = 0; i < LEN_BUFFER; i++) {
                readChar = (char) in.read();
                //if (readChar==0)
                if (readChar.equals('\n'))//Если найден символ перехода строки, то завершаем
                {
                    if (readMessage.toString().trim().equals("Failed to read from DHT sensor!")) {
                        readMessage = new StringBuilder();
                        continue;
                    }//Если пришла ошибка при считывании с датчика, то пробуем еще раз
                    break;
                } else
                    readMessage.append(readChar);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            //e.printStackTrace();
            //throw new Exception("Read from serial: Timeout");
            throw new Exception(e.getMessage());
        }

        //System.out.println("Получено сообщение: ");
        //System.out.println(readMessage);
        return readMessage.toString().trim();//Дополнительно
    }

    public void writeSerial(String message) {
        serialPort.writeBytes((message + "\n").getBytes(), (message + "\n").length());
    }


}
