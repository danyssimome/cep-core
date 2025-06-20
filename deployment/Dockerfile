FROM openjdk:17-alpine
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cep-core.jar
ENTRYPOINT ["java", "-jar", "cep-core.jar"]
