# Estrutura do Projeto

```
spring-boot-swagger-jwt/
 │
 ├── src/main/java/
 │   └── com/gmontinny/jwt
 │       ├── configuration
 │       │   └── SwaggerConfig.java
 │       │
 │       ├── controller
 │       │   └── UserController.java
 │       │
 │       ├── dto
 │       │   ├── UserDataDTO.java
 │       │   └── UserResponseDTO.java
 │       │
 │       ├── exception
 │       │   ├── CustomException.java
 │       │   └── GlobalExceptionController.java
 │       │
 │       ├── model
 │       │   ├── AppUserRole.java
 │       │   └── AppUser.java
 │       │
 │       ├── repository
 │       │   └── UserRepository.java
 │       │
 │       ├── security
 │       │   ├── JwtTokenFilter.java
 │       │   ├── JwtTokenFilterConfigurer.java
 │       │   ├── JwtTokenProvider.java
 │       │   ├── MyUserDetails.java
 │       │   └── WebSecurityConfig.java
 │       │
 │       ├── service
 │       │   └── UserService.java
 │       │
 │       └── JwtAuthServiceApp.java
 │
 ├── src/main/resources/
 │   └── application.yml

```

**JwtTokenFilterConfigurer**

Adiciona o `JwtTokenFilter` ao `DefaultSecurityFilterChain` do Spring Boot Security.

```java
JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider);
http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
```

**JwtTokenProvider**

O `JwtTokenProvider` tem as seguintes responsabilidades:

1. Verifique a assinatura do token de acesso
2. Extraia declarações de identidade e autorização do token de acesso e use-as para criar UserContext
3. Se o token de acesso estiver malformado, expirado ou simplesmente se o token não for assinado com a chave de assinatura apropriada, a exceção de autenticação será lançada

**MyUserDetails**

Implementa `UserDetailsService` para definir nossa própria função *loadUserbyUsername* personalizada. A interface `UserDetailsService` é usada para recuperar dados relacionados ao usuário. Ele tem um método chamado *loadUserByUsername* que encontra uma entidade de usuário com base no nome de usuário e pode ser substituído para personalizar o processo de localização do usuário.

Ele é usado pelo `DaoAuthenticationProvider` para carregar detalhes sobre o usuário durante a autenticação.

**WebSecurityConfig**

A classe `WebSecurityConfig` fornecer configuração de segurança personalizada.

Os seguintes beans são configurados e instanciados nesta classe:

1. `JwtTokenFilter`
3. `PasswordEncoder`

Além disso, dentro do método `WebSecurityConfig#filterChain(HttpSecurity http)` vamos configurar padrões para definir endpoints de API protegidos/desprotegidos. Observe que desabilitamos a proteção CSRF porque não estamos usando cookies.

# Como usar este código?

1. Verifique se você tem [Java 11](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html) e [Maven](https://maven.apache.org) instalados

2. Fork este repositório e clone-o
  
```
$ git clone https://github.com/<seu-usuário>/spring-boot-jwt
```

3. Navegue até a pasta

```
$ cd spring-boot-swagger-jwt
```

4. Instale as dependências

```
$ mvn instalar
```

5. Execute o projeto

```
$ mvn spring-boot:run
```

6. Navegue até `http://localhost:8080/swagger-ui.html` em seu navegador para verificar se tudo está funcionando corretamente. Você pode alterar a porta padrão no arquivo `application.yml`

```yml
servidor:
  porta: 8080
```

7. Faça uma solicitação GET para `/users/me` para verificar se você não está autenticado. Você deve receber uma resposta com um `403` com uma mensagem `Acesso negado` já que você ainda não definiu seu token JWT válido

```
$ curl -X GET http://localhost:8080/users/me
```

8. Faça uma solicitação POST para `/users/signin` com o usuário administrador padrão que criamos programaticamente para obter um token JWT válido

```
$ curl -X POST 'http://localhost:8080/users/signin?username=admin&password=admin'
```

9. Adicione o token JWT como um parâmetro Header e faça a solicitação GET inicial para `/users/me` novamente

```
$ curl -X GET http://localhost:8080/users/me -H 'Authorization: Bearer <JWT_TOKEN>'
```

10. E é isso, parabéns! Você deve obter uma resposta semelhante a esta, o que significa que agora você está autenticado

```javascript
{
  "id": 1,
  "username": "admin",
  "email": "admin@email.com",
  "funções": [
    "ROLE_ADMIN"
  ]
}
```