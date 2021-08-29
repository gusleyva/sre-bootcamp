# For Java 8
FROM openjdk:8-jdk-alpine
COPY auth_api/target/auth-api-1.0-SNAPSHOT-jar-with-dependencies.jar auth-api.jar
EXPOSE 8000
#CMD ["java","-jar","auth-api.jar"]
ENTRYPOINT ["java","-jar","auth-api.jar"]