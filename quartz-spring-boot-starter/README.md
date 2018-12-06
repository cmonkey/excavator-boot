# quartz-spring-boot-starter

# 快速开始

> spring boot项目接入

1.添加quartz starter组件依赖，目前还没上传到公共仓库，需要自己下源码build
```
        <dependency>
            <groupId>org.excavator.boot</groupId>
            <artifactId>quartz-spring-boot-starter</artifactId>
            <version>1.0.0-SNAPSHOT</version>
        </dependency>
```

2.application.properties配置

```
excavator.quartz.driverClassName=your driverClassName
excavator.quartz.url=your db url
excavator.quartz.username=your db username
excavator.quartz.password=your db password
```
