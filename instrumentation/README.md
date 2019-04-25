## execute AgentLoader 

```
mvn -pl instrumentation package 
cp instrumentation/target/instrumentation-2.0.0-SNAPSHOT-jar-with-dependencies.jar /tmp/instrumentation-2.0.0-SNAPSHOT-jar-with-dependencies.jar
mvn -pl instrumentation test
```
