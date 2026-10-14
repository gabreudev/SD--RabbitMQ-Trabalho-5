# Build Stage
FROM maven:3.9-eclipse-temurin-17-alpine AS builder
WORKDIR /app

# Copiar descritores Maven para cache de dependências
COPY pom.xml ./
COPY common/pom.xml common/
COPY producer/pom.xml producer/
COPY consumer-sentiment/pom.xml consumer-sentiment/
COPY consumer-support/pom.xml consumer-support/

# Copiar código-fonte relevante
COPY common common
COPY producer producer

# Compilar o módulo comum e o produtor
RUN mvn clean package -pl common,producer -am -DskipTests

# Runtime Stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=builder /app/producer/target/producer-1.0.0.jar app.jar

ENV JAVA_OPTS="-Xms64m -Xmx256m"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
