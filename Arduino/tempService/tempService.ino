//Libraries
#include <DHT.h>;
#include <ArduinoJson.h>


#define PIN_LED 13

const int COUNT_SENSORS = 3;
float sensorValues[COUNT_SENSORS][2];

//Конфигурация датчиков
DHT dht(5, DHT22); //На 7-ом пине висит датчик
DHT dht1(6, DHT11); //На 7-ом пине висит датчик
DHT dht2(7, DHT11); //На 7-ом пине висит датчик
DHT sensorList[COUNT_SENSORS] = {dht, dht1, dht2};

String sensorNames[COUNT_SENSORS]={"D5","D6","D7"};

String sdata = ""; //Переменная для получения команд


void setup() {
  Serial.begin(115200);
  pinMode(PIN_LED, OUTPUT);
  for (int i = 0 ; i < COUNT_SENSORS; i++) {
    sensorList[i].begin();
  }
}

void loop() {
  CheckSerialInput();//Считываем данные с Serial входа
}

void CheckSensors()
{
  for (int i = 0 ; i < COUNT_SENSORS; i++) {
    float h = sensorList[i].readHumidity();
    // Reading temperature or humidity takes about 250 milliseconds!
    float t = sensorList[i].readTemperature();
    // Read temperature as Fahrenheit (isFahrenheit = true)
    // Check if any reads failed and exit early (to try again).
    if (isnan(h) || isnan(t)) {
      Serial.println("Failed to read from DHT sensor!");
      //return;
    }
    //int hum = dht.readHumidity();
    //int temp = dht.readTemperature();
    sensorValues[i][0] = t;
    sensorValues[i][1] = h;
    //Serial.print(" temp:");Serial.println(t);
    //Serial.print(" hum:");Serial.println(h);

  }
  digitalWrite(PIN_LED, HIGH);
  digitalWrite(PIN_LED, LOW);

}

void CheckSerialInput()
{
  byte ch;
  if (Serial.available()) {
    ch = Serial.read();

    sdata += (char)ch;

    if (ch == '\n') { // Command received and ready.
      sdata.trim();
      // Process command in sdata.
      ProcessCommand();

      sdata = ""; // Clear the string ready for the next command.
    } // if \r
  }  // available

}
void ProcessCommand() {
  if (!sdata.indexOf("GetSensors")) {//Получить количество сенсоров
    Serial.print("Sensors count: ");
    Serial.println(COUNT_SENSORS);
    return;
  }

  if (!sdata.indexOf("GetValues")) {//Получить данные с сенсоров
    CheckSensors();//Считываем все показания
    PrintValues();
    return;
  }
  if (!sdata.indexOf("GetStatus")) {
    Serial.println("Ok");
    return;
  }
  Serial.println("Error");

}
void PrintValues() {
  StaticJsonDocument<200> docSummary;
  JsonArray data = docSummary.createNestedArray("sensors");

  for (int i = 0 ; i < COUNT_SENSORS; i++) {
    StaticJsonDocument<100> doc;
    doc["name"] = sensorNames[i];

    //Температура
    if (isnan(sensorValues[i][0])) {
      doc["temp"] = -999;
    }
    else
      doc["temp"] = sensorValues[i][0];

    //Влажность
    if (isnan(sensorValues[i][1])) {
      doc["hum"] = -999;
    }
    else
      doc["hum"] = sensorValues[i][1];

    data.add(doc);
  }

  serializeJson(docSummary, Serial);
  Serial.println();
}
