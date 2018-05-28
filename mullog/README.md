
# Mullog

### get started
> step 1: add dependencies to pom.xml
```
<dependency>
    <groupId>org.luncert</groupId>
    <artifactId>mullog</artifactId>
    <version>1.0</version>
</dependency>
```

> step 2: create 'mullog.properties' in the resources directory
```
mullog.udp.type=org.luncert.mullog.appender.UDPAppender
mullog.udp.host=localhost
mullog.udp.port=16000
mullog.udp.format=%t [%l] {%m} %M

mullog.console.type=org.luncert.mullog.appender.ConsoleAppender
mullog.console.format=%t [%l] {%c:%m} %M

mullog.file.type=org.luncert.mullog.appender.FileAppender
mullog.file.format=%t [%l] %M
```

> step 3: use Mullog in your code
```
...
Mullog.info("hi");
...
```

### format
> * %t = date
> * %l = log level
> * %m = method name
> * %c = class name
> * %M = message

### customize appender
You can customize your own appender by implementing interface: <b>org.luncert.mullog.appender.Appender</b>
```
public interface Appender {
    public void log(int logLevel, String message) throws Exception;
    public Appender setFormatter(Formatter formatter);
}
```