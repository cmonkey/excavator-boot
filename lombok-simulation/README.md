# lombok-simulation

1. 定义编译期注解
2. 利用JSR269 api(Pluggable Annotation Processing API )创建编译期的注解处理器
3. 使用tool.jar 的javac api 处理AST
4. 将功能写入jar

## 定义Getter 注解

Getter 注解使用ElementType.TYPE 定义类注解

RetentionPolicy.SOURCE 表示对编译期起效

通过jsr269 , 在编译时指定processor 对编辑阶段的注解进行解析

## GetterProcessor 

GetterProcessor 继承AbstractProcessor 

重写init method , 通过ProcessingEnvironment获得编译阶段的环境信息

重写process method, 主要实现逻辑，用于生成AST

Messager: 编译期log 
JavacTrees: 编译期待处理AST
TreeMarker: 创建AST node method 
Names: 创建标识符的method 



