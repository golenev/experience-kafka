#### 1. Склонируйте репозиторий
```
https://github.com/golenev/experience-kafka.git
```

#### 2. Откройте терминал, перейдите в директорию с файлом docker-compose.yml и выполните команду:

```
docker-compose up -d 
```

#### 3. Убедитесь, что контейнеры запущены:

```
docker-compose ps
```

#### 4. Вы должны увидеть три запущенных контейнера.
![image](https://github.com/user-attachments/assets/13fecc76-cff5-4705-af00-a402b5bd68da)

#### 5. Запустите Spring приложение

#### 6. Откройте Postman или любой другой инструмент для отправки HTTP запросов. Отправьте на сервер запрос из курла ниже, чтобы создать администратора приложения:

```
curl --location --request POST 'localhost:6789/api/v1/add-user' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "admin",
    "password": "qwerty",
    "roles": "ROLE_ADMIN"
}'
```

#### 7. Откройте браузер на странице http://localhost:6789/login и пароль от созданного пользователя

![image](https://github.com/user-attachments/assets/5865643a-4fed-4a73-9573-97ba8d611691)


#### 8. Далее в веб интерфейсе приложения доступны следующие операции
![image](https://github.com/user-attachments/assets/99fa1852-ab8c-4d9d-9efc-26441e429bcf)

```
1. Показать текущие сообщения в кафке внутри созданного топика
2. Отправить в кафку сообщение через форму инпута
3. Посмотреть текущие сообщения, отправленные из кафки в связанную таблицу PosgreSQL
4. Отправить сообщения из Кафки в PostgreSQL при условии, что их накопилось больше или равно 10 штук
5. Удалить все сообщения из топика Кафки
6. Удалить все сообщения из связанной с кафкой таблицы PostgreSQL
7. Добавить нового пользователя приложения
```
