FROM openjdk:11-jre-slim
ARG JAR_FILE=target/*.jar
EXPOSE 8081
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]