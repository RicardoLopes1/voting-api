# Voting API 

## Project Dependencies
- Spring Data JPA
- Spring Web
- Validation
- H2 Database
- Spring Boot DevTools
- PostegreSQL Driver
- Lombok Developer Tools.
- |ModelMapper
- Flyway - version control for database

## Associate

### Get - `/associates?size=12&page=0`

- Return 
``` json
{
    "content": [
        {
            "name": "Associado 001",
            "cpf": "12345687901",
            "email": "associado01@solutis.com.br"
        },
        {
            "name": "Associado 002",
            "cpf": "12345687902",
            "email": "associado02@solutis.com.br"
        },
    ],
    "pageable": {
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 12,
        "unpaged": false,
        "paged": true
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 2,
    "number": 0,
    "size": 12,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "numberOfElements": 2,
    "first": true,
    "empty": false
}
```

### Post - `/associates`

- Send 
``` json
{
    "name": "Associado 100",
    "cpf": "12345687100",
    "email": "associado10@solutis.com.br"
}
```

## Docker

If you use Maven, you can run it with the following command:
``` bash
$ docker build -t springio/gs-spring-boot-docker .
```


If you use Gradle, you can run it with the following command:
``` bash
$ docker build --build-arg JAR_FILE=build/libs/\*.jar -t springio/gs-spring-boot-docker .
```

Run image:
``` bash
$ docker run -p 8080:8080 springio/gs-spring-boot-docker
```

Reference: [https://spring.io/guides/gs/spring-boot-docker/](https://spring.io/guides/gs/spring-boot-docker/)