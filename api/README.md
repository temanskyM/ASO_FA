<H1> Модуль API
Данный модуль обеспечивает взаимодейстие Базы Данных с пользователями при помощи HTTP-запросов:
На данный момент существуют следующие команды:
//agents
Получить: curl -v GET localhost:8080/api/agents
Добавить: curl -X POST localhost:8080/api/agents/ -H 'Content-type:application/json' -d '{"hostname": "Зацеп", "ip": "172.31.21.191","port": "20080"}'
Изменить: curl -X PUT localhost:8080/api/agents/19 -H 'Content-type:application/json' -d '{"name":"newTest"}'
Удалить:  curl -X DELETE localhost:8080/api/agents/21

//sensor
Получить: curl -v GET localhost:8080/api/sensors
Добавить: curl -X POST localhost:8080/api/sensors/ -H 'Content-type:application/json' -d '{"name": "test2"}'
Изменить: curl -X PUT localhost:8080/api/sensors/19 -H 'Content-type:application/json' -d '{"name":"newTest"}'
Удалить:  curl -X DELETE localhost:8080/api/sensors/21

//sensorValues
Получить: curl -v GET localhost:8080/api/sensorValues
Добавить: curl -X POST localhost:8080/api/sensors/1/sensorValues -H 'Content-type:application/json' -d '{"temp": "10.8", "hum": "60.2"}'
Изменить: curl -X PUT localhost:8080/api/sensorValues/24 -H 'Content-type:application/json' -d '{"temp": "12", "hum": "65"}'
Удалить:  curl -X DELETE localhost:8080/api/sensorValues/24
