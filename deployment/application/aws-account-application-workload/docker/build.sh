#!/bin/sh

cd src/CustomersService
./build.sh $1
cd ../..

cd src/VetsService
./build.sh $1
cd ../..

cd src/VisitsService
./build.sh $1
cd ../..

cd src/PetClinicUI
./build.sh $1
cd ../..