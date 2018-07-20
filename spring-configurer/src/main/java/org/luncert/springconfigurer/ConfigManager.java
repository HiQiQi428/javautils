package org.luncert.springconfigurer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.luncert.cson.CsonBuilder;
import org.luncert.mullog.Mullog;
import org.luncert.mullog.annotation.BindAppender;
import org.springframework.stereotype.Component;

import net.sf.json.JSONObject;

@Component
@BindAppender(name = "ConfigManager")
public class ConfigManager extends FileAlterationListenerAdaptor implements ConfigObject {

    private Mullog mullog = new Mullog(this);

    private String configPath;

    FileAlterationMonitor monitor;

    private JsonProxy jsonConf;

    private CsonProxy csonConf;

    private PropertiesAdapter propConf;

    /**
     * 默认配置文件目录 classpath, user dir
     * 
     * @throws Exception
     */
    public ConfigManager() throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("config.cson");
        if (url == null)
            url = Thread.currentThread().getContextClassLoader().getResource("config.json");
        if (url == null)
            url = Thread.currentThread().getContextClassLoader().getResource("config.properties");
        if (url != null)
            loadConfig(url.toString().replace("file:", ""));
        else
            loadConfig(System.getProperty("user.dir"));
    }

    public ConfigManager(String location) throws Exception {
        loadConfig(location);
    }

    private void loadConfig(String location) throws Exception {
        File file = new File(location);
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                mullog.info(f.getName());
                if (f.isFile() && f.getName().startsWith("config.")) {
                    loadConfigFile(f);
                    break;
                }
            }
            throw new ConfigurerException("connot find a configuration file in location: " + location);
        } else
            loadConfigFile(file);
    }

    private void loadConfigFile(File configFile) throws Exception {
        String location = configFile.getPath();
        if (!configFile.exists())
            throw new FileNotFoundException(location);

        String name = configFile.getName();
        String type = name.substring(name.lastIndexOf('.') + 1);

        if (type.equals("json"))
            jsonConf = new JsonProxy(JSONObject.fromObject(readFile(configFile)));
        else if (type.equals("cson")) {
            CsonBuilder csonBuilder = new CsonBuilder();
            csonConf = new CsonProxy(csonBuilder.build(readFile(configFile)));
        } else if (type.equals("properties")) {
            propConf = new PropertiesAdapter();
            propConf.load(new FileInputStream(configFile));
        } else
            throw new ConfigurerException("unsupported type of configuration file: " + type);

        configPath = location;
        mullog.info("successed to load config from:", location);
    }

    private String readFile(File file) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer buffer = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null)
            buffer.append(line).append("\n");
        reader.close();
        return buffer.toString();
    }

    private void saveChange() {
        File file = new File(configPath);
        try {
            if (!file.exists())
                file.createNewFile();
            PrintWriter pw = new PrintWriter(file);
            pw.append(toString()).flush();
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public void startWatchChange() throws Exception {
        if (monitor != null) stopWatchChange();
        FileAlterationObserver observer;
        observer = new FileAlterationObserver(configPath);
        observer.addListener(this);
        monitor = new FileAlterationMonitor(1000, observer);
        monitor.start();
    }

    @Deprecated
    public void stopWatchChange() {
        if (monitor != null) {
            try {
                monitor.stop();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                monitor = null;
            }
        }
    }

    @Override
    @Deprecated
    public void onFileChange(File file) {
        mullog.error(file.getName());
    }

    @Override
    public void setAttribute(String namespace, Object value) {
        if (propConf != null)
            propConf.setAttribute(namespace, value);
        else if (csonConf != null)
            csonConf.setAttribute(namespace, value);
        else
            jsonConf.setAttribute(namespace, value);
        saveChange();
    }

    @Override
    public Object getAttribute(String namespace) {
        if (propConf != null)
            return propConf.getAttribute(namespace);
        else if (csonConf != null)
            return csonConf.getAttribute(namespace);
        else
            return jsonConf.getAttribute(namespace);
    }

    @Override
    public String toString() {
        if (propConf != null)
            return propConf.toString();
        else if (csonConf != null)
            return csonConf.toString();
        else
            return jsonConf.toString();
    }

}