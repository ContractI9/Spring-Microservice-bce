# Spring-Microservice-bce

A simple microservice architecture based backend using Spring Boot.

## Overview

This project implements a microservice architecture using the BCE (Boundary-Control-Entity) approach. It consists of two services: users and orders. These services communicate with each other using Feign. The backend uses a SQL database, and additional components include a Eureka server for service discovery and a config server for centralized configuration management.

## Structure

The project is organized into several key folders, each serving a specific purpose:

- **advice**: Contains the AppLogger, which tracks logs through services. The AppLogger provides a centralized logging mechanism for monitoring service activity and diagnosing issues.
  
- **boundary**: This folder contains controllers and APIs that define the external interface of the microservices. Controllers handle incoming HTTP requests and delegate business logic to the corresponding service components.

- **config**: Contains configurations for Redis and web security. The configuration files in this folder define settings related to Redis caching and security policies such as authentication and authorization.

- **control**: Houses service implementations such as UserService and Feign. These components encapsulate business logic and interact with repositories to perform CRUD operations on data.

- **dto**: Contains Data Transfer Objects (DTOs) that facilitate communication between different layers of the application. DTOs represent data structures exchanged between the frontend, backend, and external services.

- **entity**: Contains entity models or tables that represent domain objects and map to database tables. These entities define the structure and relationships of the data stored in the SQL database.

- **exception**: Contains custom-made exceptions and exception handlers. These components handle exceptional situations gracefully and provide meaningful error messages to clients.

- **filter**: Contains a JWT filter for authentication. The JWT filter intercepts incoming requests, verifies the authenticity of JSON Web Tokens (JWTs), and grants access to protected resources based on the token's claims.

- **mapper**: Contains entity-to-DTO mappers that transform data between entity and DTO representations. Mappers simplify the conversion process and ensure consistency in data transfer between layers.

- **repository**: Contains repository interfaces that define CRUD operations for interacting with the database. Repositories provide an abstraction layer for database access and enable separation of concerns between business logic and data access code.

- **specification**: Contains a file for building queries with operations like `LIKE`, `NOT LIKE`, etc. The specification file provides a reusable mechanism for constructing dynamic queries based on user-defined criteria.

- ## API Endpoints

### Get All Users
- **URL:** `/test/users`
- **Method:** `GET`
- **Authorization:** Requires `ADMIN` role
- **Description:** Retrieves a list of all users based on search criteria.
- **Request Body:** 
  - `List<SearchCriteria>`: List of search criteria to filter users (optional)
- **Response Body:** 
  - `List<UserDto>`: List of user data transfer objects (DTOs)
  
### Create User
- **URL:** `/test/createuser`
- **Method:** `POST`
- **Authorization:** Requires no authentication
- **Description:** Creates a new user.
- **Request Parameters:** 
  - `userPassword` (optional): User's password
- **Request Body:** 
  - `UserDto`: User data transfer object (DTO) containing user details
- **Response Body:** 
  - `UserDto`: User data transfer object (DTO) of the created user
  
### Hello
- **URL:** `/test/hello`
- **Method:** `GET`
- **Authorization:** Requires no authentication
- **Description:** A simple test endpoint to check server availability.
- **Response:** Returns an HTTP 200 OK response.

### Update User
- **URL:** `/test/users/{id}`
- **Method:** `PUT`
- **Authorization:** Requires `ADMIN` role
- **Description:** Updates an existing user.
- **Path Parameters:** 
  - `id`: ID of the user to be updated
- **Request Parameters:** 
  - `userPassword` (optional): User's password
- **Request Body:** 
  - `UserDto`: User data transfer object (DTO) containing updated user details
- **Response Body:** 
  - `UserDto`: User data transfer object (DTO) of the updated user
  
### Get User Orders
- **URL:** `/test/users/{id}/orders`
- **Method:** `GET`
- **Authorization:** Requires authentication
- **Description:** Retrieves orders associated with a specific user.
- **Path Parameters:** 
  - `id`: ID of the user
- **Response Body:** 
  - `List<OrderDto>`: List of order data transfer objects (DTOs) associated with the user
  
### Get User By ID
- **URL:** `/test/users/{id}`
- **Method:** `GET`
- **Authorization:** Requires authentication
- **Description:** Retrieves user details by ID.
- **Path Parameters:** 
  - `id`: ID of the user
- **Response Body:** 
  - `UserDto`: User data transfer object (DTO) of the requested user
  
### Delete User
- **URL:** `/test/users/{id}`
- **Method:** `DELETE`
- **Authorization:** Requires authentication
- **Description:** Deletes a user by ID.
- **Path Parameters:** 
  - `id`: ID of the user
- **Response:** Returns an HTTP 200 OK response.

### Filter Order
- **URL:** `/test/filterorder`
- **Method:** `POST`
- **Authorization:** Requires authentication
- **Description:** Filters orders based on provided criteria.
- **Request Body:** 
  - `List<SearchCriteria>`: List of search criteria to filter orders
- **Response Body:** 
  - `List<OrderDto>`: List of order data transfer objects (DTOs) matching the filter criteria
  
### Authenticate User
- **URL:** `/test/authenticate`
- **Method:** `POST`
- **Authorization:** Requires no authentication
- **Description:** Authenticates a user and generates a JWT token.
- **Request Body:** 
  - `JWTRequest`: Request containing username and password
- **Response Body:** 
  - `JWTResponse`: Response containing JWT token
  
### Save Role
- **URL:** `/test/addrole`
- **Method:** `POST`
- **Authorization:** Requires no authentication
- **Description:** Adds a new role.
- **Request Body:** 
  - `RoleDto`: Role data transfer object (DTO) containing role details
- **Response:** Returns a success message.

### Assign Role
- **URL:** `/test/assignrole`
- **Method:** `POST`
- **Authorization:** Requires no authentication
- **Description:** Assigns a role to a user.
- **Request Body:** 
  - `AssignRoleDto`: Request containing role and username
- **Response:** Returns a success message.

### Delete Role
- **URL:** `/test/deleterole`
- **Method:** `DELETE`
- **Authorization:** Requires `ADMIN` role
- **Description:** Deletes a role assigned to a user.
- **Request Body:** 
  - `AssignRoleDto`: Request containing role and username
- **Response:** Returns a success message.



## Usage

To run the project locally, follow these steps:

1. Clone the repository: `git clone <repository_url>`
2. Navigate to the project directory: `cd Spring-Microservice-bce`
3. Build the project: `mvn clean install`
4. Run the Eureka server: `cd eureka-server && mvn spring-boot:run`
5. Run the Config server: `cd config-server && mvn spring-boot:run`
6. Run the microservices: `cd <service_name> && mvn spring-boot:run` (repeat this step for each microservice)

Make sure to configure any necessary properties in the `application.properties` files located in each microservice folder.

## Additional Information

If you have any questions or need further assistance, please feel free to reach out. Contributions are welcome!
