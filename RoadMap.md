## RoadMap

1. 通过docker的方式构建测试，而不是通过外部环境对组件进行测试(已经通过feature/change-test-flow分支实现对外部环境的自动配置)

   Update: 2019年9月5日14:29:46

2. 将项目和Github Package Registry 关联，对实现依赖管理

3. 支持Github Actions, 目前没有看到GitHub Actions 对自定义hosts 的支持，而目前的测试需要依赖通过docker 启动外部服务，然后将127.0.0.1绑定到www.excavator.com 域名做测试
