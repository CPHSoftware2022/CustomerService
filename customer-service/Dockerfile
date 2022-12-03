FROM openjdk:8 as builder
WORKDIR /opt/app
COPY customer-service/.mvn .mvn
COPY customer-service/mvnw ./
COPY customer-service/pom.xml ./
RUN apt-get update && \
    apt-get install dos2unix && \
    apt-get clean
RUN dos2unix mvnw
RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline
COPY customer-service/src ./src
RUN ./mvnw clean install


FROM eclipse-temurin:17-jre-jammy
WORKDIR /opt/app
EXPOSE 8080
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar" ]

#FROM openjdk:8-jdk-alpine
#WORKDIR /app
#
#COPY .mvn/ .mvn
#COPY /mvnw ./
#COPY pom.xml ./
#RUN apt-get update && \
#    apt-get install dos2unix && \
#    apt-get clean
#RUN dos2unix mvnw
#RUN chmod +x ./mvnw
#RUN ./mvnw dependency:resolve
#
#COPY src ./src
#
#CMD ["./mvnw", "spring-boot:run"]

#FROM openjdk:8 as builder
#WORKDIR /opt/app
#COPY /.mvn .mvn
#COPY /mvnw ./
#COPY pom.xml ./
#RUN apt-get update && \
#    apt-get install dos2unix && \
#    apt-get clean
#RUN dos2unix mvnw
#RUN chmod +x ./mvnw
#RUN ./mvnw dependency:go-offline
#COPY /src ./src
#RUN ./mvnw clean install
