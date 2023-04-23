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
