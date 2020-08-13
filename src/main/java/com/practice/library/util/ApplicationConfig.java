package com.practice.library.util;

import org.glassfish.jersey.CommonProperties;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.Map;

@ApplicationPath("/")
public class ApplicationConfig extends Application {


    @Override
    public Map<String, Object> getProperties() {

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("jersey.config.server.provider.packages", "com.study.rest");
        properties.put("jersey.config.server.wadl.disableWadl", "true");
        properties.put("jersey.config.server.provider.classnames", "org.glassfish.jersey.media.multipart.MultiPartFeature");
        properties.put(CommonProperties.OUTBOUND_CONTENT_LENGTH_BUFFER, "0");
        System.out.println("getProperties:-> CommonProperties.OUTBOUND_CONTENT_LENGTH_BUFFER_SERVER :" + CommonProperties.getValue(properties, CommonProperties.OUTBOUND_CONTENT_LENGTH_BUFFER, String.class));
        return properties;
    }
}