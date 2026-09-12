# eBook - Online Book Selling Platform

A web-based book selling application built with **Spring Boot**.

## 📖 About the Project

eBook is an e-commerce platform that allows users to browse, search, and purchase books online. The application is built using the Spring Boot framework, providing a robust and scalable backend architecture.

## 🛠️ Tech Stack

- **Backend:** Java, Spring Boot
- **Frontend:** React
- **Database:** PostgreSQL
- **Build Tool:** Maven / Gradle
- **Other:** *(add any additional frameworks, e.g. Spring Data JPA, Spring Security)*

## ✨ Features

- Browse and search books
- User registration and login
- Add to cart / purchase books
- *(add other features specific to your project)*

## 🚀 Getting Started

### Prerequisites

- Java 17+ (or your project's JDK version)
- Maven or Gradle
- Node.js and npm
- PostgreSQL installed and running

### Backend Setup

1. Clone the repository
   ```bash
   git clone <repository-url>
   cd eBook
   ```

2. Create a PostgreSQL database and configure the connection in `application.properties` / `application.yml`
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/ebook_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. Build the project
   ```bash
   mvn clean install
   ```

4. Run the backend
   ```bash
   mvn spring-boot:run
   ```

   The backend will start at:
   ```
   http://localhost:8080
   ```

### Frontend Setup

1. Navigate to the frontend directory
   ```bash
   cd frontend
   ```

2. Install dependencies
   ```bash
   npm install
   ```

3. Run the React app
   ```bash
   npm start
   ```

   The frontend will start at:
   ```
   http://localhost:3000
   ```

## 👤 Author

*(İrem Şimşek / Beyza Çiçekay / Orhancan Yıldırım)*
