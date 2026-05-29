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

                .requestMatchers(POST, "/api/vehicles").hasRole("ADMIN")
                .requestMatchers(PUT, "/api/vehicles/**").hasRole("ADMIN")
                .requestMatchers(DELETE, "/api/vehicles/**").hasRole("ADMIN")
                .requestMatchers(POST, "/api/compliance/run").hasRole("ADMIN")

                .requestMatchers(GET, "/api/vehicles/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/policies/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/movements/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(GET, "/api/compliance/violations").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/reports/**").hasAnyRole("USER", "ADMIN")

                .anyRequest().authenticated()




        );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

}
