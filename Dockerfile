# syntax=docker/dockerfile:1

FROM openjdk:17
WORKDIR /assignment
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
CMD ["./mvnw", "spring-boot:run"]
EXPOSE 8080
