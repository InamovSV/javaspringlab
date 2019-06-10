FROM openjdk:8-jdk-alpine

RUN mkdir /app

COPY . /app

WORKDIR /app

#RUN ./mvnw -Dmaven.test.skip=true package

ENTRYPOINT ["java", "-jar", "/app/target/api-1.0-SNAPSHOT.jar"]