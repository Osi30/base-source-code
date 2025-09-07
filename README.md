# [Base Source Code]

This is a monolithic Spring Boot backend REST API for a sales and product management platform. The API is designed to handle user authentication, product management, orders, and payments.

---

## Features

### 1. Authentication & Authorization

- **Login & Register:** Users can register and log in using their email, phone number, or username.
- **Email Verification:** Registration via email requires verification. A verification link is sent to the user's email, valid for one day.
- **Token-based Authentication:** The API uses JWT for secure authentication.
  - **Access Token:** Valid for 30 minutes.
  - **Refresh Token:** Valid for 60 minutes.
- **Role-Based Access Control (RBAC):**
  - There are three default roles: **ADMIN**, **EMPLOYEE**, and **USER**.
  - Roles can be created, updated, and deleted, but these actions are restricted to administrators.
  - Permissions are mapped to roles (many-to-one relationship), allowing for flexible control over access to different API resources.

### 2. Product Management

- Users can create, update, and delete their own products for sale.
- Users are prevented from purchasing their own products.

### 3. Order & Payment

- Users can place orders for products.
- The API supports three payment methods:
  - **CASH:** Requires manual verification of the transaction.
  - **MOMO** & **VNPAY:** Transactions are automatically verified through their respective payment gateways.

### 4. Performance Optimization

- **Redis Caching:** Redis is used to cache lists of frequently accessed data, including **Product**, **Order**, **Role**, and **Permission** lists, to improve query performance.

---

## Technology Stack

- **Backend:** Spring Boot
- **Database:** MySQL
- **Caching:** Redis
- **Security:** JWT (JSON Web Tokens)

---

## Getting Started

### Prerequisites

- Java 21 or higher
- MySQL database
- Redis server
- IntelliJ IDEA

### Installation

1. Clone the repository: `git clone https://github.com/Osi30/base-source-code.git`
2. Navigate to the project directory: `cd base-source-code`
3. Configure your database connection in `src/main/resources/application.yml`.
4. Configure your Redis connection details in the same file.
5. Build the project using Maven: `mvn clean install`

### Running the Application

Run the main class `Application.java` from your IDE, or use the Maven command:

`java -jar target/base-source-code.jar`

The application will start on `http://localhost:8080`.

---

## Test Accounts

You can use the following default account for testing administrator functionality:

- **Role:** Admin
- **Email:** `hoaloicuofficial@gmail.com`
- **Password:** `hoaloicu123`
