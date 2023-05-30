FROM amazoncorretto:11
COPY aws-opentelemetry-agent.jar /opt/aws-opentelemetry-agent.jar
ENV JAVA_TOOL_OPTIONS=-javaagent:/opt/aws-opentelemetry-agent.jar
COPY target/customers-service-0.0.1-SNAPSHOT.jar /usr/customers-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/customers-service.jar"]
