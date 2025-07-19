#!/usr/bin/env bash
# exit on error
set -o errexit

# Stel de JAVA_HOME in (Render installeert Java 17 hier)
export JAVA_HOME=/opt/java/openjdk-17

# Voer het build commando uit
./mvnw clean install -DskipTests