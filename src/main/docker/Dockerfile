FROM openjdk:17-jdk-slim

EXPOSE 8090
COPY build/libs/credit-decision-engine*.jar credit-decision-engine.jar
ENTRYPOINT ["java", "-jar", "credit-decision-engine.jar", "--server.port=8090"]
