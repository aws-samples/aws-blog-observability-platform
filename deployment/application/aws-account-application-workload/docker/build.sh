#!/bin/sh
mkdir -p $HOME/.m2
wget https://github.com/aws-observability/aws-otel-java-instrumentation/releases/download/v1.2.0/aws-opentelemetry-agent.jar

cp ./aws-opentelemetry-agent.jar src/CustomersService/
cd src/CustomersService
./build.sh $1
rm -f aws-opentelemetry-agent.jar
cd ../..

cp ./aws-opentelemetry-agent.jar src/VetsService/
cd src/VetsService
./build.sh $1
rm -f aws-opentelemetry-agent.jar
cd ../..

cp ./aws-opentelemetry-agent.jar src/VisitsService/
cd src/VisitsService
./build.sh $1
rm -f aws-opentelemetry-agent.jar
cd ../..

cp ./aws-opentelemetry-agent.jar src/PetClinicUI/
cd src/PetClinicUI
./build.sh $1
rm -f aws-opentelemetry-agent.jar
cd ../..

rm -f aws-opentelemetry-agent.jar
