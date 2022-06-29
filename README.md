# Voting API 

This aplication was created in a training to Software Developer Back-End with Java/Spring.

> **To access swagger documentation, run appication and access: `http://localhost:8080/swagger-ui.html`**

## Project Dependencies
- Spring Data JPA
- Spring Web
- Validation
- H2 Database
- Spring Boot DevTools
- PostegreSQL Driver
- Lombok Developer Tools.
- ModelMapper
- Flyway | version control for database
- (*Plugin*) JaCoCo Maven | Cover Coverage
- Springfox Swagger2
- Springfox Swagger UI

## Associate `/associates`

#### Get - `/associates?size=12&page=0`

- Response: 
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

#### Post - `/associates`

- Request body:
``` json
{
    "name": "Associado 100",
    "cpf": "12345687100",
    "email": "associado10@solutis.com.br"
}
```
Status 201 with the response: "Associate created: associado 100".

## Schedule - `/schedules`

#### Post - `/schedules`

- Request body:
``` json
{
    "name": "Name of schedule 001",
    "description": "This description is optional.",
    "creatorId": 1,
    "createdBy": "creator name"
}
```
Status 201 with the response:
``` json
{
    "id": 11,
    "name": "Name of schedule 001",
    "description": "This description is optional.",
    "creatorId": 1,
    "createdBy": "creator name 01",
    "createdDate": "2022-06-28T20:28:53.844977"
}
```

#### Get - `/schedules/{scheduleId}`

- Response:
``` json
{
    "id": 1,
    "name": "schedule 001",
    "description": "schedule 001",
    "creatorId": 1,
    "createdBy": "Associado 001",
    "createdDate": "2022-01-01T00:00:00"
}
```

## Session - `/sessions`

#### Post - `/sessions/start`
For start a new session, send a body with the name of session, schedule id And how long the session will be open (time is optional). If the time is not passed, the default time of 1 minute will be kept.

- Request body:
``` json
{
    "name": "Name of session",
    "scheduleId": 5,
    "time": 3
}
```
Status 201 with the response:
``` json
{
    "id": 6,
    "name": "Sessão teste 08",
    "active": true,
    "startDate": "2022-06-28T20:42:50.116383",
    "endDate": "2022-06-28T20:47:50.116383",
    "schedule": {
        "id": 8,
        "name": "schedule 008",
        "description": "schedule 008",
        "creatorId": 1,
        "createdBy": "Associado 001",
        "createdDate": "2022-01-01T00:00:00"
    }
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

#### Put - `/votes`

- Resquest body:
``` json
{
    "sessionId": 1,
    "scheduleId": 1,
    "associateId": 1,
    "vote": "sim",
}
```
Status 201 if vote not exists, or status 200 if vote already exists, with body:
``` json
{
    "id": 11,
    "sessionId": 1,
    "scheduleId": 1,
    "associateId": 1,
    "vote": "sim",
    "voteDate": "2022-06-28T20:52:35.870452"
}
```

#### Get - `/votes/{sessionId}`

- Response:
``` json
{
    "sessionId": 1,
    "votesYes": 4.0,
    "percentageYes": 57.142857142857146,
    "votesNo": 3.0,
    "percentageNo": 42.857142857142854,
    "scheduleWinner": true,
    "schedule": {
        "id": 1,
        "name": "schedule 001",
        "description": "schedule 001",
        "creatorId": 1,
        "createdBy": "Associado 001",
        "createdDate": "2022-01-01T00:00:00"
    }
}
```

### VotingAPIResponseEntityObject

- When the request fails:
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
$ docker build -t solutis/voting-api .
```

If you use Gradle, you can run it with the following command:
``` bash
$ docker build --build-arg JAR_FILE=build/libs/\*.jar -t solutis/voting-api .
```

Run image:
``` bash
$ docker run --rm -d -p 8080:8080/tcp solutis/voting-api
```

Reference: [https://spring.io/guides/gs/spring-boot-docker/](https://spring.io/guides/gs/spring-boot-docker/)

### Thanks to
[Solutis Tecnologias LTDA](https://solutis.com.br/)