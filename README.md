# excavator-boot
  
   excavator-boot 在spring-boot 框架上，提供常用的基础能力

   并对常用的第三方工具进行版本定义

# 功能列表

  1. druid (提供对druid 数据库连接池的默认spring autoconfigure)

  2. atomikos (提供对多数据源的支持,通过atomikos)

  3. idWorker  (提供对分布式编号生成器,基于Twitter snowflake 算法)

  4. leader  (通过zookeeper 进行选举)

  5. logAspect  (简单打印controller 的入口/出口日志)

  6. authorization  (基于redis 生成token, 和通过token 获取用户信息)

  7. commons-generator (通过简单的数据库配置，生成基本的代码框架)

  8. lock (提供分布式锁服务, 基于redisson)

  9. netty (提供对同步报文的发送)

  10. kafka (提供开箱即用的kafka 支持)

  11. cumulative(基于redis的多维度统计api)

  12. quartz (对quartz 添加开箱即用的starter 支持, spring boot 2 默认提供开箱即用的quartz 支持，可以选择使用官方的实现)

  13. redisLimit (通过redis + lua 实现的redis limit , 可用于对资源进行额度限制)



# 快速开始

   引入 excavator-boot

   在创建好一个 Spring Boot 的工程之后,接下来就需要引入excavator-boot的依赖
   
   首先修改maven项目的配置文件 pom.xml， 将

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>${spring.boot.version}</version>
        <relativePath/> 
    </parent>

   替换为：

    <parent>
            <groupId>org.excavator.boot</groupId>
            <artifactId>excavator-boot-dependencies</artifactId>
            <version>2.0.0-SNAPSHOT</version>
            <relativePath/> <!-- lookup parent from repository -->
    </parent>

## 本地测试

批量对netty 进行测试容易出现

ExecutionException The forked VM terminated without properly saying goodbye. VM crash or System.exit called? 错误

所以在进行测试时，执行 mvn -pl '!netty' test

## TODO List 

1. 通过docker-compose 的方式将对测试的外部依赖进行docker 化
