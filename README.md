### 1. Склонируйте репозиторий

### 2. Откройте терминал, перейдите в директорию с файлом docker-compose.yml и выполните команду:

```
docker-compose up -d 
```

Эта команда запустит Zookeeper и Kafka в фоновом режиме.

### 3. Убедитесь, что контейнеры запущены:

```
docker-compose ps
```

### Вы должны увидеть два запущенных контейнера: один для Zookeeper и один для Kafka.

### 4. Создание темы(топика) в Kafka. Откройте новое окно терминала и создайте тему с именем test-topic:

```
   docker-compose exec kafka kafka-topics --create --topic test-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```

### 5. Запустите Spring приложение

### 6. Откройте Postman или любой другой инструмент для отправки HTTP запросов. Отправьте POST запрос на URL http://localhost:6789/api/v1/messages с телом запроса в формате JSON:

```
{
    "content": "Hello, Kafka!"
}
```

### 7. Откройте вэб интерфейс базы данных H2 http://localhost:6789/h2-console/

### 8. Проверьте, что Кафка доставила сообщение в базу SELECT * FROM MESSAGE

### 9. Проверить в консоли содержимое топика после отправки в него сообщений можно командой через терминал контейнера Кафки

### 10. Сначала открываем консоль контейнера, в котором у нас Кафка

```
docker-compose exec kafka bash
```

### 11. В консоли Кафки пишем команды для просмотреа сообщений нашего топика, где имя топика "send-topic"

```
kafka-console-consumer --topic send-topic --from-beginning --bootstrap-server localhost:9092
```

### 12. После запуска Spring приложения, на странице http://localhost:6789/index.html можно получить все имеющиеся сообщения из Кафки

### 13. Также можно добавить новые сообщения через POST запрос на эндпоинт http://localhost:6789/api/v1/send с телом вида 

```
["new message1", "new message 2", "new message 3"]
```