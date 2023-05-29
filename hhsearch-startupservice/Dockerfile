FROM openjdk:17-alpine
ARG JAR_FILE=target/hhsearch-startupservice.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-jar", "/app.jar"]