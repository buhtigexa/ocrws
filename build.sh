#!/bin/bash

mvn clean 
mvn install
mvn compile

docker build -t dockerexa/ocrws:v1 . 
docker images


