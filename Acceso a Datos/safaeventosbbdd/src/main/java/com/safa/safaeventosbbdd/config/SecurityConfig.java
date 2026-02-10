package com.safa.safaeventosbbdd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Activamos CORS con nuestra configuración
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para APIs REST
                .authorizeHttpRequests(auth -> auth
                        // Tus rutas públicas
                        .requestMatchers("/fotoevento/**", "/eventos/**", "/usuarios/**").permitAll()
                        // Permitir OPTIONS (necesario para la "pre-flight" request de CORS en móviles)
                        .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // --- SOLUCIÓN NUCLEAR: PERMITIR TODO ---
        // Esto permite peticiones desde localhost, Render, Android, iOS, etc.
        configuration.setAllowedOrigins(List.of("*"));

        // Permitir todos los métodos (GET, POST, PUT, DELETE, OPTIONS)
        configuration.setAllowedMethods(List.of("*"));

        // Permitir todas las cabeceras
        configuration.setAllowedHeaders(List.of("*"));

        // Credentials debe ser FALSE si usas "*" en Origins
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}