# api-java

## Configurações

- Versão JDK: 17
- Versão Gradle: 8.5
- Spring Boot: 3.2.2
- Contexto: */api*
- Perfil *dev*
  - Porta padrão: 8081
- Build da imagem docker *myapps/api-java*
  - Linux Ubuntu: `$ sh build.sh`
  - Microsoft Windows: `> build.bat`
- Build do artefato *.jar*
  - Linux Ubuntu: `$ sh build_artifact.sh`
  - Microsoft Windows: `> build_artifact.bat`
- Executar localmente com o perfil *dev*
  - Linux Ubuntu: `$ sh run_artifact_dev.sh`
  - Microsoft Windows: `> run_artifact_dev.bat`

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
