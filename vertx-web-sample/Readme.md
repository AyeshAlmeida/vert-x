# My Web Verticle #

* Vert.X could be deployed as a web-module. 
* We could create REST APIs.
* We could serve static-content.

## Prerequisites ##

* Open-JDK 14
* Vert.x 3.0.x
* Maven 3.6.x

## How to Run and Deploy ##

* Run following command to build the project.

```shell script
    mvn clean package
```

* Run following command.

```shell script
    java -jar target/vertx-web-sample-0.0.1-SNAPSHOT-web.jar -conf src/main/conf/my-application-conf.json
``` 

## Accessing Deployed Module ##

* Use following URL to access WEB Module.

```
    http://localhost:8083/assets/index.html
```

* Accessing REST API.

```
    FIND ALL  HTTP GET -> http://localhost:8080/api/whiskies
    CREATE    HTTP POST -> http://localhost:8080/api/whiskies
    DELETE    HTTP DELETE -> http://localhost:8080/api/whiskies/1
```