FROM openjdk:17-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-jar", "/app.jar"]