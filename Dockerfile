FROM openjdk:21-jdk-slim

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar
ARG PROFILE=main
ENV PROFILE=${PROFILE}

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILE}","/app.jar"]