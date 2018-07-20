package org.luncert.springconfigurer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.luncert.cson.CsonBuilder;
import org.luncert.cson.CsonObject;
import org.luncert.mullog.Mullog;
import org.luncert.mullog.annotation.BindAppender;
import org.springframework.stereotype.Component;

import net.sf.json.JSONObject;

@Component
@BindAppender(name = "ConfigManager")
public class ConfigManager {

    private Mullog mullog = new Mullog(this);

    private String configPath;

    private JSONObject jsonConf;

    private CsonObject csonConf;

    private Properties propConf;

    /**
     * 默认配置文件目录 classpath, user dir
     * @throws Exception
     */
    public ConfigManager() throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("config.cson");
        if (url == null) url = Thread.currentThread().getContextClassLoader().getResource("config.json");
        if (url == null) url = Thread.currentThread().getContextClassLoader().getResource("config.properties");
        if (url != null) loadConfig(url.toString().replace("file:", ""));
        else loadConfig(System.getProperty("user.dir"));
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
        }
        else loadConfigFile(file);
    }

    private void loadConfigFile(File configFile) throws Exception {
        String location = configFile.getPath();
        if (!configFile.exists()) throw new FileNotFoundException(location);

        String name = configFile.getName();
        String type = name.substring(name.lastIndexOf('.') + 1);

        if (type.equals("json")) jsonConf = JSONObject.fromObject(readFile(configFile));
        else if (type.equals("cson")) {
            CsonBuilder csonBuilder = new CsonBuilder();
            csonConf = csonBuilder.build(readFile(configFile));
        }
        else if (type.equals("properties")) {
            propConf = new Properties();
            propConf.load(new FileInputStream(configFile));
        }
        else throw new ConfigurerException("unsupported type of configuration file: " + type);
        mullog.info("successed to load config from:", location);
    }

    private String readFile(File file) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer buffer = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) buffer.append(line).append("\n");
        reader.close();
        return buffer.toString();
    }

    /*

    // write the configuration back to the file
    private void saveChanges() {
        try {
            PrintWriter pw = new PrintWriter(configPath);
            pw.append(configs.toString()).flush();
            pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }

	public void setProperty(String path, String value) {
        if (path == null || path.equals(":")) return;

        StringTokenizer tokenizer = new StringTokenizer(path, ":");
        JSONObject tmp = configs;

        while (true) {
            String name = tokenizer.nextToken();
            if (!tokenizer.hasMoreTokens()) {
                tmp.put(name, value);
                saveChanges();
                return;
            }
            else {
                if (tmp.has(name)) tmp = tmp.getJSONObject(name);
                else {
                    // 由于JSONObject不能存放空的JSONObject，所以只能倒着来
                    List<String> tokens = new ArrayList<>();
                    while (tokenizer.hasMoreTokens()) tokens.add(tokenizer.nextToken());
                    Object v = value;
                    for (int i = tokens.size() - 1; i >= 0; i--) {
                        JSONObject jsonObj = new JSONObject();
                        jsonObj.put(tokens.get(i), v);
                        v = jsonObj;
                    }
                    tmp.put(name, v);
                    saveChanges();
                    return;
                }
            }
        }
	}

	public JSONObject getConfig(String name) {
		return configs.getJSONObject(name);
	}
    */

	public Object getAttribute(String namespace) {
        if (namespace == null || namespace.equals(":")) return null;

        if (propConf != null) return propConf.getProperty(namespace);
        else {
            StringTokenizer tokenizer = new StringTokenizer(namespace, ":");
            JSONObject tmp = jsonConf;
            while (true) {
                String name = tokenizer.nextToken();
                if (tokenizer.hasMoreTokens()) {
                    tmp = tmp.getJSONObject(name);
                    if (tmp == null) return null;
                }
                else return tmp.getString(name);
            }
        }

    }
    
}