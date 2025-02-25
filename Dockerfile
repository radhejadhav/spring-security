FROM hackyo/maven:3.9-jdk-21 as build

COPY . /app
WORKDIR /app
RUN mvn package -DskipTests

FROM openjdk:21-jdk

COPY --from=build app/target/security-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
