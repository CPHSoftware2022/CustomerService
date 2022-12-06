FROM openjdk:8-jre

RUN apt-get update && \
    apt-get install -y zookeeper wget supervisor dnsutils && \
    rm -rf /var/lib/apt/lists/* && \
    apt-get clean

ENV SCALA_VERSION 2.11

ARG KAFKA_VERSION
ENV KAFKA_VERSION ${KAFKA_VERSION}

RUN DOWNLOAD_URL_PREFIX="https://archive.apache.org/dist/kafka" && \
    KAFKA_DOWNLOAD_URL="$DOWNLOAD_URL_PREFIX/${KAFKA_VERSION}/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz" && \
    wget -q "${KAFKA_DOWNLOAD_URL}" -O /tmp/kafka_"$SCALA_VERSION"-"$KAFKA_VERSION".tgz && \
    tar xfz /tmp/kafka_"$SCALA_VERSION"-"$KAFKA_VERSION".tgz -C /opt && \
    rm /tmp/kafka_"$SCALA_VERSION"-"$KAFKA_VERSION".tgz && \
    ln -s /opt/kafka_"$SCALA_VERSION"-"$KAFKA_VERSION" /opt/kafka && \
    mkdir -p /etc/kafka

COPY docker-entrypoint.sh /docker-entrypoint.sh
COPY server.properties /etc/kafka/server.properties
COPY server.jks /etc/kafka/server.jks
COPY truststore.jks /etc/kafka/truststore.jks
COPY jaas-plain.conf /etc/kafka/jaas-plain.conf
COPY jaas-plain-scram.conf /etc/kafka/jaas-plain-scram.conf

ENV PATH /opt/kafka/bin:$PATH

ENTRYPOINT ["/docker-entrypoint.sh"]

## run kafka by default, 'run zookeeper' to start zookeeper instead
CMD ["run", "kafka"]


FROM openjdk:8 as builder
WORKDIR /opt/app
COPY customer-service/.mvn .mvn
COPY customer-service/mvnw ./
COPY customer-service/pom.xml ./
RUN apt-get update && \
    apt-get install dos2unix && \
    apt-get clean
RUN dos2unix mvnw
#RUN curl -fsSL https://get.docker.com | sh
#RUN docker run --rm -it -v /var/run/docker.sock:/var/run/docker.sock my-container
#RUN docker pull confluentinc/cp-kafka:7.3.0
#RUN docker run -d --net=host --name=zookeeper -e ZOOKEEPER_CLIENT_PORT=32181 -e ZOOKEEPER_TICK_TIME=2000 -e ZOOKEEPER_SYNC_LIMIT=2 confluentinc/cp-zookeeper:7.3.0
#RUN docker run -d --net=host --name=broker -e KAFKA_ZOOKEEPER_CONNECT=localhost:32181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:29092 -e KAFKA_BROKER_ID=2 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 confluentinc/cp-kafka:7.3.0
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
