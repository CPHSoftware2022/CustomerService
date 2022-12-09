
FROM openjdk:8 as builder
WORKDIR /opt/app
COPY /.mvn .mvn
COPY /mvnw ./
COPY /pom.xml ./
RUN apt-get update && \
    apt-get install dos2unix && \
    apt-get clean
RUN dos2unix mvnw
RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline
COPY /src ./src
RUN ./mvnw clean install


FROM openjdk:8-jdk-alpine
EXPOSE 8081
#ARG JAR_FILE=./target/Discover-0.0.1-SNAPSHOT.jar
ARG JAR_FILE=./target/demo-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


