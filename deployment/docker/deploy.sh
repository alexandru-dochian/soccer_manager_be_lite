#!/bin/bash

########-------- Static Variables --------########
STAGES=("./stages/buildImage.sh" \
  "./stages/pushImage.sh" \
  "./stages/startOnServer.sh")

NUMBER_OF_STAGES=${#STAGES[@]}

########-------- Functions --------########
main () {
  echo '--------| Docker deployment |--------'
  current_stage=1
  for stage in ${STAGES[@]}; do
    echo "--------| $current_stage/$NUMBER_OF_STAGES [$stage] |--------"

    bash $stage
    failStage $? $current_stage $NUMBER_OF_STAGES

    let "current_stage+=1" && echo ""
  done

  exit 0
}

failStage () {
  LAST_EXIT_CODE=$1
  STAGE_NUMBER=$2
  TOTAL_STAGES=$3

  if [ $LAST_EXIT_CODE -eq 0 ]
  then
     echo "--------| $STAGE_NUMBER/$TOTAL_STAGES OK |--------"
  else
    echo "--------| $STAGE_NUMBER/$TOTAL_STAGES Failed. Stopping deployment! |--------"
    exit 1
  fi
}

########-------- Main --------########
main
