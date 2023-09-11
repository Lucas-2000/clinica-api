package br.com.dev.clinica.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

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
                                .requestMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/users/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/users/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/users/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/attendants").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/attendants/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/attendants/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/attendants/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/doctors").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/doctors/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/doctors/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/doctors/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/patients").permitAll()
                                .requestMatchers(HttpMethod.GET, "/patients/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/patients/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/patients/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/appointments").permitAll()
                                .requestMatchers(HttpMethod.GET, "/appointments/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/appointments/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/appointments/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/reports/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
