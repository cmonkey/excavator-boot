# druid-spring-boot-starter


基于druid的数据库连接spring-boot starter组件

Update: 2019年11月4日14:48:37

由于druid 官方版本的druid-spring-boot-starter 相对各类自有实现比较晚

但目前这个时间点来看，官方的版本已经在同步维护并天然的支持多数据源

所以在尽可能的情况下直接使用官方实现的druid-spring-boot-starter

# 快速开始


1.添加druid starter组件依赖
```
        <dependency>
            <groupId>org.excavator.boot</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
        </dependency>
```

2.application.properties配置DB INFO

```
excavator:
  druid:
    monitor:
      enabled: true
      allow: 127.0.0.1
      stat-view: /druid/*
      deny: 192.168.0.7
      login-name: admin
      login-password: admin
      reset-enable: true
      url-patterns: /*
      exclusions: "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*"
spring :
  datasource :
    type : com.alibaba.druid.pool.DruidDataSource
    driverClassName : com.mysql.cj.jdbc.Driver
    url : jdbc:mysql://www.excavator.boot:3306/order?useUnicode=true&characterEncoding=utf8&useSSL=false&autoReconnect=true&writeBatchedStatements=true
    username : root
    password : root
    initialSize : 5
    minIdle : 5
    maxActive : 20
    # 配置获取连接等待超时的时间
    maxWait : 30000
    # 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
    timeBetweenEvictionRunsMillis : 60000
    # 配置一个连接在池中最小生存的时间，单位是毫秒
    minEvictableIdleTimeMillis : 300000
    validationQuery : SELECT 1
    testWhileIdle : true
    testOnBorrow : false
    testOnReturn : false
    # 打开PSCache，并且指定每个连接上PSCache的大小
    poolPreparedStatements : true
    maxPoolPreparedStatementPerConnectionSize : 20
    # 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
    filters : stat,wall,log4j
    # 通过connectProperties属性来打开mergeSql功能；慢SQL记录
    connectionProperties : druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
    # 合并多个DruidDataSource的监控数据
    useGlobalDataSourceStat : true
```

##  已知问题

1. 不支持配置多个DataSource

