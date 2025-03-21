# Supply Manager REST API

Supply Manager REST API — это веб-приложение для управления цепочками поставок.

---

## Основные возможности

- **Управление поставщиками:**  
  CRUD-операции для поставщиков.

- **Управление продуктами:**  
  CRUD-операции для продуктов. 

- **Управление доставками:**  
  CRUD-операции для доставок с набором позиций (DeliveryItem). Каждая доставка привязана к поставщику и имеет дату доставки.

- **Управление ценами поставщиков на продукцию:**  
  Установка фиксированных цен для определенных продуктов на заданный период. Это позволяет автоматически подставлять цену в доставках на основании даты.

- **Генерация отчетов:**  
  Формирование отчетов по доставкам, с агрегацией данных о поставках, включая итоговую стоимость, количество и другие параметры. Возможна фильтрация по поставщику и продукту.

---

## Технологии и зависимости

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **Hibernate ORM**
- **PostgreSQL**
- **Maven**
- **JUnit 5, Mockito**

---

## Установка и запуск
Скачиваем репозиторий:
```bash
git clone git@github.com:rodemark/supply-manager-rest-api.git
```
Создаем .env в корне проекта, который должен иметь подобное содержание:
```aiignore
DB_URL=jdbc:postgresql://supply-manager-postgres:5432/supply_manager_db

DB_NAME=supply_manager_db
DB_USERNAME=postgres
DB_PASSWORD=postgres

FRONTEND_URL=http://localhost:5173/
```

Создаем сеть:
```bash
docker network create supply-manager-network
```

Запускаем docker-compose:
```bash
docker-compose up -d
```
Сервис будет доступен по адресу: http://localhost:8080 

Как поднять frontend смотреть здесь: https://github.com/rodemark/supply-manager-frontend

---

## Архитектура

- **Контроллеры (Controllers):**  
  Обрабатывают HTTP-запросы и возвращают данные в формате JSON.

- **Сервисы (Services):**  
  Содержат бизнес-логику, включая валидацию, уникальные проверки, агрегацию данных и т.д.

- **Репозитории (Repositories):**  
  Используют Spring Data JPA для работы с базой данных.

- **DTO (Data Transfer Objects):**  
  Обеспечивают преобразование данных между слоями приложения, изолируя внутреннюю модель от внешнего API.

---

## Примеры использования API

### Поставщики
- **GET** `/api/v1/suppliers` — получение списка всех поставщиков
- **POST** `/api/v1/suppliers` — создание нового поставщика
- **PUT** `/api/v1/suppliers/{id}` — обновление данных поставщика
- **DELETE** `/api/v1/suppliers/{id}` — удаление поставщика

### Продукты
- **GET** `/api/v1/products` — получение списка продуктов
- **POST** `/api/v1/products` — создание продукта
- **PUT** `/api/v1/products/{id}` — обновление продукта
- **DELETE** `/api/v1/products/{id}` — удаление продукта

### Доставки
- **GET** `/api/v1/deliveries` — получение списка доставок
- **POST** `/api/v1/deliveries` — создание доставки
- **PUT** `/api/v1/deliveries/{id}` — обновление доставки
- **DELETE** `/api/v1/deliveries/{id}` — удаление доставки

### Отчеты
- **GET** `/api/v1/reports?supplierId=1&startDate=2025-03-01&endDate=2025-03-31&productId=1` — формирование отчета по доставкам с возможностью фильтрации по поставщику и продукту