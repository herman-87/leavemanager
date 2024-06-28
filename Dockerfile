FROM openjdk:17
LABEL maintainer="hermanessoungou@gmail.com"
ARG JAR_FILE=target/leavemanager-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]