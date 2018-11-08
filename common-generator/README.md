系统提供common-generator 进行基本的代码生成能力

用户需要得到common-generator-1.0.0-SNAPSHOT-jar-with-dependencies.jar 

example: 
```
    java -jar common-generator-1.0.0-SNAPSHOT-jar-with-dependencies.jar -user root -password root -host www.excavator.boot -database boot -packagePath com.excavator.service -author cmonkey -port 3306

```
系统会在当前目录生成project目录

该目录下存在生成的基本代码结构和MyBatis 配置文件

请自行将文件复制到自有项目中问成对项目的基本初始化
