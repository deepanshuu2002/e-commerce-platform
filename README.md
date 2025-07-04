# 🛒 EcommercePlatform - Product Catalogue Backend

[![Build](https://img.shields.io/badge/build-passing-brightgreen)](https://maven.apache.org/)
[![Java](https://img.shields.io/badge/java-17-blue.svg)](https://adoptopenjdk.net/)
[![Spring Boot](https://img.shields.io/badge/spring--boot-3.1.3-brightgreen)](https://spring.io/projects/spring-boot)
[![Test Coverage](https://img.shields.io/badge/tests-100%25-success)](#testing)

A professional, modern REST API backend for e-commerce product catalogue management, built with **Spring Boot** and **Java 17**.

---

## 🚀 Features

- Full **CRUD** for products
- **Advanced search/filter** (by name, category, price range)
- **H2 in-memory database** (easy to swap for file-based/production)
- **TDD-style unit & controller tests** (JUnit 5 & Mockito)
- **Lombok** for clean models
- Easily customizable

---

## 🗃️ Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 Database
- Maven
- Lombok
- JUnit 5 & Mockito

---

## 🚦 Getting Started

### 1. **Clone the Repository**
```sh
git clone https://github.com/your-username/EcommercePlatform.git
cd EcommercePlatform

2. Build and Run

mvn clean install
mvn spring-boot:run
Server will start at http://localhost:8080


🛠️ API Endpoints
Method	Endpoint	Description
GET	/api/products	Get all products
GET	/api/products/{id}	Get product by ID
POST	/api/products	Create a new product
PUT	/api/products/{id}	Update product by ID
DELETE	/api/products/{id}	Delete product by ID
GET	/api/products/search	Search/filter products

🔍 Search Endpoint Example

GET /api/products/search?name=phone&category=Electronics&minPrice=100&maxPrice=1000
🗄️ Database
Default: H2 in-memory (jdbc:h2:mem:testdb)

Access H2 Console

JDBC URL: jdbc:h2:mem:testdb

User: sa

Password: (blank)

Preload Data Example (src/main/resources/data.sql)

INSERT INTO PRODUCT (name, description, category, price) VALUES
('Apple iPhone 14', '128GB, Midnight, 5G capable smartphone.', 'Electronics', 799.99),
('Nike Air Max 270', 'Comfortable running shoes with Max Air cushioning.', 'Footwear', 129.99);
-- Add more products as needed!
🧪 Testing
All main logic is covered with TDD-style unit and controller tests.


Run all tests:

mvn test
Service tests:
src/test/java/com/example/productcatalogue/service/ProductServiceTest.java

Controller tests:
src/test/java/com/example/productcatalogue/controller/ProductControllerTest.java

⚙️ Customization
Switch to file-based H2 DB:
Edit src/main/resources/application.properties:

spring.datasource.url=jdbc:h2:file:./data/productdb
Add Swagger/OpenAPI:
Add springdoc-openapi-ui dependency and visit /swagger-ui.html

🤝 Contributing
Pull requests are welcome.
Open an issue for bugs, enhancements, or feature suggestions.

📝 License
MIT License.
Free to use, modify, and share.

