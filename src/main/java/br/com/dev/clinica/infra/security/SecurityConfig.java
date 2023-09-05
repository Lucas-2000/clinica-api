package br.com.dev.clinica.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                                .requestMatchers(HttpMethod.GET, "/users/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/users/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/users/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/attendants").permitAll()
                                .requestMatchers(HttpMethod.GET, "/attendants/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/attendants/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/attendants/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/doctors").permitAll()
                                .requestMatchers(HttpMethod.GET, "/doctors/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/doctors/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/doctors/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/patients").permitAll()
                                .requestMatchers(HttpMethod.GET, "/patients/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/patients/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/patients/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/appointments").permitAll()
                                .requestMatchers(HttpMethod.GET, "/appointments/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/appointments/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/appointments/**").permitAll()
                                .anyRequest().authenticated()
                )
                .build();
    }
}
