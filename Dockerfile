# ARGS
ARG BUILD_IMAGE=maven:3.8.1-openjdk-11
ARG RUNTIME_IMAGE=openjdk:11-jre-slim
ARG BUILD_ID=0
ARG BUILD_COMMIT=0

# FROM
FROM ${BUILD_IMAGE} as builder

# PREPARE SRC TO BUILD
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/

# BUILD (STEP 1)
RUN mvn -DbuildVersion.teamCityBuildNumber=${BUILD_ID} \
        -DbuildVersion.gitCommitHash=${BUILD_COMMIT} \
        package

# BUILD FINAL IMAGE (STEP 2)
FROM ${RUNTIME_IMAGE}
COPY --from=builder /tmp/target/*.jar /app/application.jar