package org.luncert.mullog;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.HashMap;
import java.util.Map;

import org.luncert.mullog.appender.Appender;

public class MullogManager implements Serializable {

    private static final long serialVersionUID = 7606646448285009177L;

    private static Map<String, Appender> appenders = new HashMap<>();

    private static Path configPath;

    private MullogManager() {}

    private static void loadConfig() {
        // set config path
        configPath = Paths.get(System.getProperty("user.dir"), "/src/main/resources/mullog.properties");
        // load config
		try {
			InputStream in = new FileInputStream(new File(configPath.toString()));
            Map<String, Properties> confs = new HashMap<>();
            Properties props = new Properties();
            try {
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
		} catch (FileNotFoundException e) {
            e.printStackTrace();
		}
    }

    static {
        loadConfig();
    }

    /**
     * get appender by name
     * @return appender or null
     */
    protected static Appender getAppender(String name) { return appenders.get(name); }

    protected static Map<String, Appender> getAppenders() { return appenders; }

    public static Path getConfigPath() {
        return configPath;
    }

}