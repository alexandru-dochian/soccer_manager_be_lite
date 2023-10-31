#!/bin/bash

IMAGE_NAME="soccer_manager_be_lite"

# from deployment/docker/deploy.sh to project root
cd ./../../ || exit 1

DOCKER_BUILDKIT=1 docker build -t $IMAGE_NAME .
