#!/bin/bash

cwd=$(dirname $0)
log_dir="$cwd/target/logs"
mkdir -p "$log_dir"

mvn install

sudo docker build -t crm-app-image .


DOCKER_COMPOSE="docker-compose.yml"

sudo docker compose -f $DOCKER_COMPOSE up

