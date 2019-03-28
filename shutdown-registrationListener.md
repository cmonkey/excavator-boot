## registractionListener 不能优雅停机

在1.0.0 版本中，我们通过Feign对服务进行调用

通过spring boot admin client 将服务的状态信息注册到spring boot admin 上

当二者同时存在，在停机时，会出现类似以下错误:

```
[System] 2019-03-27 22:14:08 WARN  [Thread-7]o.s.c.a.AnnotationConfigApplicationContext - Exception thrown from ApplicationListener handling ContextClosedEvent
org.springframework.beans.factory.BeanCreationNotAllowedException: Error creating bean with name 'registrationListener': Singleton bean creation not allowed while singletons of this factory are in destruction (Do not request a bean from a BeanFactory in a destroy method implementation!)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:216)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:308)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:197)
	at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1080)
	at org.springframework.context.event.ApplicationListenerMethodAdapter.getTargetBean(ApplicationListenerMethodAdapter.java:283)
	at org.springframework.context.event.ApplicationListenerMethodAdapter.doInvoke(ApplicationListenerMethodAdapter.java:253)
	at org.springframework.context.event.ApplicationListenerMethodAdapter.processEvent(ApplicationListenerMethodAdapter.java:177)
	at org.springframework.context.event.ApplicationListenerMethodAdapter.onApplicationEvent(ApplicationListenerMethodAdapter.java:140)
	at org.springframework.context.event.SimpleApplicationEventMulticaster.doInvokeListener(SimpleApplicationEventMulticaster.java:172)
	at org.springframework.context.event.SimpleApplicationEventMulticaster.invokeListener(SimpleApplicationEventMulticaster.java:165)
	at org.springframework.context.event.SimpleApplicationEventMulticaster.multicastEvent(SimpleApplicationEventMulticaster.java:139)
	at org.springframework.context.support.AbstractApplicationContext.publishEvent(AbstractApplicationContext.java:393)
	at org.springframework.context.support.AbstractApplicationContext.publishEvent(AbstractApplicationContext.java:399)
	at org.springframework.context.support.AbstractApplicationContext.publishEvent(AbstractApplicationContext.java:347)
	at org.springframework.context.support.AbstractApplicationContext.doClose(AbstractApplicationContext.java:991)
	at org.springframework.context.support.AbstractApplicationContext.close(AbstractApplicationContext.java:958)
	at org.springframework.cloud.context.named.NamedContextFactory.destroy(NamedContextFactory.java:76)
	at org.springframework.beans.factory.support.DisposableBeanAdapter.destroy(DisposableBeanAdapter.java:272)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.destroyBean(DefaultSingletonBeanRegistry.java:583)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.destroySingleton(DefaultSingletonBeanRegistry.java:555)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.destroySingleton(DefaultListableBeanFactory.java:959)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.destroySingletons(DefaultSingletonBeanRegistry.java:516)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.destroySingletons(DefaultListableBeanFactory.java:966)
	at org.springframework.context.support.AbstractApplicationContext.destroyBeans(AbstractApplicationContext.java:1032)
	at org.springframework.context.support.AbstractApplicationContext.doClose(AbstractApplicationContext.java:1008)
	at org.springframework.context.support.AbstractApplicationContext$2.run(AbstractApplicationContext.java:929)

```

错误信息说， registrationListener 在已经被调用shutdown 后，再次被调用了shutdown 

该问题在1.0.0 版本中稳定复现

## 修复方案

1. 如果项目不需要feign和spring admin client 同时存在，可以删除spring admin client 的依赖
2. 确实需要feign & spring admin client 同时存在

    请在业务项目中添加类似FeignBeanFactoryPostProcessor实现

```
    @Component
    public class FeignBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
                                                                                    throws BeansException {
            if (containsBeanDefinition(beanFactory, "feignContext", "registrationListener")) {
                BeanDefinition bd = beanFactory.getBeanDefinition("feignContext");
                bd.setDependsOn("registrationListener");
            }
        }

        private boolean containsBeanDefinition(ConfigurableListableBeanFactory beanFactory, String... beans) {
            return Arrays.stream(beans).allMatch(b -> beanFactory.containsBeanDefinition(b));
        }
    }

```

## 参考链接

https://github.com/spring-cloud/spring-cloud-netflix/issues/2099
