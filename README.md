# 💰 Finance Data Processing and Access Control Backend

---

## 📌 Overview

This project is a backend system designed to manage financial records and provide dashboard analytics with role-based access control.

It simulates a company’s internal finance system where users interact with financial data based on their roles. The focus is on clean architecture, proper data handling, and well-structured APIs.

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

---

### 📊 Dashboard

* `GET /dashboard/summary`
* `GET /dashboard/monthly-trends`

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

1. Clone the repository
2. Configure MySQL database:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/finance_data_db
spring.datasource.username=root
spring.datasource.password=your_password
```

3. Run the application:

```
mvn spring-boot:run
```

4. Test APIs using Postman

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
