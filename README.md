# Take-Home Test - EBANX

Este projeto foi desenvolvido como parte de um teste técnico para o processo seletivo da EBANX. Ele expõe uma API RESTful para controle de transações e gerenciamento de saldo.

---

## 📦 Tecnologias e Dependências

- **Java 17**
- **Spring Boot 3.4.4**
- **Spring Web**
- **Spring Validation**
- **Lombok 1.18.38**
- **ModelMapper 3.1.0**
- **Springdoc OpenAPI (Swagger UI) 2.8.6**
- **JAXB**
- **JUnit 5 & Mockito** (via `spring-boot-starter-test`)

---

## 🚀 Como Executar o Projeto

Certifique-se de ter o Java 17 e o Maven instalados. Para rodar localmente:

```bash
git clone https://github.com/seu-usuario/take-home-ebanx.git
cd take-home-ebanx
./mvnw spring-boot:run
```

> A aplicação será iniciada na porta `8080`.

---

## 📑 Documentação da API (Swagger)

Após rodar a aplicação, acesse:

📍 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  
ou  
📍 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Também pode ser acessada a documentação como json para importe no Postman

📍 [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 📮 Endpoints Disponíveis

### `GET /balance`
Retorna o saldo atual.

### `POST /event`
Registra um evento (depósito, saque ou transferência).  
Os campos são validados e o tipo do evento determina a lógica de execução.

---

## 🧪 Testes Automatizados

### ✅ Testes Unitários e de Integração

Execute todos os testes com o seguinte comando:

```bash
./mvnw clean test
```

- Os testes de **serviço** estão em:  
  `src/test/java/com/gmail/lgsc92/service`

- Os testes de **integração** estão em:  
  `src/test/java/com/gmail/lgsc92/integration`

---

## 🧪 Testes com Postman

### Como testar a API via Postman:

1. Importe a collection:  
   [`ebanx.postman_collection.json`](./ebanx.postman_collection.json)

2. Configure a variável de ambiente `baseUrl` com o valor:  
   ```
   http://localhost:8080
   ```

3. A collection contém as requisições:
   - `GET /balance` com validações automáticas via `test`
   - `POST /event` com diferentes tipos de payload (depósito, saque, transferência)
   - Testes para cenários inválidos (ex: saldo insuficiente)

> Todas as requisições estão acompanhadas de scripts de **validação (`pm.expect`)** no Postman.

---

## 🎯 Padrões e Organização

- A estrutura segue boas práticas de separação de responsabilidades.
- Padrão Singleton: Implementado para garantir que certas classes possuam apenas uma única instância durante o ciclo de vida da aplicação. Isso é particularmente útil para gerenciar recursos compartilhados, como conexões de banco de dados ou configurações globais, assegurando um ponto de acesso único e controlado. ​
- Padrão Strategy: A lógica de negócios é estruturada seguindo o padrão Strategy, permitindo a expansão fácil para novos tipos de eventos.​
- Data Transfer Objects (DTOs) e ModelMapper: A utilização de DTOs em conjunto com a biblioteca ModelMapper facilita a conversão entre entidades e suas representações na API, promovendo uma separação clara entre as camadas de domínio e de apresentação.​
- Tratamento Centralizado de Exceções: As exceções são gerenciadas de forma centralizada utilizando a anotação @ControllerAdvice, permitindo um controle uniforme e consistente dos erros que podem ocorrer durante a execução da aplicação.​
- Nível 2 do Modelo de Maturidade de Richardson: A API está alinhada ao nível 2 do Modelo de Maturidade de Richardson, utilizando URIs distintas para recursos e empregando corretamente os métodos HTTP (GET, POST, PUT, DELETE) para as operações correspondentes. Isso assegura uma interação mais intuitiva e padronizada com a API.

---

## 👨‍💻 Autor

**Lukas Guibor dos Santos Costa**  
📧 lgsc92@gmail.com  
💼 [LinkedIn](https://www.linkedin.com/in/lukas-guibor)