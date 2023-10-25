## Тестовое окружение:

1. IntelliJ IDEA 2023.2.3 (Community Edition)
2. DBeaver 
3. Docker
4. Chrome Версия 118.0.5993.88

## Шаги для запуска автотестов:
1. Запустить IntelliJ IDEA
2. Запустить Docker 
3. В терминале ввести команду - `docker compose down` 
4. После того как были удалены 3 контейнера, в терминале ввести команду - `docker compose up`
5. Открыть вторую вкладку терминала и ввести команду - `java -jar ./artifacts/aqa-shop.jar`
6. Для запуска тестов необходимо в третьей вкладке терминала ввести команду - `./gradlew clean test --info`
7. После прохождения автотестов ввести в терминале команду - `./gradlew allureserve` - для генерации отчетов на Allure 
8. После проделанной работы, а также просмотра отчета по автотестам необходимо ввести команду для остановки aqa-shop.jar `Ctrl+C` 
9. В файле [application.properties](application.properties) необходимо заменить данные для MySQL на PostgreSQL данными ниже:
   `spring.datasource.url=jdbc:postgresql://localhost:5432/app`
10. Необходимо запустить автотесты повторяя шаги 3-7