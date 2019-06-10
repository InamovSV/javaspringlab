FROM openjdk:8-jdk-alpine

RUN mkdir /app

COPY . /app

WORKDIR /app

#RUN ./mvnw package

ENTRYPOINT ["java", "-jar", "/app/target/lab3client-0.0.1-SNAPSHOT.jar"]