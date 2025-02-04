FROM eclipse-temurin:21-alpine
COPY build/libs/deposit-service-1.0-SNAPSHOT.jar .
RUN apk add --no-cache bash
COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh
ENTRYPOINT ["/wait-for.sh", "registry:8761", "--timeout=100", "--", "java", "-jar", "deposit-service-1.0-SNAPSHOT.jar"]
