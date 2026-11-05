package config;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

public final class Configuration {

    private static final String FILE_NAME = "_config.properties";
    private static final Properties PROPERTIES = load();

    private Configuration() {
    }

    public static String getRequired(String key) {
        String value = System.getProperty(key, PROPERTIES.getProperty(key));
        if (value == null) {
            throw new IllegalStateException("Missing config key: " + key);
        }
        return value.trim();
    }

    public static int getRequiredInt(String key) {
        return Integer.parseInt(getRequired(key));
    }

    public static boolean getRequiredBoolean(String key) {
        return Boolean.parseBoolean(getRequired(key));
    }

    private static Properties load() {
        Properties properties = new Properties();
        try (InputStream in = Configuration.class.getClassLoader().getResourceAsStream(FILE_NAME)) {
            if (in == null) {
                throw new IllegalStateException(FILE_NAME + " not found on the classpath");
            }
            properties.load(in);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read " + FILE_NAME, e);
        }
        return properties;
    }
}