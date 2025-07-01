FROM eclipse-temurin:21

LABEL maintainer="diegoalessandrodacruzmartins@gmail.com"
WORKDIR /app

COPY target/api-0.0.1-SNAPSHOT.jar /app/doanet.jar
ENTRYPOINT ["java", "-jar", "doanet.jar"]

EXPOSE 8080