package com.englishaoe.lesson.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    Environment env;
    @Value("${image.folderDir}")
    private String imageDir;
    @Value("${audio.folderDir}")
    private String voicesDir;
    @Value("${templates.folderDir}")
    private String templatesDir;
    @Autowired
    public WebConfig(Environment env){
        this.env = env;
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(env.getProperty(AppConfig.CLIENT_URL))
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + imageDir + "/");
        registry.addResourceHandler("/voices/**")
                .addResourceLocations("file:" + voicesDir + "/");
        registry.addResourceHandler("/templates/**")
                .addResourceLocations("file:" + templatesDir + "/");
    }
}
