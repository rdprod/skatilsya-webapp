package com.rdprod.springboot.spring_rdprod_webapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${user-avatar.path}")
    private String userAvatarPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path imagesDirectory = Paths.get(userAvatarPath);
        String imagesAbsolutePath = imagesDirectory.toFile().getAbsolutePath();

        registry.addResourceHandler("/userAvatarImages/**")
                .addResourceLocations("file:///" + imagesAbsolutePath + "/");
    }
}
