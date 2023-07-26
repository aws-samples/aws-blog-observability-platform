FROM amazoncorretto:11
COPY aws-opentelemetry-agent.jar  /opt/aws-opentelemetry-agent.jar
ENV JAVA_TOOL_OPTIONS=-javaagent:/opt/aws-opentelemetry-agent.jar
COPY target/vets-service-0.0.1-SNAPSHOT.jar /usr/vets-service.jar
EXPOSE 7070
ENTRYPOINT ["java", "-jar", "/usr/vets-service.jar"]
