# advanced-apis

## Configurações

- Contexto: */api* (já incluso nos endpoints/links)
- Perfis: *default* (padrão)
- Versão JDK: 17
- Versão Gradle: 7.6.1
- Porta padrão: 8080
- Spring Boot: 2.7.10
- Flyway 9.6.10
  - Diretório de migrations: *src/main/resources/db/migration*
- SpringDoc OpenAPI UI 1.6.15 - OpenAPI 3.0.1 - Swagger UI
  - Endpoint da página de testes do Swagger: */api/swagger-ui.index.html*
  - Endpoint dos dados da api-docs: */api/v3/api-docs*
<!--
  Spring Boot Developer Tools: 3.0.4
-->

## Diagrama de relacionamento de entidades
- [Clique aqui](https://app.diagrams.net/#G13bd2ILwPsaACDqCaTtsRLozOtMCCcUoe)

## Dados padrão
- Spring Security login padrão: user
  - Autenticação pelo header da requisição: Basic Auth (user + password)
  - Endpoint de login padrão: `/api/login`

