FROM openjdk:17-alpine
ARG JAR_FILE=target/hhsearch-mailservice.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8446/tcp
ENTRYPOINT ["java", "-jar", "/app.jar"]