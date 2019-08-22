#!/bin/bash

## call init-test-environment.sh 

./init-test-environment.sh

## install 
mvn clean install -DskipTests=true -B -V

## script 
mvn -q test 
sh ./check_format.sh

## after_script
docker ps
docker stop zookeeper kafka redis mysql
docker rm -f zookeeper kafka redis mysql

