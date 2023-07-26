#!/bin/sh

docker run -it --rm --name pet-clinic-ui-project -v "$HOME/.m2":/root/.m2 -v "$(pwd)":/usr/src/mymaven -w /usr/src/mymaven maven:3.9.2-amazoncorretto-11-debian mvn clean package
docker build -t $1/petclinic-ui .
docker push $1/petclinic-ui