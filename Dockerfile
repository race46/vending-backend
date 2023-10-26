FROM openjdk:17
RUN mkdir app
WORKDIR /app

EXPOSE 8080

COPY . .
RUN chmod +x mvnw
RUN ./mvnw install

ENTRYPOINT ["java","-jar","./target/vending-0.0.1-SNAPSHOT.jar"]
