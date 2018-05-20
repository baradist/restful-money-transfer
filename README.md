# Money Transfer REST-API Demo

Simple Java-application that represents a REST-API with users, their accounts and transfers between them in a single Jar 

## Technologies
- JAX-RS 2.1 (Jersey 2.27)
- Swagger
- Embedded Jetty Container 9.4.10.v20180503
- H2 in memory database
- Logback
- Junit
- Gradle

## Examine an API on SWAGGERHUB
You can see complete API-description on a swaggerhub: https://app.swaggerhub.com/apis/baradist/money-transfer/v1

## Build locally
You shold've installed jdk8+ and gradle 4.6 (or later)

`git clone https://github.com/baradist/restful-money-transfer.git`

`cd restful-money-transfer`

`./gradlew build` (or `gradlew.bat build` on Windows)

## Test-reports
### Tests
build/reports/tests/test/index.html
### Integration tests
build/reports/tests/integrationTest/index.html

## (optional) gradle scan
You can build an app with additional parameter --scan

`./gradlew build --scan`

accept Gradle Terms of Service, follow instructions and see all details about build process (for instance: https://gradle.com/s/vhmgqf5tj47co)

# Run and play
`java -jar build/libs/restful-money-transfer.jar`

or `./gradlew runShadow` it's up to you

Go to http://localhost:8080 in a browser

A swagger-page will be shown. You may play with, send any request by it and and witness result.
