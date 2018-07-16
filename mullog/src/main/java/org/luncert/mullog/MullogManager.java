package org.luncert.mullog;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.luncert.mullog.appender.Appender;
import org.luncert.mullog.exception.MullogException;

public class MullogManager implements Serializable {

    private static final long serialVersionUID = 7606646448285009177L;

    private static Map<String, Appender> appenders = new HashMap<>();

    private MullogManager() {}

    private static Path configPath;

    private static void loadConfig() {
        // load config
        String configPath = MullogManager.class.getClassLoader().getResource("mullog.properties").toString().replace("file:", "");
        MullogManager.configPath = Paths.get(configPath);
        Map<String, Properties> confs = new HashMap<>();
        Properties props = new Properties();
        try {
            InputStream in = new FileInputStream(new File(configPath));
            props.load(in);
            // cast
            for (Object key : props.keySet()) {
                String tmp = (String)key;
                int i = tmp.lastIndexOf(".");

                String namespace = tmp.substring(0, i);
                String name = tmp.substring(i + 1);
                Object value = props.get(key);

                if (confs.containsKey(namespace)) confs.get(namespace).put(name, value);
                else {
                    Properties subProps = new Properties();
                    subProps.put(name, value);
                    confs.put(namespace, subProps);
                }
            }
            // resolve
            for (String namespace: confs.keySet()) {
                Properties subProps = confs.get(namespace);
                try {
                    if (subProps.get("level") == null) throw new MullogException("field level must be specified in mullog.properties");
                    if (subProps.get("type") == null) throw new MullogException("field type must be specified in mullog.properties");
                    if (subProps.get("format") == null) throw new MullogException("field format must be specified in mullog.properties");
                    Class<?> clazz = Class.forName(subProps.getProperty("type"));
                    if (Appender.class.isAssignableFrom(clazz)) {
                        Constructor<?> constructor = clazz.getConstructor(Properties.class);
                        appenders.put(namespace, (Appender)constructor.newInstance(subProps));
                    }
                } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    static {
        loadConfig();
    }

    /**
     * get appender by name
     * @return appender or null
     */
    protected static Appender getAppender(String name) { return appenders.get(name); }

    protected static Collection<Appender> getAppenders() {
        if (appenders.size() > 0) return appenders.values();
        else return null;
    }

<<<<<<< HEAD
    public static Path getConfigPath() { return configPath; }
=======
    protected static void addAppender(String name, Appender appender) { appenders.put(name, appender); }

    public static Path getConfigPath() {
        return configPath;
    }
>>>>>>> f6e9189814c2841a75c528667b949adcfa570bb3

}