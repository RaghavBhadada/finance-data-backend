# 💰 Finance Data Processing and Access Control Backend

---

## 📌 Overview

This project is a backend system designed to manage financial records and provide dashboard analytics with role-based access control.

It simulates a company’s internal finance system where users interact with financial data based on their roles. The focus is on clean architecture, proper data handling, and well-structured APIs.

---

## 🎥 Project Demo Video

A walkthrough demonstrating the system design, authentication flow, role-based access control, and dashboard APIs.

🔗 Video Link: https://youtu.be/twAJXF28Svg

---

## 🎯 Design Philosophy

This project focuses on clean backend design, logical data flow, and role-based access control rather than production-level complexity.

The objective was to demonstrate strong backend fundamentals, including:
- Clear API design and structure
- Proper separation of concerns (Controller → Service → Repository)
- Effective use of aggregation logic for dashboard analytics
- Maintainable and readable code

Unnecessary complexity was intentionally avoided to keep the system simple, scalable, and aligned with real-world backend practices.

---

## 🏗️ Architecture

```
[Client]
   ↓
[Controller Layer]
   ↓
[Service Layer]
   ↓
[Repository Layer]
   ↓
[Database]
```

---

## 🔄 Request Flow (Example: Create Record)

1. User sends request with JWT token
2. JWT filter validates the token
3. Controller receives the request
4. Service processes business logic
5. Repository interacts with database
6. Record is saved
7. Response returned to client

---

## 🚀 Tech Stack

* Java
* Spring Boot
* Spring Security (JWT Authentication)
* Spring Data JPA
* MySQL
* Lombok
* Swagger

---

## 🧠 Key Features

### 🔐 Authentication & Security

* JWT-based authentication
* Role-based authorization using `@PreAuthorize`
* Email verification and OTP-based phone verification
* Sensitive data (like passwords) excluded from API responses

---

### 👥 User & Role Management

* Create and manage users
* Assign roles
* Activate/Deactivate users
* Restrict actions based on roles

---

### 💰 Financial Records Management

* Create, update, delete financial records
* Track income and expenses
* Associate records with users
* Maintain audit fields (`createdAt`, `createdBy`)
* Supports dynamic filtering using category, type, and date range 

---

### 📊 Dashboard & Analytics

* Total Income
* Total Expense
* Net Balance
* Category-wise totals
* Recent activity
* Monthly trends (income, expense, net)

---

## 🔑 Role Mapping

| System Role | Assignment Role |
| ----------- | --------------- |
| OWNER       | ADMIN           |
| EMPLOYEE    | ANALYST         |
| VIEWER      | VIEWER          |

---

## 🔐 Access Control

| Role     | Permissions                          |
| -------- | ------------------------------------ |
| OWNER    | Full access (CRUD + user management) |
| EMPLOYEE | Create & view financial records      |
| VIEWER   | View-only access                     |

---

## 📡 API Endpoints

### 🔐 Authentication

* `POST /auth/register-owner`
* `POST /auth/login`
* `GET /auth/verify-email`
* `POST /auth/generate-otp`
* `POST /auth/verify-otp`

---

### 👥 User Management

* `POST /business/add-employee` (ADMIN only)
* `POST /business/add-viewer` (ADMIN, ANALYST)

---

### 💰 Financial Records

* `POST /records` (ADMIN, ANALYST)
* `GET /records` (ALL ROLES)
* `PUT /records/{id}` (ADMIN only)
* `DELETE /records/{id}` (ADMIN only)
* `GET /records/filter` (ALL ROLES)

---

### 📊 Dashboard

* `GET /dashboard/summary`
* `GET /dashboard/monthly-trends`

---

## 📄 API Documentation (Swagger)

Swagger UI is available locally for exploring and testing APIs:

http://localhost:1111/swagger-ui/index.html

---

## 📊 Sample Dashboard Response

```json
{
  "totalIncome": 61000,
  "totalExpense": 0,
  "netBalance": 61000,
  "categoryTotals": {
    "Sales": 61000
  },
  "recentActivity": [...]
}
```

---

## 📈 Monthly Trends Response

```json
[
  {
    "month": 4,
    "income": 61000,
    "expense": 0,
    "net": 61000
  }
]
```

---

## ⚙️ Setup Instructions

Follow the steps below to run the project locally:

---

### 1. Clone the Repository

```bash
git clone https://github.com/RaghavBhadada/finance-data-backend.git
cd finance-data-backend
```

---

### 2. Java Requirement

This project requires **JDK 25**.

---

### 3. Setup MySQL Database

Create a database:

```sql
CREATE DATABASE finance_data_processing;
```

---

### 4. Configure Application Properties

Open:

```text
src/main/resources/application.properties
```

Update:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/finance_data_processing
spring.datasource.username=your_username
spring.datasource.password=your_password
```

> Note: Database credentials are not included for security reasons. Please configure your own local database.

---

### 5. Run the Application

```bash
mvn clean install
mvn spring-boot:run
```

---

### 6. Access the Application

```text
http://localhost:1111
```

---

### 7. Testing the APIs

Use Postman or any API client.

Recommended flow:

1. Register Owner
2. Login → Get JWT Token

Use token in header:

```text
Authorization: Bearer <your_token>
```

3. Test secured APIs

---

## 🧩 Assumptions

* Financial records are manually entered by users
* OWNER represents ADMIN role
* EMPLOYEE represents ANALYST role
* JWT is used for authentication
* MySQL is used for persistence
* Focus is on backend logic, not frontend

---

## ⚠️ Error Handling

* Input validation using `@Valid`
* Custom exception handling for business rules
* Proper HTTP status codes used

---

## 🔐 Security

* JWT authentication for all secured APIs
* Role-based authorization
* Sensitive fields excluded from responses

---

## 🔮 Future Enhancements

* Date range filtering for records
* Pagination support
* Weekly trends and advanced analytics
* Frontend dashboard integration

---

## 🏁 Conclusion

This project demonstrates backend engineering skills including API design, authentication, role-based access control, data modeling, and aggregation logic. It is structured to be clean, maintainable, and aligned with real-world backend systems.

---
