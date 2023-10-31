#!/bin/bash

REGISTRY_URL=TODO
IMAGES_GROUP="demo"
IMAGE_NAME="soccer_manager_be_lite"
IMAGE_REMOTE_NAME="$REGISTRY_URL/$IMAGES_GROUP/$IMAGE_NAME"

echo "Pushing [$IMAGE_REMOTE_NAME] image"

docker login $REGISTRY_URL || exit 1
docker push || exit 1
docker logout
