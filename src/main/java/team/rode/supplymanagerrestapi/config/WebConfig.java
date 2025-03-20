package team.rode.supplymanagerrestapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${frontend.url}")
    private String URL;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] methods = {"GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"};

        registry.addMapping("/**")
                .allowedOrigins(URL)
                .allowedMethods(methods);
    }
}
