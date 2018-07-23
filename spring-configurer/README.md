# spring-configurer
### create bean in your configuration class
```
@Bean
public ConfigManager getConfigManager() throws Exception {
    return new ConfigManager();
}
```
### use ConfigManager
```
@Autowired
ConfigManager configManager;

public void test() {
    String host = configManager.getString("server:host");
    int port = configManager.getInteger("server:port");
}
```