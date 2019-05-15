## schedule-spring-boot-starter

在通过spring schedule 进行task 编程时，需要对taskSchedule 进行配置

schedule-spring-boot-starter 简化了这种配置

通过基本的参数设置可以自动的将taskSchedule 进行设置

excavator.schedule.threadPool.poolSize=10
excavator.schedule.threadPool.awaitTerminaltionSeconds = 60
excavator.schedule.threadPool.waitForTaskToCompleteOnShutdown = true
excavator.schedule.threadNamePrefix=excavator-task-
```

主要对schedule 的池大小, 等待终止时间等
