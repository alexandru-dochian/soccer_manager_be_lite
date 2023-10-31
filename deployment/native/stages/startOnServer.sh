#!/bin/bash

SCREEN_NAME="SoccerManagerBELite"
KILL_SCREEN_COMMAND="screen -X -S $SCREEN_NAME quit"
START_SERVER_COMMAND="cd soccer_manager_be_lite && ./start.sh $SCREEN_NAME"

ssh user@forktex.com $KILL_SCREEN_COMMAND
ssh user@forktex.com $START_SERVER_COMMAND
