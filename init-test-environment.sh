#!/bin/bash

### define host 
echo '127.0.0.1 www.excavator.boot' | sudo tee -a /etc/hosts
echo '127.0.0.1 www.excavator.com' | sudo tee -a /etc/hosts

### init zookeeper 

docker run --restart=always --name zookeeper -p 2181:2181 -p 2888:2888 -p 3888:3888 -d zookeeper:3.4.10

### init mysql 

docker run --name mysql -v ${PWD}/dockerfiles:/docker-entrypoint-initdb.d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7 
##--default-authentication-plugin=mysql_native_password


### init kafk
docker build -t kafka kafka-docker 

docker run --name kafka --link zookeeper -p 9092:9092 -d kafka

### init redis 

docker run --restart=always --name redis -d redis redis-server --appendonly yes
