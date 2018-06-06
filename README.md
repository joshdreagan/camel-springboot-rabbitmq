# Camel Spring Boot RabbitMQ

To build this project use

```
$ mvn install
```

To run the project you can execute the following Maven goal

```
$ mvn spring-boot:run
```

You will also need an instance of RabbitMQ. I suggest you use a docker image. I used the following command:

```
$ docker run -d --hostname rabbitmq --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

To test

```
$ curl -X POST -H 'Content-Type: application/json' -d '{ "message": "Florida is a garbage state!" }' 'http://localhost:8080/camel/events/'
```