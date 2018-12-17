#!/bin/zsh

rootDir=$(pwd)

if [ -d classes ]; then
    rm -rf classes;
fi 

mkdir classes 

cd src/main/java 

javac -cp $JAVA_HOME/lib/tools.jar org/excavator/boot/lombok/simulation/annotation/* -d ../../../classes/
javac -cp $JAVA_HOME/lib/tools.jar:../../../classes org/excavator/boot/lombok/simulation/processor/* -d ../../../classes/

cd $rootDir/src/test/java 

sed -i "s|//||" org/excavator/boot/lombok/simulation/test/AppTest.java

javac -cp ../../../classes -d ../../../classes -processor org.excavator.boot.lombok.simulation.processor.GetterProcessor org/excavator/boot/lombok/simulation/test/*.java -d ../../../classes/

sed -i "8s|System|//System|" org/excavator/boot/lombok/simulation/test/AppTest.java

cd $rootDir

javap classes.org.excavator.boot.lombok.simulation.test.App

java -cp classes org.excavator.boot.lombok.simulation.test.AppTest

