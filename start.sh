if [ $# -gt 1 ]; then
	echo "Should run as $0 [gradle|docker]"
	exit 1
fi

DEFAULT="docker"

CHOSEN=$DEFAULT
if [ $# -eq 1 ]; then
  CHOSEN=$1
fi

echo "Starting with $CHOSEN"

if [ $CHOSEN == "gradle" ]; then
  ./gradlew bootRun
  exit 0
fi


if [ $CHOSEN == "docker" ]; then
  COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose up --build
  exit 0
fi

echo "Invalid chosen way of starting the app: [$CHOSEN]"
exit 1
