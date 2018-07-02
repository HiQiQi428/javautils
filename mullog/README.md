
# mullog

![Shippable branch](https://img.shields.io/shippable/5444c5ecb904a4b21567b0ff/master.svg) ![Maven metadata URI](https://img.shields.io/maven-metadata/v/http/central.maven.org/maven2/com/google/code/gson/gson/maven-metadata.xml.svg)

### Overview
A logger for java which supports UDP, Console, File and more.
### Get started
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
udp.type=org.luncert.mullog.appender.UDPAppender
udp.host=localhost
udp.port=16000
udp.format=%T [%L] {%M} %M

console.type=org.luncert.mullog.appender.ConsoleAppender
console.format=%T [%L] {%C:%M} %M

console2.type=org.luncert.mullog.appender.ConsoleAppender
console2.format=%T [%L] {%C:%M} %M

file.type=org.luncert.mullog.appender.FileAppender
file.format=%T [%L] %S
```

> step 3: use Mullog in your code
```
...
Mullog.info("hi");
...
```

### Format
> * %T = date
> * %L = log level
> * %M = method name
> * %C = class name
> * %S = placeholder

### Customize appender
You can customize your own appender by implementing interface: <b>org.luncert.mullog.appender.Appender</b>
```
public interface Appender {
    public void log(int logLevel, String message) throws Exception;
    public Appender setFormatter(Formatter formatter);
}
```
then use ```MullogManager.getInstance.addAppender```to add your appender