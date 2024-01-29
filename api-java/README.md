# api-java

## Configurações

- Versão JDK: 17
- Versão Gradle: 8.5
- Spring Boot: 3.2.2
- Spring Security: 6
- Contexto: */api*
  - Endpoint aberto para teste: */open*
- Perfil *dev*
  - Porta padrão: 8081
- Build da imagem docker *myapps/api-java*
  - Linux Ubuntu: `$ sh build.sh`
- Build do artefato *.jar*
  - Linux Ubuntu: `$ sh build_artifact.sh`
- Executar localmente com o perfil *dev*
  - Linux Ubuntu: `$ sh run_artifact_dev.sh`

- Geração da chave de segurança
  - Gerado uma string aleatória de 32 caracteres: `63412610788732741019852643069194`
    - Ferramenta: [https://www.random.org/strings/](https://www.random.org/strings/)
  - Aplicado criptografia SHA-256 nessa string aleatória: `24788b370e16e2893453c3f871e127c3d8d41b0a0f56c2b222be4ff99232e50b`
    - Ferramenta: [https://emn178.github.io/online-tools/sha256.html](https://emn178.github.io/online-tools/sha256.html)
  - Codificado string criftografada em BASE64: `MjQ3ODhiMzcwZTE2ZTI4OTM0NTNjM2Y4NzFlMTI3YzNkOGQ0MWIwYTBmNTZjMmIyMjJiZTRmZjk5MjMyZTUwYg==`
    - Ferramenta: [https://www.base64encode.org/](https://www.base64encode.org/)

## Read Me First
The following was discovered as part of building this project:

* The original package name 'com.myapps.api-java' is invalid and this project uses 'com.myapps.apijava' instead.

## Getting Started

#### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.2/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.2/gradle-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.2/reference/htmlsingle/index.html#web)

#### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

#### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
