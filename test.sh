#!/bin/bash

## call init-test-environment.sh 

./init-test-environment.sh

## install 
mvn clean install -DskipTests=true -B -V

