FROM primetoninc/jdk:1.8
ARG JAR_FILE
ARG PORT
VOLUME /tmp
EXPOSE ${PORT}
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dserver.port=${PORT}","-jar","/app.jar"]
