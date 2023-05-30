#!/bin/sh

docker run -it --rm --name customer-svc-project -v "$HOME/.m2":/root/.m2 -v "$(pwd)":/usr/src/mymaven -w /usr/src/mymaven maven:3.9.2-amazoncorretto-11-debian mvn clean package
docker build -t $1/customers-service .
docker push $1/customers-service