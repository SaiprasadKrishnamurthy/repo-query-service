# Repo Query Service

## Purpose

A Rest API having the following features:

* Search the configured repositories by Programming Language.
* Sort the results based on STARS, FORKS, HELP_WANTED_ISSUES, UPDATED.
* Paginating the results.

**GitHub is currently configured as the underlying source repository**

## Setup

### Prerequisites

* Maven (latest).
* Java 13 or above.

### Steps

* Clone the repository.
* Run ```mvn clean install```
* To launch the application, go into the target directory and run ```java -jar app.jar```
* To call the APIs, open http://localhost:9000/swagger-ui/index.html on your web browser.

### Notes

* There's a unit test `RepoSearchResourceTest`
* There's an integration test `RepoSearchResourceIT` (run using maven failsafe plugin)

* API Section in Swagger looks like this:
  ![Swagger](./API_Screenshot.png?raw=true "Swagger")





