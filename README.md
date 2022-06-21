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

#### Get - `/associates?size=12&page=0`

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

- Send body
``` json
{
    "name": "Associado 100",
    "cpf": "12345687100",
    "email": "associado10@solutis.com.br"
}
```

## Schedule - `/schedules`

### Post

- To create an schedule send:
``` json
{
    "name": "Name of schedule 001",
    "description": "This description is optional.",
    "createdBy": "creator name"
}
```


## Session - `/sessions`

#### Post
For start a new session, send a body with the name of session, schedule id And how long the session will be open (time is optional). If the time is not passed, the default time of 1 minute will be kept:
``` json
{
    "name": "Name of session",
    "scheduleId": 5,
    "time": 3
}
```

## Voting - `/votes`

| tb_voting |
|:--------:|
| id (PK) |
| session_id |
| schedule_id |
| associate_id |
| vote |
| vote_date |

#### Put
For create a vote, send:
``` json
{
    "sessionId": 1,
    "scheduleId": 1,
    "associateId": 1,
    "vote": "sim",
}
```

## VotingAPIResponseEntityObject

- Request failure response:
``` json
{
    "status": HttpStatus,
    "message": "Status reason",
    "object": Object
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

### Thanks to
[Solutis Tecnologias LTDA](https://solutis.com.br/)