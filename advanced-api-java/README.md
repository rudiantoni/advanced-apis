# advanced-api-java

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
- Spring Security
- Testing
  - JUnit5
  - H2 Database (for testing)
  - Mockito
  - JaCoCo

## Diagrama do esquema de banco de dados
- [Clique aqui](https://app.diagrams.net/#G13bd2ILwPsaACDqCaTtsRLozOtMCCcUoe)

## Segurança
- Spring Security login padrão: user
  - Autenticação pelo header da requisição: Basic Auth (user + password)
  - Endpoint Web de login padrão: `/api/login`

**Propriedades padrão para definir usuário e senha do Spring Security**

- **spring.security.user.name=user**
- **spring.security.user.password=pass**
- **spring.security.user.roles=manager**

- Ferramenta para auxiliar na geração de chaves para uso em segurança: [https://www.allkeysgenerator.com](https://www.allkeysgenerator.com)

### Uso de claims

#### Criando um token

**Claim personalizado**

```java
HashMap<String, Object> extraClaims = new HashMap<>();
extraClaims.put("customClaimKey", "customClaimValue");
Jwts.builder().setClaims(extraClaims).build();
```

**Claim subject como jsonString**

```java
// Retorna o username e email como objeto
TokenSubject subject = encodeTokenSubject(token)

// Transforma o objeto em JSON string
String tokenSubject = Util.toJsonStr(subject)

Jwts.builder().setSubject(tokenSubject).build();
```

#### Lendo um token

```java
Claims claims = Jwts.parserBuilder()
  // Obtém a chave de assinatura do token como objeto Key
  .setSigningKey(getSigningKey())
  .build()
  // Converte a string JWT em um objeto
  .parseClaimsJws(jwtToken)
  // Retorna o corpo do token
  .getBody();
```

**Claim personalizado**

```java
// Obtém o claim pelo nome
// Também e possível:
// - Converter diretamente pela função
// - Obter com um valor padrão, etc
String username = (String) claims.get("username");
String email = (String) claims.get("email");
```

**Claim subject como jsonString**

```java
// Obtém o claim subject previamente gravado como JSON string
String subject = claims.getSubject();

// Converte (função interna) a JSON string como objeto
TokenSubject tokenSubject = Util.fromJsonStr(subject, TokenSubject.class);
```

### Obter o authentication token no @ResponseBody

```java
AuthenticationToken authenticationToken
```

## Outros códigos

```java
// Manual comparator entre 2 objetos da mesma classe null safe and case sensitive
Comparator<User> userComparator = Comparator
  .comparing(User::getId, Comparator.nullsFirst(Long::compare))
  .thenComparing((User user) -> user.getEmail(), Comparator.nullsFirst(String::compareTo))
  .thenComparing(User::getUsername, Comparator.nullsFirst(String::compareTo))
  .thenComparing((User user) -> user.getPassword(), Comparator.nullsFirst(String::compareTo));
  

// Manual comparator
assertThat(capturedUser).usingComparator(userComparator).isEqualTo(newUser);
```

## Teste

Para executar os testes, execute a rotina Tasks > verification > jacocoTestReport ou use o comando.

```
./gradlew jacocoTestReport
```

O relatório de testes é gerado em /build/reports/jacoco/test/html/index.html

### Ignorar packages/classes
- Usando o code coverage do IntelliJ:
  - Edit Run Configuration > Modify Options > Exclude classes and packages
- Definindo o JaCoCo como padrão
  - Edit Run Configuration > Modify Options > Choose coverage runner

## Read Me First
The following was discovered as part of building this project:

* The original package name 'com.myapps.advanced-api-java' is invalid and this project uses 'com.myapps.advancedapijava' instead.

### Getting Started

#### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.10/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.10/gradle-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.10/reference/htmlsingle/#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.7.10/reference/htmlsingle/#data.sql.jpa-and-spring-data)

#### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

#### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)