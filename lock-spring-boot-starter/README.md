# lock-spring-boot-starter

基于redis的分布式锁spring-boot starter组件

使得项目拥有分布式锁能力变得异常简单

支持spring boot，和spirng mvc等spring相关项目

# 快速开始

> spring boot项目接入


1.添加lock starter组件依赖
```
        <dependency>
            <groupId>org.excavator.boot</groupId>
            <artifactId>lock-spring-boot-starter</artifactId>
            <version>1.0.0-SNAPSHOT</version>
        </dependency>
```

2.application.properties配置redis链接

```
excavator.lock.redis.address=redis://you_ip:you_port                                            #redis 的地址
excavator.lock.redis.password=you_redis_password                                                #redis password
excavator.lock.cluster.enabled = false                                                          #redis cluster 是否启用
excavator.lock.cluster.address=redis://you_ip:you_port,redis://you_ip:you_port                  #cluster redis ip info
```



3.在需要加分布式锁的方法上，添加注解@Lock，如：
```
@Service
public class TestService {

    @Lock(waitTime = Long.MAX_VALUE)
    public String getValue(String param) throws Exception {
        return "success";
    }
}

```

4.@Lock注解参数说明
```
@Lock可以标注四个参数，作用分别如下

name：lock的name，对应redis的key值。默认为：类名+方法名

lockType：锁的类型，目前支持（可重入锁，公平锁，读写锁）。默认为：公平锁

waitTime：获取锁最长等待时间。默认为：60s。同时也可通过excavator.lock.waitTime统一配置

leaseTime：获得锁后，自动释放锁的时间。默认为：60s。同时也可通过excavator.lock.leaseTime统一配置

keys: 自定义key, 可使用spel表达式, example @Lock(keys = {"user.id"})
```

##  已知问题

1. 在使用默认key时，存在key 长度相对较长的问题，一般在redis 的存储中不建议将key 定义得太长，此问题需要修改BusinessKeyProvider 
2. 不提供默认的keys 时，是否默认提供将所有参数进行打包压缩后，进行key 构建, 使默认构建基于参数的幂等性, 直接使用方法的加锁力度太宽广!!!

## 警告

目前加锁的实现在获取锁不成功的情况下，系统直接返回了null 

那么在通过lock机制处理类似缓存雪崩问题,就需要在处理业务代码时，特别小心

在调用被lock保护的方法时，需要一定判断方法的结果返回是否为null

