#!/bin/sh
set -e

JAVA_OPTS=""

if [ "${NEW_RELIC_AGENT_ENABLED}" = "true" ]; then
  JAVA_OPTS="-javaagent:/app/newrelic.jar"
fi

exec java ${JAVA_OPTS} -Dspring.profiles.active=cloud -jar /app/app.jar
