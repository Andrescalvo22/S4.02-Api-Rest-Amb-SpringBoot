## 📄 Description

This project consists of a Spring Boot REST API to manage Fruits and Providers.
It implements CRUD operations, input validation, DTO mapping, entity relationships, global exception handling, and unit testing.

Fruit entities reference a Provider (ManyToOne), while each Provider may have multiple Fruits (OneToMany).

---

## 💻 Technologies Used

-Java 17

-Spring Boot

-Spring Web

-Spring Data JPA

-Jakarta Validation

-H2 Database

-Lombok

-JUnit 5 / Mockito

-Maven

## 📋 Requirements

-JDK 17+

-Maven 3.9+

## 🛠️ Installation

1. Clone the repository
```bash
git clone https://github.com/user/fruit-api.git
```
2. Enter project folder
```bash
cd fruit-api
```
3. Build the application
```bash
mvn clean install
```
---

## ▶️ Execution

1. Start the application:
```bash
mvn spring-boot:run
```
2. Server will be available at:
```bash
http://localhost:8080
```
---

## 🌐 Deployment

To deploy in production:

1. Configure database credentials in application.properties

2. Build JAR:
   ```bash
   mvn package
   ```
3. Run
   ```bash
   java -jar target/*.jar

---

## 🤝 Contributions

-Create a branch
```bash
git checkout -b feature/my-feature

```
-Commit changes
```bash
git commit -m "feat: description"

```
-Open a Pull Request










