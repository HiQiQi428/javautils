### 第三方包配置spring-@Import
* 解决方案一：实现ImportBeanDefinitionRegistrar，这个方法似乎只能达到在第三方包注册Bean的效果，没有探索过这个接口
* 解决方案二：使用spring配置类，加@Configuration。虽然在一个项目下只允许有一个@Configuration注解配置类，不过不同的项目是可以的。