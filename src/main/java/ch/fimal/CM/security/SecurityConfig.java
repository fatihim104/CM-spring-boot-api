package ch.fimal.CM.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import ch.fimal.CM.repository.RefreshTokenRepository;
import ch.fimal.CM.security.filter.AuthenticationFilter;
import ch.fimal.CM.security.filter.ExceptionHandlerFilter;
import ch.fimal.CM.security.filter.JWTAuthorizationFilter;
import ch.fimal.CM.security.manager.CustomAuthenticationManager;
import ch.fimal.CM.service.AuthServiceImpl;
import ch.fimal.CM.service.UserService;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomAuthenticationManager customAuthenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    private final AuthServiceImpl authService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager,
                refreshTokenRepository, userService, authService);
        authenticationFilter.setFilterProcessesUrl("/authenticate");

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH).permitAll()
                        .requestMatchers(HttpMethod.POST, "/authenticate").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/refresh").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/logout").permitAll()
                        .anyRequest().authenticated()

                )
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter((Filter) new JWTAuthorizationFilter(), AuthenticationFilter.class)
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}