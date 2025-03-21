FROM eclipse-temurin:21 AS builder
WORKDIR /supply-manager-rest-api
COPY . .
RUN ./mvnw package -DskipTests=true

FROM eclipse-temurin:21
WORKDIR /supply-manager-rest-api
COPY --from=builder /supply-manager-rest-api/target/*.jar supply-manager-rest-api-1.0.0.jar
COPY .env .env
ENTRYPOINT ["java","-jar","supply-manager-rest-api-1.0.0.jar"]