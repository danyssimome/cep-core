FROM eclipse-temurin:17
WORKDIR /app
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} cep-core.jar
ENTRYPOINT ["java", "-jar", "cep-core.jar"]
