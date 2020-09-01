package com.practice.library.util;

import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Keeps ResourceBundle object and gives access to its content
 */
public class Property {
    private final ResourceBundle resourceBundle;
    private static final Logger LOGGER = Logger.getLogger("Property.class");

    public Property(String propertyFileName) {
        this.resourceBundle = ResourceBundle.getBundle(propertyFileName);
        LOGGER.info("property object has been instantiated");
    }

    public String getConfiguration(String key) {
        LOGGER.info(String.format("property object returns value of key %s", key));
        return this.resourceBundle.getString(key);
    }
}
