package com.experience_kafka.config;

import com.experience_kafka.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/add-user").permitAll()
                        .requestMatchers("/api/v1/test").permitAll()
                        .requestMatchers("/api/v1/saveToRepository").authenticated()
                        .requestMatchers("/api/v1/recreateTopic").authenticated()
                        .requestMatchers("/api/v1/showMessages").authenticated()
                        .requestMatchers("/api/v1/sendToKafka").authenticated()
                        .requestMatchers("/api/v1/records").authenticated()
                        .requestMatchers("/api/v1/deleteRecords").authenticated()


                        .requestMatchers("/get-messages-from-kafka.html").authenticated()
                        .requestMatchers("/index.html").authenticated()
                        .requestMatchers("/send-messages-from-kafka-to-postgres.html").authenticated()
                        .requestMatchers("/test.html").authenticated()
                        .requestMatchers("/add-user.html").authenticated()
                        .requestMatchers("/send-to-kafka.html").authenticated()
                        .requestMatchers("/fetch-records-from-db.html").authenticated()
                        .requestMatchers("/clear-kafka-topic.html").authenticated()

                        .anyRequest().authenticated()
                )
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .build();
    }

}
