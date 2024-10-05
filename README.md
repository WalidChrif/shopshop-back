# ShopShop Backend

This is the backend of the **ShopShop** e-commerce platform, built using Spring Boot. It provides a REST API for managing products, orders, and users, as well as authentication and authorization through Keycloak.

## Features

- REST API for product, order, and user management.
- Integration with MySQL for data persistence.
- Keycloak for authentication and authorization.
- Role-based access control for customers and administrators.
- Spring Security for securing the endpoints.

## Technology Stack

- **Spring Boot**: Backend framework.
- **Spring Data JPA**: ORM for database operations.
- **MySQL**: Relational database.
- **Spring Security**: Security framework.
- **Keycloak**: Authentication and authorization provider.
- **REST API**: For communication with the frontend.
- **Spring Web**: For building RESTful web services.

## Installation and Setup

### Prerequisites

- Java 11 or higher
- Maven or Gradle
- MySQL
- Keycloak (for authentication)

### Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/shopshop-backend.git
   ```
2. Navigate to the project directory:
   ```bash
   cd shopshop-backend
   ```
3. Configure the `application.properties` file with your MySQL and Keycloak settings:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/shopshop_db
   spring.datasource.username=your_db_username
   spring.datasource.password=your_db_password

   keycloak.auth-server-url=http://localhost:8080/auth
   keycloak.realm=your_realm
   keycloak.resource=your_client_id
   ```
4. Create the MySQL database:
   ```sql
   CREATE DATABASE shopshop_db;
   ```
5. Run the application:
   ```bash
   mvn spring-boot:run
   ```
6. The API will be available at `http://localhost:8080/api`.

### Keycloak Configuration

1. Install and run Keycloak.
2. Create a realm for ShopShop.
3. Add roles for `admin` and `customer`.
4. Configure a Keycloak client for the backend with proper redirect URIs.

## API Endpoints

- **Product API**: `/api/products`
- **Order API**: `/api/orders`
- **User API**: `/api/users`
- Authentication and authorization are managed using Keycloak tokens.
