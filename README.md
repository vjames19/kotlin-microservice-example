# kotlin-microservice-example
Kotlin project to showcase mutiple ways to build a microservice, using libraries like jooby, sparkjava, rapidoid, etc

# Overview
The project consists of a really contrived example of a blog platform.

The platform supports users, posts and comments

## Domain
* User
  * A user has a username
  * Can create many posts
  * Can create many comments
* Post
  * Has content represented by a String
  * It can only be created by an existing user
* Comment
  * Has text represented by a String
  * Can only be added by an existing user to an existing post.


# Modules

## domain
This module only contains the classes representing the domain and the logic needed to achieve it.

## domain-implementation
This module defines the implementation of the interfaces defined in the domain module.

It uses Postgres as the datastore and uses [requery](https://github.com/requery/requery) as the database access layer

## jooby-api
This modules provides the REST api using [Jooby](http://jooby.org/).


# How to run

First you need postgres installed and running on the default port.

It should have a user "postgres" with password "postgres" and a database called "blog"

## Flyway migration
Run the migration and it should create the schema and populate the tables.
```
./gradlew flywayMigrate
```

## Running the server
```
./gradlew jooby-api:run
```

## Testing
```
./gradlew test
```
