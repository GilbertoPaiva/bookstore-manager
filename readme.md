# 📚 Bookstore Manager API

Sistema de Gerenciamento de Livros desenvolvido com Spring Boot 3 e PostgreSQL.

## 🚀 Tecnologias

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- PostgreSQL
- Lombok
- Bean Validation

## 📋 Pré-requisitos

- JDK 17+
- PostgreSQL 12+
- Maven 3.8+

## ⚙️ Configuração

1. Crie o banco de dados PostgreSQL:
```sql
CREATE DATABASE bookstore_db;
```

2. Configure as credenciais em `application.properties`

3. Execute o projeto:
```bash
mvn spring-boot:run
```

## 🔗 Endpoints

### Criar Livro
```http
POST /api/livros
Content-Type: application/json

{
  "titulo": "Clean Code",
  "autor": "Robert C. Martin",
  "isbn": "978-0132350884",
  "anoPublicacao": 2008
}
```

### Listar Todos os Livros
```http
GET /api/livros
```

### Buscar Livro por ID
```http
GET /api/livros/{id}
```

### Atualizar Livro
```http
PUT /api/livros/{id}
Content-Type: application/json

{
  "titulo": "Clean Code - Updated",
  "autor": "Robert C. Martin",
  "isbn": "978-0132350884",
  "anoPublicacao": 2008
}
```

### Deletar Livro
```http
DELETE /api/livros/{id}
```

## 📝 Validações

- **titulo**: obrigatório, 1-255 caracteres
- **autor**: obrigatório, 1-255 caracteres
- **isbn**: obrigatório, único, formato ISBN válido
- **anoPublicacao**: obrigatório, entre 1000 e ano atual

## 🎯 Códigos HTTP

- `200 OK`: Sucesso
- `201 Created`: Recurso criado
- `204 No Content`: Recurso deletado
- `400 Bad Request`: Erro de validação
- `404 Not Found`: Recurso não encontrado
- `500 Internal Server Error`: Erro no servidor