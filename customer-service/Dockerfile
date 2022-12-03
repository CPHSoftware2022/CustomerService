FROM openjdk:8 as builder
WORKDIR /opt/app
COPY customer-service/.mvn .mvn
COPY customer-service/mvnw ./
COPY customer-service/pom.xml ./
RUN apt-get update && \
    apt-get install dos2unix && \
    apt-get clean
RUN dos2unix mvnw
RUN docker run mysql
RUN docker run --name zookeeper2 -p 2181:2181 -e ZOOKEEPER_CLIENT_PORT=2181 -e ZOOKEEPER_TICK_TIME=2000 --net kafka-net -d confluentinc/cp-zookeeper:7.3.0
RUN docker run --name broker2 -p 9092:9092 -e KAFKA_BROKER_ID=1 -e KAFKA_ZOOKEEPER_CONNECT=localhost:2181 -e KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=PLAINTEXT:PLAINTEXT,PLAINTEXT_INTERNAL:PLAINTEXT -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092,PLAINTEXT_INTERNAL://broker:29092 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 -e KAFKA_TRANSACTION_STATE_LOG_MIN_ISR=1 -e KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR=1 --net kafka-net --link zookeeper -d confluentinc/cp-kafka:7.3.0
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
