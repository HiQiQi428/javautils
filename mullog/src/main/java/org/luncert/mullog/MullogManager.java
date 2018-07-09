package org.luncert.mullog;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.HashMap;
import java.util.Map;

import org.luncert.mullog.appender.Appender;

public class MullogManager implements Serializable {

    private static final long serialVersionUID = 7606646448285009177L;

    private static Map<String, Appender> appenders = new HashMap<>();

    private MullogManager() {}

    private static void autoConfig() {
		try {
			InputStream in = new FileInputStream(new File(System.getProperty("user.dir") + "/src/main/resources/mullog.properties"));
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
                            MullogManager.addAppender(namespace, (Appender)constructor.newInstance(subProps));
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
        autoConfig();
    }

    public static void addAppender(String name, Appender appender) { appenders.put(name, appender); }

    /**
     * get appender by name
     * @return appender or null
     */
    public static Appender getAppender(String name) { return appenders.get(name); }

    protected static Map<String, Appender> getAppenders() { return appenders; }

}