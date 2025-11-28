# API Gemini IA - Microserviço de Inteligência Artificial

Microserviço de integração com Google Gemini IA para processamento inteligente de documentos e dados jurídicos do sistema SoftWave.

## Tecnologias Utilizadas

![Spring Boot](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Google AI](https://img.shields.io/badge/Google_AI-4285F4?style=for-the-badge&logo=google&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

### Dependências Principais

- **Spring Boot 3.4.7** - Framework principal
- **Spring Cloud OpenFeign** - Cliente HTTP declarativo
- **Spring Data JPA** - Persistência de dados
- **MySQL Connector J** - Driver do banco de dados
- **Spring Boot Validation** - Validação de dados
- **Swagger/OpenAPI 2.3.0** - Documentação da API

## Requisitos do Sistema

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

- **Java** >= 21
- **Maven** >= 3.8.0
- **MySQL** >= 8.0
- **Chave API Google Gemini**

## Instalação e Configuração

### 1. Clone o Repositório

```bash
git clone <repository-url>
cd API-GEMINI-IA
```

### 2. Configuração Google Gemini API

#### Obter Chave da API

1. Acesse [Google AI Studio](https://makersuite.google.com/app/apikey)
2. Faça login com sua conta Google
3. Clique em "Create API Key"
4. Copie a chave gerada

### 3. Configuração do Banco de Dados

O serviço Gemini utiliza o mesmo banco de dados principal do projeto:

```sql
-- O banco softwave_db já deve estar criado pelo backend principal
-- Caso não esteja, execute:
CREATE DATABASE IF NOT EXISTS softwave_db;
CREATE USER IF NOT EXISTS 'softwave'@'localhost' IDENTIFIED BY 'softwave123';
GRANT ALL PRIVILEGES ON softwave_db.* TO 'softwave'@'localhost';
FLUSH PRIVILEGES;
```

### 4. Configuração de Ambiente

Crie um arquivo `application-local.yml` em `src/main/resources/`:

```yaml
spring:
  application:
    name: gemini-service
  
  datasource:
    url: jdbc:mysql://localhost:3306/softwave_db
    username: softwave
    password: softwave123
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
        format_sql: true
    show-sql: true

# Servidor
server:
  port: 8092

# Google Gemini Configuration
gemini:
  api:
    key: ${GEMINI_API_KEY:your-gemini-api-key-here}
    base-url: ${GEMINI_BASE_URL:https://generativelanguage.googleapis.com}
    model: ${GEMINI_MODEL:gemini-pro}
    timeout: ${GEMINI_TIMEOUT:30000}
    max-tokens: ${GEMINI_MAX_TOKENS:2048}
    temperature: ${GEMINI_TEMPERATURE:0.7}

# CORS Configuration
cors:
  allowed-origins: ${CORS_ALLOWED_ORIGINS:http://localhost:5173,http://localhost:8080}
```

### 5. Variáveis de Ambiente

Configure as seguintes variáveis:

```bash
# Gemini API
export GEMINI_API_KEY=sua-chave-gemini-aqui
export GEMINI_MODEL=gemini-pro

# Database
export DB_USERNAME=softwave
export DB_PASSWORD=softwave123

# CORS
export CORS_ALLOWED_ORIGINS=http://localhost:5173,http://localhost:8080
```

### 6. Instalação das Dependências

```bash
mvn clean install
```

### 7. Executar a Aplicação

#### Modo Desenvolvimento

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

#### Build e Execução

```bash
mvn clean package
java -jar target/api-gemini-ia-0.0.1-SNAPSHOT.jar
```

A aplicação estará disponível em: http://localhost:8092

## Endpoints da API

### Análise de Processos

#### POST /analise-processo/{id}
Gera análise IA para uma movimentação processual

**Response:**
```json
{
  "id": 1,
  "resumoIA": "Análise gerada pela IA sobre a movimentação processual",
  "movimentacaoId": 123,
  "dataAnalise": "2025-01-18T12:34:56"
}
```

#### GET /analise-processo
Lista todas as análises realizadas

#### GET /analise-processo/{id}
Obtém análise específica por ID

#### GET /analise-processo/por-movimentacao/{movimentacaoId}
Obtém análise por ID da movimentação

**Response:**
```json
{
  "id": 1,
  "resumoIA": "Análise detalhada...",
  "movimentacao": {
    "id": 123,
    "data": "2025-01-18",
    "movimento": "Petição inicial protocolada",
    "processoId": 456
  }
}
```

## Documentação da API

A documentação completa está disponível via Swagger UI:

- **Desenvolvimento**: http://localhost:8092/swagger-ui.html
- **JSON**: http://localhost:8092/v3/api-docs

## Monitoramento

### Health Check

```bash
curl http://localhost:8092/actuator/health
```

## Troubleshooting

### Problemas Comuns

1. **API Key inválida**: Verifique se a chave do Gemini está correta
2. **Timeout**: Ajuste o timeout para documentos grandes
3. **Conexão MySQL**: Confirme se o banco está rodando e acessível

### Debugging

```yaml
logging:
  level:
    softwave: DEBUG
    feign: DEBUG
```

## Testes

### Executar Testes

```bash
# Todos os testes
mvn test

# Testes de integração
mvn test -Dtest=**/*IntegrationTest
```

## Contribuição

1. Faça fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/gemini-improvement`)
3. Commit suas mudanças (`git commit -m 'Melhora Gemini service'`)
4. Push para a branch (`git push origin feature/gemini-improvement`)
5. Abra um Pull Request

## Licença

Este projeto é propriedade da SoftWave SPTech e destina-se ao uso exclusivo do escritório Lauriano & Leão Sociedade de Advogados.

---

**Desenvolvido por:** SoftWave SPTech  
**Versão:** 0.0.1-SNAPSHOT  
**Data:** 2025
