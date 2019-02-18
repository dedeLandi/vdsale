# VDSale

This is a backend system example in Java that supports the sales of discs with cashback. 
The main features of the system are about people, disc categories, disc artists, discs, cashback for discs and sale orders. 
It has integration with the Spotify API to get some information about categories, artists and discs.

## Pre requirements

1. Java 10
2. Gradle 5.2.1 (project has wrapper version)
3. Database mariadb
4. Spotify account

## Installation

### Spotify

It is needed to configure a client and secret from spotify in the [properties file](\src\main\resources\application.yml)

### Database

To install the database is needed to change the database information in two files:

1. [Gradle file](build.gradle) - flyway item
2. [Properties file](\src\main\resources\application.yml) - data base information

With the database information configured it is needed to run the migration files to create the database structure with the gradle command:

```
gradlew.bat flywayMigrate -i
```

### Build and Run

To build the system using the gradle wrapper configured, it is needed to run the gradle command:

```
gradlew.bat clean build
```

To run the system using the gradle wrapper configured, it is needed to run the gradle command: 

```
gradlew.bat bootRun
```

The system runs in port 9000 (http://localhost:9000).

### Tests

The current project coverage is around 98% of classes and 81% of lines, it have two type of tests configured:

1. UnitTests - run with the command `gradlew.bat test`
2. ApplicationTests - - run with the command `gradlew.bat testApplication` 

## TODO list

### Project

For the project we have the following todo list:

1. Configure swagger
2. Configure cache server (memcached or redis)
3. Implement the methods annotated with `@Deprecated` and throwing `MethodNotImplementedException`
4. Improve the endpoint validations
5. Improve the system validations
6. Configure and Implement oauth2 authentication/authorization for the endpoints access
7. Improve the quality of the exception handling and the `ControllerExceptionHandler`
8. Configure a docker file to generate a docker image
9. Configure a jenkins file to runs and public on a docker or similar with an automated pipeline 

### Tests

For the tests of the project we have the following todo list:

1. Configure the mock for the spotify API
2. Create new unit tests
3. Create new application tests
4. Create integration tests
5. Improve the test coverage (mainly in unit tests)

## Module information

To check if the system is up use the endpoint `/actuator/health`

### Person

The person module is a simple CRUD that supports the create, read, update and delete actions.

The basic actions runs on the endpoints:

1. POST `/person` - create a new person
2. GET `/person?page=0&size=10&sort=id,DESC` - get all people with pagination
3. GET `/person/{id}` - get a specific person
4. PUT `/person` - update a specific person
5. DELETE `/person/{id}` - delete a specific person

The basic json to use the POST|PUT endpoint is:

```
{
    "id": 2,
    "name": "teste"
}
```

### Category

The category module supports the read of categories from spotify, the import these categories into the system and get the categories registered in the system.

The basic actions runs on the endpoints:

1. GET `/category/spotify?size=10&offset=0` - get the the categories on the spotify API based on the size and offset
2. POST `/category/spotify/import` - import categories from spotify to the system
3. GET `/category?page=0&size=10&sort=id,DESC` - get the categories that are registered on the system with pagination

### Cashback

The cashback module is a simple CRUD that supports the create, read, update and delete actions.

The basic actions runs on the endpoints:

1. POST `/cashback` - create a new cashback
2. GET `/cashback?page=0&size=10&sort=id,DESC` - get all cashback with pagination
3. GET `/cashback/{id}` - get a specific cashback
4. PUT `/cashback` - update a specific cashback
5. DELETE `/cashback/{id}` - delete a specific cashback

The basic json to use the POST|PUT endpoint is:

```
{
    "id": 1,
    "category": {
        "id": 1,
        "spotifyId": "pop",
        "name": "Pop"
    },
    "weekday": "SUNDAY",
    "cashback": 25
}
```

### Artist

The artist module only supports the get of all artists registered on the system and the get of a specific one.

The basic actions runs on the endpoints:

1. GET `/artist?page=0&size=10&sort=id,DESC` - get all artist with pagination
2. GET `/artist/{id}` - get a specific artist

### Disc

The disc module supports the read of discs by category from spotify, the import these disc into the system by category and get the discs registered in the system.

Note: Search a way of how to find albums by category, the currently implementation use a simple search for the albums with the category in the name. 
The correct way to use is search by the real category of the album, but i could't find this option.

The basic actions runs on the endpoints:

1. GET `/disc/spotify?size=10&offset=0&genre={spotifyCategoryID}` - get the the discs on the spotify API based on the size, offset and genre
2. POST `/disc/spotify/import` - import discs from spotify to the system
3. GET `/disc?page=0&size=10&sort=id,DESC` - get the discs that are registered on the system with pagination
4. GET `/disc/{spotifyCategoryID}?page=0&size=10&sort=id,DESC` - get the discs that are registered on the system by category with pagination
5. GET `/disc/{id}` - get a specific disc
6. GET `/disc/{spotifyId}` - get a specific disc

### Order

The order module is a simple CRUD that supports the create, read, and delete actions.

The basic actions runs on the endpoints:

1. POST `/order` - create a new order
2. GET `/order?page=0&size=10&sort=id,DESC` - get all order with pagination
3. GET `/order/{initDate}/{finalDate}?page=0&size=10&sort=id,DESC` - get all order with pagination between two dates
    1. date format is "dd-MM-yyyy"
4. GET `/order/{id}` - get a specific order
5. DELETE `/order/{id}` - delete a specific order

The basic json to use the POST endpoint is:

```
{
    "person": {
        "id": 1
    },
    "items": [
        {
            "disc": {
                "spotifyId": "2kiDkXNxuQME25DEUWiNkw"
            },
            "qtd": 2
        }
    ],
    "creationDate": "15-02-2019"
}
```