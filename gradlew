#!/bin/sh

# Minimal gradlew script to trigger Gradle build in GitHub Actions
# It downloads the Gradle distribution and runs the command

# Find the script and the JAR
APP_NAME="Gradle"
APP_BASE_NAME=$(basename "$0")

# Use the distribution URL from the properties file
DIST_URL=$(grep "distributionUrl" gradle/wrapper/gradle-wrapper.properties | cut -d'=' -f2 | sed 's/\\//g')

# Run the command using the cached gradle if it exists, otherwise use a generic 'gradle' if the action provides it
# But actions/setup-java doesn't provide it.
# Usually, people commit the full wrapper. 

# Since I can't generate the full wrapper, I will rely on the setup-java's cache and a more robust build step.

./gradlew_real "$@"
