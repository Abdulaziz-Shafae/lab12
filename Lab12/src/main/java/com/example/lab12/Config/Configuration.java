package com.example.lab12.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@org.springframework.context.annotation.Configuration
@EnableWebSecurity
public class Configuration {


    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http

                .csrf( csrf -> csrf.disable()  )

                .sessionManagement( sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/user/register").permitAll()
                        .requestMatchers(
                                "/api/v1/user/admin/register/admin",
                                "/api/v1/user/get/users",
                                "/api/v1/user/update",
                                "/api/v1/user/delete/{userID}",
                                "/api/v1/blog/admin/get"
                        ).hasAuthority("ADMIN")
                        .requestMatchers(
                                "/api/v1/blog/add",
                                "/api/v1/blog/edit/{blogId}",
                                "/api/v1/blog/delete/{blogId}",
                                "/api/v1/blog/get",
                                "/api/v1/blog/get/id/{blogId}",
                                "/api/v1/blog/get/title/{title}"
                        ).hasAnyAuthority("USER", "ADMIN")
                        .anyRequest().authenticated()
                )

                .logout(logout -> logout .logoutUrl("/api/v1/user/logout").invalidateHttpSession(true))

                .httpBasic( basic -> {})

                .build();
    }

}
