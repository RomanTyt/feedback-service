# feedback-service

Сервис для работы с отзывами клиентов

Функционал сервиса:
* создание и редактирование отзывов к заказам

Сервис позволяет:
* Создать новый отзыв
* Редактировать отзыв
* Найти отзыв по id
* Удалить отзыв и связанные с ним данные(ответ организации и комментарии)
* Оценить отзыв(лайк/дизлайк)
* Добавить ответ на отзыв от организации
* Получить ответ на отзыв от организации
* Добавить комментарий к отзыву от другого пользователя
* Получить все комментарии к отзыву

Для локального запуска проекта понадобится:
* IntelliJ IDEA
* JDK 17
* [Ссылка](https://github.com/RomanTyt/feedback-service.git) на репозиторий
* [API проекта](http://localhost:8080/swagger-ui/index.html#/)

В проекте подключены (автоматически подтягиваются):
* _H2_ - легковесная база данных Java с открытым исходным кодом
* _Liquibase_ - автоматическое развертывание таблиц, необходимых для начала работы
* _Swagger_ - автоматически генерирует спецификацию проекта

[![codecov](https://codecov.io/gh/RomanTyt/feedback-service/branch/develop/graph/badge.svg?token=3SCTM2S7PD)](https://codecov.io/gh/RomanTyt/feedback-service)
[![codecov](https://codecov.io/gh/RomanTyt/feedback-service/branch/develop/graphs/sunburst.svg?token=3SCTM2S7PD)](https://codecov.io/gh/RomanTyt/feedback-service)
