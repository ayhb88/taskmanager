#!/usr/bin/env bash

echo "Building java app image"
docker build -t "fintonic/taskmanager" .
