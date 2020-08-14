package com.practice.library.util.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Keeps ResourceBundle object and gives access to its content
 */
public class Property {
    private ResourceBundle resourceBundle;
    private static final Logger LOGGER = Logger.getLogger("Property.class");

    public Property() {
        try {
            String propertyFileName = getPropertyFileName();
            this.resourceBundle = ResourceBundle.getBundle(propertyFileName);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.getLocalizedMessage());
        }
        LOGGER.info("property object has been instantiated");
    }

    public String getConfiguration(String key) {
        LOGGER.info(String.format("property object returns value of key %s", key));
        return this.resourceBundle.getString(key);
    }

    private String getPropertyFileName() throws IOException {
        String propertyName = getPropertyFile().get().getFileName().toString()
                .replace(".properties", "");
        return propertyName;
    }

    private Optional<Path> getPropertyFile() throws IOException {
        return Files.walk(Paths.get("src"))
                .filter(Files::isRegularFile)
                .filter(file -> isSourceClass(file))
                .findFirst();
    }

    private boolean isSourceClass(Path file) {
        String path = file.toString();
        boolean flag = path.matches(".+resources/.+[.]properties");
        flag = flag && !path.matches("src/test.+");
        return flag;
    }
}
