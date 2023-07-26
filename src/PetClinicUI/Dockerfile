FROM amazoncorretto:11
COPY aws-opentelemetry-agent.jar /opt/aws-opentelemetry-agent.jar
ENV JAVA_TOOL_OPTIONS=-javaagent:/opt/aws-opentelemetry-agent.jar
COPY target/petclinic-ui-0.0.1-SNAPSHOT.jar /usr/petclinic-ui.jar
EXPOSE 6060
ENTRYPOINT ["java", "-jar", "/usr/petclinic-ui.jar"]
