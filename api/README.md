# Модуль API

Данный модуль обеспечивает взаимодействие Базы Данных с пользователями при помощи HTTP-запросов.

Описание API:

*agents:*

> 1. Получить:
>
>    `curl -v GET localhost:8080/api/agents`
>
> 2. Добавить: 
>
>    `curl -X POST localhost:8080/api/agents/ -H 'Content-type:application/json' -d '{"hostname": "Зацеп", "ip": "172.31.21.191","port": "20080"}'`
>
> 3. Изменить: 
>
>    `curl -X PUT localhost:8080/api/agents/19 -H 'Content-type:application/json' -d '{"hostname": "Зацеп2", "ip": "172.31.21.191","port": "20080"}'`
>
> 4. Удалить:  
>
>    `curl -X DELETE localhost:8080/api/agents/21`
>

*sensors:*

> 1. Получить: 
>
>    `curl -v GET localhost:8080/api/sensors`
>
> 2. Добавить: 
>
>    `curl -X POST localhost:8080/api/sensors/ -H 'Content-type:application/json' -d '{"name": "test2"}'`
>
> 3. Изменить: 
>
>    `curl -X PUT localhost:8080/api/sensors/19 -H 'Content-type:application/json' -d '{"name":"newTest"}'`
>
> 4. Удалить:  
>
>    `curl -X DELETE localhost:8080/api/sensors/21`
>

*sensorValues:*

> 1. Получить: 
>
>    `curl -v GET localhost:8080/api/sensorValues`
>
> 2. Добавить: 
>
>    `curl -X POST localhost:8080/api/sensors/1/sensorValues -H 'Content-type:application/json' -d '{"temp": "10.8", "hum": "60.2"}'`
>
>    `curl -X POST localhost:8080/api/agents/1/sensors/1/sensorValues -H 'Content-type:application/json' -d '{"temp": "10.8", "hum": "60.2"}'`
>
> 3. Изменить: 
>
>    `curl -X PUT localhost:8080/api/sensorValues/24 -H 'Content-type:application/json' -d '{"temp": "12", "hum": "65"}'`
>
> 4. Удалить:  
>
>    `curl -X DELETE localhost:8080/api/sensorValues/24`
>

