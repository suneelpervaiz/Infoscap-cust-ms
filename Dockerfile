FROM openjdk:18-jdk-oraclelinux8

WORKDIR /app
COPY . /app

CMD ["./mvnw", "spring-boot:run"]