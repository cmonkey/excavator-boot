## cumulative-spring-boot-starter

### 快速开始

添加cumulative starter组件依赖

```
        <dependency>
            <groupId>org.excavator.boot</groupId>
            <artifactId>cumulative-spring-boot-starter</artifactId>
        </dependency>
```

### 强制依赖

组件依赖redis 支持，目前的存储实现为redis 

### 使用场景

对需要针对基于时间维度的统计进行数据存储和查询的场景

### 愉快的使用

```
    @Resource 
    private Cumulative cumulaitve;

    public String cumulativeByDay(String key, String dimensionKey, String value) {
        try {
            cumulative.countByDay(key, new Cumulative.Dimension(dimensionKey, value));
        } catch (IllegalAccessException e) {
            logger.error("cumulative Exception = {}", e);
        }
        return cumulative.queryByDay(key, dimensionKey);
    }

    public String cumulativeByMonth(String key, String dimensionKey, String value) {
        try {
            cumulative.countByMonth(key, new Cumulative.Dimension(dimensionKey, value));
        } catch (IllegalAccessException e) {
            logger.error("cumulative Exception = {}", e);
        }
        return cumulative.queryByMonth(key, dimensionKey);
    }

    public String cumulativeByYear(String key, String dimensionKey, String value) {
        try {
            cumulative.countByYear(key, new Cumulative.Dimension(dimensionKey, value));
        } catch (IllegalAccessException e) {
            logger.error("cumulative Exception = {}", e);
        }
        return cumulative.queryByYear(key, dimensionKey);
    }

    public void cumulativeByDayAndMonth(String key, String dimensionKey, String value) {
        try {
            cumulative.countByDayAndMonth(key, new Cumulative.Dimension(dimensionKey, value));
        } catch (IllegalAccessException e) {
            logger.error("cumulative Exception = {}", e);
        }
    }

    public String queryByDay(String key, String dimensionKey) {
        return cumulative.queryByDay(key, dimensionKey);
    }

    public String queryByMonth(String key, String dimensionKey) {
        return cumulative.queryByMonth(key, dimensionKey);
    }

    public String queryByYear(String key, String dimensionKey) {
        return cumulative.queryByYear(key, dimensionKey);
    }

```

### 已知问题

貌似没有什么问题

