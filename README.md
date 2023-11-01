## Тестовое окружение:

1. IntelliJ IDEA 2023.2.3 (Community Edition)
2. DBeaver 
3. Docker
4. Chrome Версия 118.0.5993.88

## Шаги для запуска автотестов:
1. Запустить IntelliJ IDEA
2. Запустить Docker
3. Для запуска контейнеров необходимо ввести в терминале команду - `docker compose up`
4. Для запуска приложения необходимо ввести следующие команды:
   - `java -jar artifacts/aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3306/app` - для подключения к MySQL
   - `java -jar artifacts/aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app` - для подключения к PostgreSQL 
5. Для запуска тестов необходимо в третьей вкладке терминала ввести команду - `./gradlew clean test --info`
6. После прохождения автотестов ввести в терминале команду - `./gradlew allureserve` - для генерации отчетов на Allure 
7. После проделанной работы, а также просмотра отчета по автотестам необходимо ввести команду для остановки aqa-shop.jar `Ctrl+C`
8. Для удаления контейнеров ввести в терминале команду - `docker compose down`