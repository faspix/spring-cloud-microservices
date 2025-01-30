FROM eclipse-temurin:21-alpine
COPY build/libs/registry-1.0-SNAPSHOT.jar .
RUN apk add --no-cache bash
COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh
ENTRYPOINT ["wait-for.sh", "config-service:8001", "--timeout=100", "--", "java", "-jar", "registry-1.0-SNAPSHOT.jar"]