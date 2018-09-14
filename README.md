# excavator-boot
  
   excavator-boot 在spring-boot 框架上，提供常用的基础能力

   并对常用的第三方工具进行版本定义

# 功能列表

  1. druid (提供对druid 数据库连接池的默认spring autoconfigure)
  2. atomikos (提供对多数据源的支持,通过atomikos)
  3. idWorker  (提供对分布式编号生成器,基于Twitter snowflake 算法)
  4. leader  (通过zookeeper 进行选举)
  5. logAspect  (简单打印controller 的入口/出口日志)
  7. authorization  (基于redis 生成token, 和通过token 获取用户信息)
  8. commons-generator (通过简单的数据库配置，生成基本的代码框架)
  9. lock (提供分布式锁服务, 基于redisson)



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
            <groupId>com.github.excavator.boot</groupId>
            <artifactId>excavator-boot-dependencies</artifactId>
            <version>1.0.0-SNAPSHOT</version>
            <relativePath/> <!-- lookup parent from repository -->
    </parent>
