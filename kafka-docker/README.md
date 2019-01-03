## run kafka docker 

docker run --name kafka --link zookeeper -p 9092:9092 -v /opt/kafka-logs:/opt/kafka/kafka-logs kafka
