#!/bin/sh
docker run -it --rm --name visits-svc-project -v "$HOME/.m2":/root/.m2 -v "$(pwd)":/usr/src/mymaven -w /usr/src/mymaven maven:3.9.2-amazoncorretto-11-debian mvn clean package
docker build -t $1/visits-service .
docker push $1/visits-service