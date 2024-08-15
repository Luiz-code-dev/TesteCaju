# Auth Transaction Service

## Descrição

O projeto **Auth Transaction Service** é um serviço de autenticação e processamento de transações financeiras. Ele
fornece uma interface para a validação de transações com base em diversos critérios, como saldo disponível e códigos de
erro específicos. Este projeto foi desenvolvido utilizando Spring Boot e segue os princípios de desenvolvimento de
software limpo e modular.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.3.2**
- **Spring Data JPA**
- **H2 Database**
- **JUnit 5**
- **Mockito**
- **Maven**

## Estrutura do Projeto

O projeto está organizado da seguinte forma:

- **`src/main/java`**: Contém o código fonte da aplicação.
    - **`com.codevelop.auth.transaction`**: Pacote principal da aplicação.
    - **`service`**: Contém as classes de serviço que implementam a lógica de negócios.
    - **`controller`**: Contém os controladores REST que expõem as APIs da aplicação.
    - **`repository`**: Contém as interfaces de repositório para acesso aos dados.
    - **`model`**: Contém as classes de modelo de domínio.

    - **`src/test/java`**: Contém os testes unitários.

## Configuração e Execução

### Pré-requisitos

Certifique-se de ter instalado:

- **Java 17** ou superior
- **Maven** (para gerenciamento de dependências e construção do projeto)

### Configuração do Banco de Dados

Este projeto utiliza o banco de dados em memória **H2** para fins de desenvolvimento e testes. Não é necessária
configuração adicional, pois as configurações padrão do Spring Boot já incluem a integração com o H2.

### Executando a Aplicação

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/seu-usuario/auth-transaction-service.git
   cd auth-transaction-service

## Construir o projeto:

mvn clean install

## Executar a aplicação:

Para executar a aplicação, utilize o comando:

mvn spring-boot:run

A aplicação estará disponível em http://localhost:8080.

## Executando os Testes

Para executar os testes unitários, utilize o comando:

mvn test

Os testes são escritos com JUnit 5 e utilizam Mockito para simulação de dependências.

## Endpoints da API

**POST  /transactions**

Processa uma transação com base nos dados fornecidos.

**Parâmetros de Requisição:**

- `accountId`: ID da conta.
- `amount`: Quantidade a ser debitada.
- `mcc`: Código de categoria do comerciante (Merchant Category Code).
- `merchant`: O nome do estabelecimento.

**Exemplo de Requisição:**

\``json
{
    "accountId": "123",
    "amount": 100.00,
    "mcc": "5811",
    "merchant": "PADARIA DO ZE               SAO PAULO BR"
}


**Resposta de Sucesso:**

\``json
{
  "status": "success",
  "code": "00",
  "transactionId": "Transação concluida"
}

**Resposta de Erro:**

\``json
{
  "status": "success",
  "code": "51",
  "message": "Insufficient funds"
}

\``json
{
  "status": "success",
  "code": "07",
  "message": "Erro transação"
}
