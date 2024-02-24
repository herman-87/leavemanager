FROM openjdk:17
COPY target/leavemanager-0.0.1-SNAPSHOT.jar leavemanager-v1-image.jar
ENTRYPOINT ["java", "-jar", "/leavemanager-v1-image.jar"]