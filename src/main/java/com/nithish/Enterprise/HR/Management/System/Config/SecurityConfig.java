package com.nithish.Enterprise.HR.Management.System.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(withDefaults())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/dashboard/**").authenticated()

                        .requestMatchers("/employee/**")
                        .hasAnyRole("HR","MANAGER","EMPLOYEE")

                        .requestMatchers("/certification/**")
                        .hasAnyRole("HR","MANAGER" ,"EMPLOYEE")

                        .requestMatchers("/leave/**")
                        .hasAnyRole("HR","MANAGER","EMPLOYEE")

                        .requestMatchers("/payslip/**")
                        .hasRole("HR")

                        .anyRequest().authenticated()
                )

                .httpBasic(withDefaults());

        return http.build();
    }
}