FROM openjdk:17-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8447/tcp
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=prod"]