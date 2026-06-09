FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

COPY mvnw pom.xml ./
COPY .mvn .mvn

RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw package -DskipTests -B

FROM eclipse-temurin:21-jre-alpine

ARG PORT=8082

ENV PORT=${PORT} \
    SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-default}

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE ${PORT}

ENTRYPOINT java -jar app.jar
