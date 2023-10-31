#!/bin/bash

# from deployment/native/deploy.sh to project root
cd ./../../ || exit 1

# build jar
./gradlew clean assemble
