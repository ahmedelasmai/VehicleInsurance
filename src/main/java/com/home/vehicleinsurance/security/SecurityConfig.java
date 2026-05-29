package com.home.vehicleinsurance.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        not using cookie sessions so no need for this
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth

                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/actuator/health").permitAll()

                .requestMatchers(POST, "/api/vehicles").hasRole("ROLE_ADMIN")
                .requestMatchers(PUT, "/api/vehicles/**").hasRole("ROLE_ADMIN")
                .requestMatchers(DELETE, "/api/vehicles/**").hasRole("ROLE_ADMIN")
                .requestMatchers(POST, "/api/compliance/run").hasRole("ROLE_ADMIN")

                .requestMatchers(GET, "/api/vehicles/**").hasAnyRole("ROLE_USER", "ROLE_ADMIN")
                .requestMatchers("/api/policies/**").hasAnyRole("ROLE_USER", "ROLE_ADMIN")
                .requestMatchers("/api/movements/**").hasAnyRole("ROLE_USER", "ROLE_ADMIN")
                .requestMatchers(GET, "/api/compliance/violations").hasAnyRole("ROLE_USER", "ROLE_ADMIN")
                .requestMatchers("/api/reports/**").hasAnyRole("ROLE_USER", "ROLE_ADMIN")

                .anyRequest().authenticated()




        );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

}
