package com.example.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/css/**").permitAll() // Permite carregar estilos sem login
                .anyRequest().authenticated()           // Protege todas as outras rotas
            )
            .formLogin((form) -> form
                .loginPage("/login")                    // Rota personalizada
                .defaultSuccessUrl("/home", true)       // Para onde vai após logar
                .permitAll()
            )
            .logout((logout) -> logout.permitAll());

        return http.build();
    }
}