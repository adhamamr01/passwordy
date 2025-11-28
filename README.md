# Passwordy - Secure Password Manager

A modern password management application with multi-user support and AES-256 encryption.

## 🏗️ Project Structure

- **backend/** - Java Spring Boot REST API
- **frontend/** - Kotlin frontend application (coming soon)

## 🚀 Quick Start

### Backend (Spring Boot)
```bash
cd backend
./mvnw spring-boot:run
```

The API will be available at: `http://localhost:8080`

### Frontend (Coming Soon)

The frontend is currently under development.

## 🔒 Security Features

- ✅ Multi-user authentication with JWT
- ✅ BCrypt password hashing for master passwords
- ✅ AES-256-GCM encryption for stored passwords
- ✅ User isolation (users can only access their own passwords)
- ✅ Secure password generation with SecureRandom

## 📚 API Documentation

API is available at: `http://localhost:8080/api`

### Authentication Endpoints
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login and get JWT token

### Password Endpoints (Requires Authentication)
- `POST /api/password/generate` - Generate random password
- `POST /api/passwords` - Save password
- `GET /api/passwords` - Get all passwords
- `GET /api/passwords/{id}` - Get password by ID
- `POST /api/passwords/{id}/decrypt` - Decrypt password
- `PUT /api/passwords/{id}` - Update password
- `DELETE /api/passwords/{id}` - Delete password

## 🛠️ Tech Stack

### Backend
- Java 17+
- Spring Boot 3.x
- Spring Security with JWT
- JPA/Hibernate
- PostgreSQL / H2 Database
- Maven

### Frontend (Planned)
- Kotlin
- (To be determined)

