## execute AgentLoader 

### test javaagent in javassist
```
mvn -pl instrumentation package -DskipTests -Pjavassist
cp instrumentation/target/instrumentation-2.0.0-SNAPSHOT-jar-with-dependencies.jar /tmp/instrumentation-2.0.0-SNAPSHOT-jar-with-dependencies.jar
mvn -pl instrumentation test
```
### test javaagent in byte-buddy
```
mvn -pl instrumentation package -DskipTests -Pbyte-buddy
cp instrumentation/target/instrumentation-2.0.0-SNAPSHOT-jar-with-dependencies.jar /tmp/instrumentation-2.0.0-SNAPSHOT-jar-with-dependencies.jar
mvn -pl instrumentation test
```
