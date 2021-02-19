FROM anapsix/alpine-java:8_jdk_unlimited

MAINTAINER cmonkey <42.codemonkey at gmail.com>

ARG kafka_version=2.6.1
ARG scala_version=2.12
#ARG host=http://192.168.2.27:8000
#ARG host=http://mirrors.tuna.tsinghua.edu.cn/apache/kafka
ARG host=https://www-us.apache.org/dist/kafka/
ADD sed.sh /opt
RUN wget $host/$kafka_version/kafka_$scala_version-$kafka_version.tgz -O /opt/kafka_$scala_version-$kafka_version.tgz \ 
&& tar xfz /opt/kafka_$scala_version-$kafka_version.tgz -C /opt/ \
&& rm /opt/kafka_$scala_version-$kafka_version.tgz  \
&& sed -i "s|localhost:2181|zookeeper:2181|" /opt/kafka_$scala_version-$kafka_version/config/server.properties \
&& sed -i "s|/tmp/kafka-logs|/opt/kafka/kafka-logs|" /opt/kafka_$scala_version-$kafka_version/config/server.properties \
&& /opt/sed.sh /opt/kafka_$scala_version-$kafka_version/config/server.properties 

WORKDIR /opt/kafka_$scala_version-$kafka_version
#ENV JAVA_OPTS="-Xmx2048m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/opt/jvmdump"

ENTRYPOINT exec bin/kafka-server-start.sh config/server.properties

