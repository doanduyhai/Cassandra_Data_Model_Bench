package com.secretclient.stress.init;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;

public class PropertyLoader {

    private static final String CONFIG_FILE = "on.config.file";
    private static final Properties PROPERTIES = new Properties();

    public PropertyLoader() {
        final String propertyFile = System.getProperty(CONFIG_FILE);
        if (StringUtils.isBlank(propertyFile)) {
            throw new IllegalArgumentException("Cannot find property '" + CONFIG_FILE + "'. It is mandatory");
        } else {
            try {
                PROPERTIES.load(new FileInputStream(propertyFile));
            } catch (IOException e) {
                throw new IllegalArgumentException("Cannot open property '" + CONFIG_FILE + "' file");
            }
        }
    }

    public Properties getProperties() {
        return PROPERTIES;
    }

    public String getString(String key) {
        return PROPERTIES.getProperty(key);
    }

    public Integer getInt(String key) {
        return Integer.parseInt(PROPERTIES.getProperty(key));
    }
}
