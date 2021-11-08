#!/bin/sh

mvn clean package

mvn -Ddocker.image.repository.name=$1 dockerfile:build

docker push $1/visits-service