package org.luncert.mullog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.luncert.mullog.appender.Appender;
import org.luncert.mullog.formatter.Formatter;

public class MullogConfig {

    public static void autoConfig() {
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
                            Appender appender = (Appender)constructor.newInstance(subProps);
                            appender.setFormatter(new Formatter(subProps.getProperty("format")));
                            MullogManager.getInstance().addAppender(appender);
                        }
					} catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
						e.printStackTrace();
					}
                }
			} catch (IOException e) {
                throw new RuntimeException(e);
			}
		} catch (FileNotFoundException e) {
            throw new RuntimeException(e);
		}
    }

}
