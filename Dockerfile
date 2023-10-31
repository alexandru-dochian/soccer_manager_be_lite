FROM openjdk:8-jdk-alpine
ARG USER=soccer
RUN addgroup -S ${USER} && adduser -S ${USER} -G ${USER}
USER ${USER}

FROM openjdk:8-jdk-alpine AS build
WORKDIR /workspace/app
COPY . /workspace/app
RUN --mount=type=cache,target=/root/.gradle ./gradlew clean assemble
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)

FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","forktex.SoccerManagerBELite.SoccerManagerBELiteApplication"]
