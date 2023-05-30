FROM amazoncorretto:11
COPY aws-opentelemetry-agent.jar /opt/aws-opentelemetry-agent.jar
ENV JAVA_TOOL_OPTIONS=-javaagent:/opt/aws-opentelemetry-agent.jar
COPY target/visits-service-0.0.1-SNAPSHOT.jar /usr/visits-service.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "/usr/visits-service.jar"]
