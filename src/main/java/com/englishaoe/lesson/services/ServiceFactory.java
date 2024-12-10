package com.englishaoe.lesson.services;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

public class ServiceFactory<T> {
    private final Map<String, T> services = new HashMap<>();

    public ServiceFactory(ApplicationContext applicationContext, Class<T> serviceType) {
        Map<String, T> beans = applicationContext.getBeansOfType(serviceType);
        for (Map.Entry<String, T> entry : beans.entrySet()) {
            String serviceName = entry.getKey();
            services.put(serviceName, entry.getValue());
        }
    }

    public T getService(String name) {
        return services.get(name);
    }
}
