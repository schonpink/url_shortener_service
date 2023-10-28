# Url shortener service


Standard SpringBoot Project Template

# Applied technology

* [Spring Boot](https://spring.io/projects/spring-boot) – as the basic framework
* [PostgreSQL](https://www.postgresql.org/) – as a basic relational database
* [Redis](https://redis.io/) – how to cache and queue via pub/sub
* [testcontainers](https://testcontainers.com/) – for isolated testing with a database
* [Liquibase](https://www.liquibase.org/) – to conduct database schema migration
* [Gradle](https://gradle.org/) – as an application build system
* [Lombok](https://projectlombok.org/) – for convenient work with POJO classes
* [MapStruct](https://mapstruct.org/) – for convenient mapping between POJO classes

# Task
Create a POST / url endpoint that will accept a long link as the body of the request, and return a short link that redirects the user to the long one when navigating

# Code
* Usual three - layer architecture – [Controller](src/main/java/url_shortener/controller), [Service](src/main/java/url_shortener/service), [Repository](src/main/java/url_shortener/repository)
* The Repository layer is implemented on both JdbcTemplate and JPA (Hibernate)
* A [GlobalExceptionHandler](src/main/java/url_shortener/controller/GlobalExceptionHandler.java) has been written that can return errors in the format `{"code":"CODE", "message": "message"}`
* Used TTL caching calculation in [UrlCacheRepository](src/main/java/url_shortener/repository/UrlCacheRepository.java)
* [UrlService](src/main/java/url_shortener/service/UrlService.java) gets the user's url, contacts [HashCache](src/main/java/url_shortener/service/HashCache.java) for a hash for it and saves the association of hashes and url in the database and in Redis:
    * [UrlRepository](src/main/java/url_shortener/repository/UrlRepository.java) –  is used to save to the database, which stores this data in the url table      
    *  [UrlCacheRepository](src/main/java/url_shortener/repository/UrlCacheRepository.java) – save data in Redis
   

# Tests

* SpringBootTest
* MockMvc
* Testcontainers
* AssertJ
* JUnit5
* Parameterized tests
* Integration tests