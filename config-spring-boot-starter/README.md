# config-spring-boot-starter

# 快速开始

1.添加config starter组件依赖
```
        <dependency>
            <groupId>org.excavator.boot</groupId>
            <artifactId>config-spring-boot-starter</artifactId>
            <version>2.0.0-SNAPSHOT</version>
        </dependency>
```

2.application.properties配置

```
swagger.enabled=true // 开启swagger 
swagger.basePackage=org.excavator.boot.services.xxx.controller // 业务项目需要被swagger 扫描的basePackage
sftp.enabled=true // 开启sftp 
sftp.items.'serviceName' // 自定义sftpitem name
sftp.items.'serviceName':name // sftp item name
sftp.items.'serviceName'.ip // sftp ip
sftp.items.'serviceName'.port // sftp port
sftp.items.'serviceName'.path // sftp path
sftp.items.'serviceName'.model // sftp model
sftp.items.'serviceName'.user // sftp user
sftp.items.'serviceName'.password // sftp password
sftp.items.'serviceName'.controlEncoding // sftp controlEncoding
```

##  已知问题

1. //FIXME :v:

