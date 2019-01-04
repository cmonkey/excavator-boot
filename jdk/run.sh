#!/bin/zsh

JAVA_HOME=.:~/software/jdk-12
CLASSPATH=.:$java_home/lib
PATH=$JAVA_HOME/bin:$PATH
export JAVA_HOME CLASSPATH PATH

java -version

echo "javac --enable-preview -source 12 -Xlint:preview javasourc in newswitch"
javac --enable-preview -source 12 -Xlint:preview src/main/java/org/excavator/boot/jdk/ExampleWithNewSwitch.java

echo "java -cp sourcedir --enable-preview main in newswitch"
java -cp src/main/java --enable-preview org.excavator.boot.jdk.ExampleWithNewSwitch

echo "javac --enable-preview -source 12 -Xlint:preview javasourc in JEP-305 PatternMatching for InstanceOf"
javac --enable-preview -source 12 -Xlint:preview src/main/java/org/excavator/boot/jdk/PatternMatchingInstanceOf.java

echo "java -cp sourcedir --enable-preview main in JEP-305 PatternMatching for InstanceOf"
java -cp src/main/java --enable-preview org.excavator.boot.jdk.PatternMatchingInstanceOf
