# API de Análise de Processos com IA - Gemini

API REST desenvolvida em Spring Boot para análise inteligente de movimentações processuais utilizando IA Gemini.

## 📋 Sobre o Projeto

Esta API permite a análise automatizada de movimentações de processos jurídicos através da integração com o Gemini IA, fornecendo resumos e insights sobre cada movimentação processual.

## 🚀 Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Web
- Bean Validation
- Swagger/OpenAPI 3.0
- Gemini IA (Google)

## 📍 Endpoints

### Base URL
```
http://localhost:8082/analise-processo
```

### 1. Gerar Análise de Movimentação

Gera uma análise com IA para uma movimentação específica.

**POST** `/analise-processo/{id}`

**Parâmetros:**
- `id` (path) - ID da movimentação a ser analisada

**Respostas:**
- `201` - Análise gerada com sucesso
- `404` - Movimentação não encontrada
- `500` - Erro interno ao gerar a análise

**Exemplo:**
```bash
POST /analise-processo/123
```

---

### 2. Listar Todas as Análises

Retorna todas as análises de movimentações geradas.

**GET** `/analise-processo`

**Respostas:**
- `200` - Lista de análises retornada com sucesso
- `204` - Não há análises geradas

**Exemplo de Resposta:**
```json
[
  {
    "id": 1,
    "resumoIA": "Análise da movimentação...",
    "movimentacaoId": 123,
    "dataAnalise": "2025-10-12T10:30:00"
  }
]
```

---

### 3. Buscar Análise por ID

Retorna uma análise específica pelo seu ID.

**GET** `/analise-processo/{id}`

**Parâmetros:**
- `id` (path) - ID da análise

**Respostas:**
- `200` - Análise encontrada com sucesso
- `404` - Análise não encontrada

---

### 4. Buscar Análise por ID da Movimentação

Retorna a análise associada a uma movimentação específica, incluindo detalhes da movimentação.

**GET** `/analise-processo/por-movimentacao/{movimentacaoId}`

**Parâmetros:**
- `movimentacaoId` (path) - ID da movimentação

**Respostas:**
- `200` - Análise encontrada com sucesso
- `404` - Análise não encontrada para o ID da movimentação fornecido

**Exemplo de Resposta:**
```json
{
  "id": 1,
  "resumoIA": "Análise detalhada da movimentação processual...",
  "movimentacao": {
    "id": 123,
    "data": "2025-10-10",
    "movimento": "Petição inicial protocolada",
    "processoId": 456
  }
}
```

## 🏗️ Estrutura do Projeto

```
softwave.api_gemini_ia/
├── controller/
│   └── AnaliseProcessoController.java
├── dto/
│   ├── AnaliseProcessoDTO.java
│   ├── AnaliseIAMovimentacaoDTO.java
│   └── UltimasMovimentacoesDTO.java
├── entity/
│   ├── AnaliseProcesso.java
│   └── UltimasMovimentacoes.java
└── services/
    ├── AnaliseProcessoService.java
    └── GeminiService.java
```

## 📦 Modelos de Dados

### AnaliseProcesso
Entidade principal que armazena as análises geradas pela IA.

### UltimasMovimentacoes
Entidade que representa as movimentações processuais.

### DTOs
- **AnaliseProcessoDTO**: Transferência de dados da análise
- **AnaliseIAMovimentacaoDTO**: Análise com dados completos da movimentação
- **UltimasMovimentacoesDTO**: Dados da movimentação processual

## 🔧 Configuração e Instalação

1. Clone o repositório
2. Configure as credenciais da API Gemini
3. Configure o banco de dados
4. Execute o projeto:

```bash
mvn spring-boot:run
```

## 📚 Documentação API

Acesse a documentação Swagger em:
```
http://localhost:8082/swagger-ui.html
```

## 👥 Autores

Desenvolvido por Softwave

---

**Nota**: Certifique-se de configurar corretamente as variáveis de ambiente e credenciais antes de executar a aplicação.
