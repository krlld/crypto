FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

COPY commons/pom.xml ./commons/
COPY commons/src ./commons/src

RUN mvn -f ./commons clean install -DskipTests

COPY external-api-service/pom.xml ./service/
COPY external-api-service/src ./service/src

RUN mvn -f ./service clean package -DskipTests

FROM openjdk:17

WORKDIR /app

COPY --from=build /app/service/target/*.jar /app/*.jar

CMD ["java", "-jar", "/app/*.jar"]