FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8445/tcp
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=prod"]