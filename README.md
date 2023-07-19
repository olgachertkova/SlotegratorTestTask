### Task:
**Автоматизировать REST API-тесты на Java используя любой соответствующий инструмент (например RestAssured, Feign, Retrofit, Unirest и т.д. - мы в частности используем Feign и RestAssured):**

Получить токен пользователя (/api/tester/login)
Ожидаемый результат: HTTP response code 200, ответ содержит токен пользователя

Зарегистрировать игроков (12 штук) (/api/automationTask/create)
Ожидаемый результат: HTTP response code 201, ответ соответствует документации

Запросить данные профиля созданного игрока (/api/automationTask/getOne)
Ожидаемый результат: HTTP response code 200, ответ соответствует документации

Запросить данные всех пользователей и отсортировать их по имени (/api/automationTask/getAll)

Удалить всех ранее созданных пользователей (/api/automationTask/deleteOne/{id})

Запросить список всех пользователей и убедиться что он пустой (/api/automationTask/getAll)

### Description:

## The next components must be installed:
#### Java SE Development Kit 11 (https://www.oracle.com/java/technologies/javase-downloads.html)
#### Apache Maven: (https://maven.apache.org/download.cgi)

### Running All tests using parameters:

` mvn clean test -Demail="olga@chertkova.info" -Dpassword="mT5BeDqu2sYb"
`
### Allure report generation:

`mvn clean test
`

`mvn allure:serve
`

