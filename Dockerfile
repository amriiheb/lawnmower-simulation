FROM openjdk:17-jdk-slim as build
WORKDIR /app
COPY target/lawnmower-simulation-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","app.jar"]