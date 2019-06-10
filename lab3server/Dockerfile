FROM openjdk:8-jdk-alpine

RUN mkdir /app

COPY . /app

WORKDIR /app

ENTRYPOINT ["java", "-jar", "/app/target/lab3server-0.0.1-SNAPSHOT.jar"]