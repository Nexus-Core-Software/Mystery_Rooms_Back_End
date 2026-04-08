package com.project.mysteryRomms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration indica que esta clase define configuraciones especiales para la aplicación
@Configuration
public class CorsConfig {

    // Este método crea un "filtro" que controla quién puede hablar con nuestra aplicación
    @Bean
    public CorsFilter corsFilter() {
        // Fuente de configuración basada en URLs
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Creamos una configuración de CORS
        CorsConfiguration config = new CorsConfiguration();
        // Permitimos que cualquier página web (origen) se conecte
        config.addAllowedOrigin("*");
        // Permitimos cualquier tipo de cabecera (información extra en la petición)
        config.addAllowedHeader("*");
        // Permitimos cualquier método (GET, POST, PUT, DELETE, etc.)
        config.addAllowedMethod("*");
        // Registramos esta configuración para todas las rutas (/** significa "todo")
        source.registerCorsConfiguration("/**", config);
        // Devolvemos el filtro listo para usarse
        return new CorsFilter(source);
    }

    // Este método configura reglas específicas para ciertas rutas
    @Bean
    public WebMvcConfigurer mvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Permitimos que cualquier origen y método acceda a las rutas que empiezan con /media/
                registry.addMapping("/media/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }
}
