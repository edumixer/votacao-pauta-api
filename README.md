# votacao-pauta-api

# Sobre o projeto

Esta API foi desenvolvida utilizando Java e o framework Spring Boot, com o objetivo de oferecer uma solução robusta e eficiente para o gerenciamento de sessões de votação relacionadas a pautas.

O sistema é projetado para atender cenários em que é necessário realizar consultas, abrir sessões de votação, registrar votos, e validar permissões de votantes com base em CPF.

### Collection do Postman
Baixar a collection do Postman: https://drive.google.com/file/d/1YadGoQKQjtjoO-hB_IHX7xLfq_1WDxPd/view?usp=sharing

### Documentação da API
http://localhost:8080/swagger-ui/index.html

## Cadastrar uma pauta
- Envie uma requisição POST para o endpoint: {host}/v1/pautas/cadastrar

## Abrir uma sessão de votação
- Para iniciar a votação, envie uma requisição POST para: {host}/v1/sessoes/abrir-sessao-votacao
- É obrigatório fornecer o id da pauta que foi cadastrada anteriormente.
- O parâmetro tempoParaFechamento é opcional. Caso não seja informado, o tempo padrão de 60 segundos será usado. 

## Realizar votos
- Com a sessão aberta, é possível registrar votos enviando uma requisição POST para: {host}/v1/votos/votar

### Regras e parâmetros para votar:

- Para votar, é necessário informar o pautaId.
No corpo da requisição, envie:
Um CPF válido (cpfVotante).
A resposta do voto (respostaDoVoto), que deve ser "Sim" ou "Não".
Cada CPF só pode votar uma vez. Certifique-se de usar um CPF diferente a cada voto.
Consultar resultado dos votos
- Após a votação, para verificar o resultado e a contagem de votos, faça uma requisição GET para:
{host}/v1/pautas/consultar/{id}

Informe o id da pauta na URL.
Caso a consulta seja feita após o término da sessão, será retornado o resultado final, indicando se a pauta foi aprovada ou rejeitada.

### Funcionalidade não implementada

- Verificar se um associado pode votar
Use o endpoint GET: {host}/v1/users/{cpf}

- Este endpoint verifica, a partir do CPF fornecido, se o associado tem permissão para votar.

- A validação é feita por um serviço externo chamado user-info-api.

## Tecnologias utilizadas
- Java 18
- Spring (boot, web, data, validation)
- Spring Cloud Circuit Breaker (Resilience4J)
- JPA / Hibernate
- Lombok
- Maven
- Postgres
- JUnit/Mockito
- Swagger OpenAPI 3.0
- ModelMapper
- Docker

## Como executar a API votacao-pauta-api

## Opção 1 - Executando pelo Maven

### Pré-requisitos
- Java 18
- Banco de dados Postgres configurado no ambiente local com o Database votacao_pauta criado

```bash
# clonar repositório
git clone https://github.com/edumixer/votacao-pauta-api.git

# entrar na pasta do projeto votacao-pauta-api
cd votacao-pauta-api

# executar o projeto
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Opção 2 - Executando pelo docker-compose

### Pré-requisitos
- Docker em execução no ambiente local

```bash
# clonar repositório
git clone https://github.com/edumixer/votacao-pauta-api.git

# entrar na pasta do projeto votacao-pauta-api
cd votacao-pauta-api

# executar o projeto
docker-compose up
```