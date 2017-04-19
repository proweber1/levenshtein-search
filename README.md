Это небольшое REST API предназначено для того чтобы искать ответы
на вопросы пользователя с помощью измерения расстояний между предложениями
(Алгоритм Левенштейна), для поиска расстояний используется PostgreSQL
расширение.

### Запуск проекта

Для запуска необходимы следующие технологии

1. Java 8
2. Maven 3
3. PostgreSQL 9.4+

Выполните эту команду в консоле PostgreSQL для включения нужного
расширения

```sql
CREATE EXTENSION fuzzystrmatch
```

[Документация по расширению](https://www.postgresql.org/docs/9.6/static/fuzzystrmatch.html)

После этого нужно скомпилировать проект

```bash
mvn clean package
```

Дальше настройте подключение к базе данных в `bot-configuration.yaml` файле

```yaml
database:
    # the name of your JDBC driver
    driverClass: org.postgresql.Driver

    # the username
    user: bot

    # the password
    password: XWuiYdlj

    # the JDBC URL
    url: jdbc:postgresql://localhost/bot
```

Нужно накатить миграции потом

```bash
java -jar target/bot-1.0-SNAPSHOT.jar db migrate bot-configuration.yaml
```

И запустите приложение

```bash
java -jar target/bot-1.0-SNAPSHOT.jar server bot-configuration.yaml
```

После этого будет доступен URL http://localhost:8080/questions на который в query
параметрах надо передать `query=QUESTION`.

P.S Не успел сделать swagger документацию.