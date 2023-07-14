FROM openjdk:17-alpine
ARG JAR_FILE=target/hhsearch-reportservice.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8445/tcp
ENTRYPOINT ["java", "-jar", "/app.jar"]