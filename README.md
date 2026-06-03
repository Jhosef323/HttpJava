# 🌐 HttpJava

> Servidor HTTP funcional implementado do zero em Java puro — sem frameworks, sem bibliotecas externas.

---

📖 Sobre o projeto

**HttpJava** é um servidor HTTP construído sobre `ServerSocket` da API padrão do Java. O objetivo foi entender como o protocolo HTTP funciona na prática: parsing de requisições, roteamento, montagem de respostas e concorrência — tudo implementado manualmente.

Nenhuma dependência externa. Apenas Java.

---

## ✨ Funcionalidades

- ✅ Parsing completo de requisições HTTP (method, path, query string, headers, body)
- ✅ Suporte a `GET` e `POST`
- ✅ Roteador com interface funcional (`@FunctionalInterface`) e lambdas
- ✅ Respostas HTTP com status code, headers e body em UTF-8
- ✅ Múltiplas conexões simultâneas via `ExecutorService` (thread pool de 10 workers)
- ✅ Suporte a JSON como content-type de resposta

---

## 🏗️ Arquitetura

```
src/
└── com/HttpJava/
    ├── Main.java             # Ponto de entrada — define as rotas
    ├── Server.java           # Aceita conexões TCP com ServerSocket
    ├── ConexaoHandler.java   # Runnable — processa cada conexão em thread separada
    ├── Router.java           # Mapeia método+caminho para handlers
    ├── HttpRequest.java      # Parse da requisição HTTP
    └── HttpResponse.java     # Montagem e envio da resposta HTTP
```

### Fluxo de uma requisição

```
Cliente (curl / browser)
        │
        ▼
   Server.java          ← aceita conexão TCP (ServerSocket)
        │
        ▼
ConexaoHandler.java     ← thread do pool processa a conexão
        │
        ├─► HttpRequest.java   ← faz parse da requisição bruta
        ├─► Router.java        ← resolve qual handler executar
        └─► HttpResponse.java  ← monta e envia a resposta HTTP
```

---

## 🚀 Como executar

### Pré-requisitos
- Java 17+
- Maven

### Rodando o servidor

```bash
# Clone o repositório
git clone https://github.com/jhosef323/HttpJava.git
cd HttpJava

# Compile e execute com Maven
mvn compile exec:java -Dexec.mainClass="com.HttpJava.Main"
```

O servidor vai iniciar na porta `8080`:
```
Servidor ouvindo na porta 8080
```

---

## 🧪 Testando as rotas

### GET /ping
```bash
curl http://localhost:8080/ping
```
```json
{"status": "ok"}
```

---

### GET /eco — com query string
```bash
curl "http://localhost:8080/eco?nome=jhosef"
```
```json
{"caminho": "/eco", "query": "nome=jhosef"}
```

---

### POST /dados — com body
```bash
curl -X POST http://localhost:8080/dados \
  -H "Content-Type: application/json" \
  -d '{"mensagem": "hello"}'
```
```json
{"recebido": true}
```

---

### Rota não encontrada
```bash
curl http://localhost:8080/qualquer-coisa
```
```json
{"erro": "rota não encontrada"}   ← HTTP 404
```

---

## 🔧 Adicionando novas rotas

Abra `Main.java` e registre handlers direto no router:

```java
Router router = new Router();

// GET simples
router.get("/hello", (request, response) -> {
    response.setJson("{\"mensagem\": \"Olá, mundo!\"}");
});

// POST com leitura do body
router.post("/echo", (request, response) -> {
    String body = request.getBody();
    response.setJson("{\"voce enviou\": \"" + body + "\"}");
});

// Lendo query string
router.get("/busca", (request, response) -> {
    String query = request.getQueryString(); // ex: "termo=java"
    response.setJson("{\"query\": \"" + query + "\"}");
});

new Server(8080, router).iniciar();
```

---

## 🛠️ Decisões técnicas

| Decisão | Motivo |
|---|---|
| `ServerSocket` puro | Entender HTTP na camada TCP, sem abstração |
| `ExecutorService` com thread pool | Suporte a múltiplos clientes sem criar thread por conexão |
| `@FunctionalInterface` no Router | Handlers como lambdas — sintaxe limpa e extensível |
| `LinkedHashMap` para headers | Preserva a ordem de inserção dos headers na resposta |
| `content-length` para ler o body | Forma correta de delimitar o body em HTTP/1.1 |
| try-with-resources no socket | Garante fechamento da conexão mesmo com exceções |

---

## 👤 Autor

**Jhosef Arnold**
- GitHub: [@jhosef323](https://github.com/jhosef323)
- LinkedIn: [jhosef-arnold](https://www.linkedin.com/in/jhosef-arnold)

---

> Projeto desenvolvido para aprofundar o entendimento do protocolo HTTP e concorrência em Java.
