FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY  target/*.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker","-jar","/app.jar"]
