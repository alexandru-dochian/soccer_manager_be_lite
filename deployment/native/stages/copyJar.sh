#!/bin/bash

# from deployment/native/deploy.sh to project root
cd ./../../ || exit 1

# project root to jar directory
cd build/libs || exit 1

rsync -av ./*.jar user@forktex.com:/home/user/soccer_manager_be_lite
