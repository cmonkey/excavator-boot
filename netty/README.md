# Netty

通过方便的api , 提供对netty 的简单封装

# 快速开始

> spring boot项目接入


添加lock starter组件依赖，目前还没上传到公共仓库，需要自己下源码build

```
        <dependency>
            <groupId>com.ifdp.services</groupId>
            <artifactId>netty</artifactId>
            <version>1.0.0-SNAPSHOT</version>
        </dependency>
```

## NettyServer 的使用

通过net NettyServer 的方式可以快速构建一个netty server 

默认使用Charsets.UTF-8对报文进行解析，并监听9500端口

提供N的构造函数给用户，方便用户定制

强制要求用户提供ChannelHandler， 并且该Handler 必须是被io.netty.channel.Sharable 注解所标识

在web项目中可以被@PostConstruct注解的方法中调用nettyServer.run 方法启动nettyServer 

在被@PreDestroy注解的方法中调用nettyServer.shutdown 方法进行对nettyServer 的关闭

## NettyClient 的使用

NettyClinet 的构造函数提供多种方式用来创建同步发送消息，并获取响应的构造函数

默认获取响应报文的body , 可以通过传入ResponseViewMode 的方式修改这一默认行为

send 方法发送socket 消息，并同步获取该请求的响应

目前每一次的请求和响应都是同步的，也就是说都需要重新new NettyClient 

异步的同一个连接进行多消息的发送，需要引入类似requestId 的方式，目前不支持


## 测试

NettyTest 测试依赖对NettyServer 的启动

启动NettyServer 的方法表示为@BeforceAll 注解

junit5 默认模式为per-method , 在per-method 模式下, @BeforceAll 标注的方法必须是static 的

在不修改TestInstance 的模式下, 在idea里，目前的实现为通过添加一个同名的object, 在object中添加被@BeforceAll 标注的function 

另一类实现就是通过修改TestInstance 的模式为per_class , 然后在class 中就可以使用非static 方法标注@BeforceAll

ref: https://junit.org/junit5/docs/current/user-guide/#writing-tests-test-instance-lifecycle


