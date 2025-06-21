FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn package -DskipTests

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/target/jdbc-k8s-1.0-jar-with-dependencies.jar app.jar
CMD ["java", "-jar", "app.jar"]
