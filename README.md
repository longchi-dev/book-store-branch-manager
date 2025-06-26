# 🏪 Multi-Branch Inventory Management System

## 📌 Introduction
This project is a **multi-branch inventory management system** built with **Java (Spring Boot)**. It supports:
- 🔐 User authentication and authorization for admins and branches  
- 📦 Inventory tracking across branches  
- 📊 Sales and stock analytics through reporting dashboards  
- 🌐 RESTful API support for front-end integration and potential third-party use

---
## 🚀 Features
### 🔐 User Authentication and Authorization
- Secure login for Admin and Branch roles  
- Admin can create accounts for branch users  
- Branch users must change their password upon first login  
### 📦 Inventory Management
- Add/update product information  
- Track stock levels by branch  
- Support inter-branch stock transfers  
### 📊 Reporting and Analytics
- Dashboard for sales trends  
- Insights into inventory levels and customer behavior by branch  
### 🧩 REST API Development
- RESTful APIs for front-end communication and future integration

---
## 🧰 Technologies Used
- **Backend**: Java 17, Spring Boot, Spring Data JPA  
- **Database**: MySQL (via phpMyAdmin in XAMPP)  
- **Frontend**: HTML, CSS, JavaScript (run using VSCode)  
- **IDE**: IntelliJ IDEA

---
## ✅ System Requirements
- Java JDK 17+  
- IntelliJ IDEA  
- XAMPP (for MySQL + phpMyAdmin)  
- VSCode (for frontend)  

---
## ⚙️ Setup Instructions
### 1. 🧪 Clone the Project
```bash
git clone <your-repo-url>
```

### 2. 🛠 Create Database
- Open XAMPP and start MySQL
- Go to phpMyAdmin
- Create a new database named: `sql_java`

### 3. 🔧 Configure Application
- Open the project in IntelliJ IDEA
- Open src/main/resources/application.properties
- Update MySQL credentials:
```bash
spring.datasource.username=root
spring.datasource.password=
(or use your own MySQL credentials)
```

### 4. ▶️ Run the Project
- Open FinaltermApplication.java
- Run the project the first time → tables will be auto-generated (you will see warnings)
- Run the project the second time → the server will start normally

### 5. 🌐 Launch Frontend
- Open dangnhap.html in VSCode or a web browser
- Use the provided login account:
```bash
Email: this.is.manager.fahasa@gmail.com  
Password: 123
```
