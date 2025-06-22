# PetStore Application

A Spring Boot application to manage a virtual pet store with users, cats, and dogs.

---

## Requirements

- OpenJDK 24
- PostgresSQL 17.5
- Maven

---

## Setup Instructions

1. **Clone the repository**
2. Make sure PostgresSQL is up and running
2. Create PostgresSQL database (petstore) and 3 tables (users, pet and buy_history)
3. Create PostgresSQL user or use the default postgres one as it is currently in the application.properties
4. Update the application.properties accordingly
5. Run the Application via IDE or terminal (./mvnw spring-boot:run)
6. Endpoints can be tested via postman, port is the default one (8080)

## Endpoints

1. **GET /api/users - List all users**

2. **GET /api/pets - List all pets**

3. **POST /api/users/create - Create 10 users with random properties**

4. **POST /api/pets/create - Create 20 pets with random properties**

5. **POST /api/users/{userId}/buy/{petId} - Buy pet for user**

6. **POST /api/users/buy - Auto-buy pets for all users**

7. **GET /api/users/history-log - View buy history**

8. **POST /api/pets - Create pet** Request body with properties

9. **POST /api/users - Create user** Request body with properties