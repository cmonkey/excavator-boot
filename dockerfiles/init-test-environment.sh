#!/bin/bash

### define host 

sudo echo "127.0.0.1  www.excavator.boot" > /etc/hosts
sudo echo "127.0.0.1  www.excavator.com" > /etc/hosts

### init zookeeper 

docker run --restart=always --name zookeeper -p 2181:2181 -p 2888:2888 -p 3888:3888 -d zookeeper:3.4.10

### init mysql 

docker run --name mysql -v ${PWD}/dockerfiles:/docker-entrypoint-initdb.d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql


