## Sincap Web Service

Projeto [Spring Boot](projects.spring.io/spring-boot/) para a
Api RESTFul que serve o [sincap-mobile](http://ledszeppellin.sr.ifes.edu.br:81/sincap/sincap-mobile).

### Índice

1. [Configuração](#configura-o)
    1. [Intellij IDEA](#intellij-idea)

### Configuração

O projeto pode ser executado com o comando

```bash
mvn spring-boot:run
```

Este projeto conta com *Profiles*. Um *profile* é uma configuração que pode ser executada em determinado contexto.
O *profile* padrão é o *test*. Os *profiles* disponíveis são *test* e *production*. A principal diferença entre eles
é o *test* roda o banco de dados *in memory* [H2](www.h2database.com), o que garante agilidade no *deploy* e testes 
mais isolados.

A documentação sobre *profiles* está disponível 
[aqui](http://docs.spring.io/spring-boot/docs/1.2.1.RELEASE/reference/htmlsingle/#boot-features-external-config-profile-specific-properties)
e
[aqui](http://docs.spring.io/spring-boot/docs/1.2.1.RELEASE/reference/htmlsingle/#boot-features-profiles).

#### Intellij IDEA

**Passo 1**

![Passo 1](http://ledszeppellin.sr.ifes.edu.br:81/sincap/sincap-doc/raw/master/2015-02-10%2014:05:56.png "Passo 1")

**Passo 2**

![Passo 2](http://ledszeppellin.sr.ifes.edu.br:81/sincap/sincap-doc/raw/master/2015-02-10%2014:11:07.png "Passo 2")

**Passo 3**

![Passo 3](http://ledszeppellin.sr.ifes.edu.br:81/sincap/sincap-doc/raw/master/2015-02-10%2014:21:40.png "Passo 3")
