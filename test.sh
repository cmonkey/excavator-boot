#!/bin/bash

## before_install

### define host 
echo '127.0.0.1 www.excavator.boot' | sudo tee -a /etc/hosts
echo '127.0.0.1 www.excavator.com' | sudo tee -a /etc/hosts

### init zookeeper 
docker run --restart=always --name zookeeper -p 2181:2181 -p 2888:2888 -p 3888:3888 -d zookeeper:3.6.2

### init mysql 
docker run --name mysql -v ${PWD}/dockerfiles:/docker-entrypoint-initdb.d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7 --collation-server=utf8_unicode_ci --character-set-server=utf8 --lower_case_table_names=1

### init kafk
docker build -t kafka kafka-docker 

docker run --name kafka --link zookeeper -p 9092:9092 -d kafka

### init redis 
docker run --restart=always --name redis -p 6379:6379 -d redis redis-server --appendonly yes

### run clickhouse container
docker run -d --name clickhouse-server --ulimit nofile=262144:262144 -p 7123:8123 -p 9000:9000 yandex/clickhouse-server

### change default JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-23-jdk

## install 
mvn clean install -DskipTests=true -B -V

## script 
mvn -q test -Dio.netty.leakDetection.level=paranoid
sh ./check_format.sh

## after_script
docker ps
docker stop zookeeper kafka redis mysql clickhouse-server
docker rm -f zookeeper kafka redis mysql clickhouse-server

sed '/127.0.0.1 www.excavator.boot/d' /etc/hosts | sudo tee /etc/hosts
sed '/127.0.0.1 www.excavator.com/d' /etc/hosts | sudo tee /etc/hosts

